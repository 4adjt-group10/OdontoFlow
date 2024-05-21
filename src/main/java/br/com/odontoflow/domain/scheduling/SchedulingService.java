package br.com.odontoflow.domain.scheduling;

import br.com.odontoflow.application.patient.PatientRecordFormDTO;
import br.com.odontoflow.application.scheduling.SchedulingDTO;
import br.com.odontoflow.application.scheduling.SchedulingFormDTO;
import br.com.odontoflow.application.scheduling.SchedulingUpdateDTO;
import br.com.odontoflow.domain.patient.Patient;
import br.com.odontoflow.domain.patient.PatientRecordService;
import br.com.odontoflow.domain.patient.PatientService;
import br.com.odontoflow.domain.procedure.Procedure;
import br.com.odontoflow.domain.procedure.ProcedureService;
import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.domain.professional.ProfessionalAvailability;
import br.com.odontoflow.domain.professional.ProfessionalService;
import br.com.odontoflow.infrastructure.professional.ProfessionalAvailabilityRepository;
import br.com.odontoflow.infrastructure.scheduling.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.odontoflow.domain.scheduling.SchedulingStatus.LATE;

@Service
public class SchedulingService {

    private final PatientService patientService;
    private final ProcedureService procedureService;
    private final SchedulingRepository schedulingRepository;
    private final ProfessionalService professionalService;
    private final PatientRecordService patientRecordService;
    private final ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    public SchedulingService(SchedulingRepository schedulingRepository,
                             PatientService patientService,
                             ProfessionalService professionalService,
                             ProcedureService procedureService,
                             PatientRecordService patientRecordService,
                             ProfessionalAvailabilityRepository availabilityRepository) {
        this.schedulingRepository = schedulingRepository;
        this.patientService = patientService;
        this.professionalService = professionalService;
        this.procedureService = procedureService;
        this.patientRecordService = patientRecordService;
        this.professionalAvailabilityRepository = availabilityRepository;
    }

    @Transactional
    public SchedulingDTO register(SchedulingFormDTO formDTO) {
        Patient patient = patientService.findByDocumentOrCreate(formDTO.patientName(), formDTO.patientDocument());
        Procedure procedure = procedureService.findById(formDTO.procedureId());
        Professional professional = professionalService.findProfessionalById(formDTO.professionalId());
        ProfessionalAvailability availability = professionalService
                .findAvailabilityByProfessionalAndAvailableTime(professional.getId(), formDTO.appointment());
        Scheduling scheduling = new Scheduling(patient, procedure, professional, formDTO.appointment(), formDTO.status());
        schedulingRepository.save(scheduling);
        professionalAvailabilityRepository.delete(availability);
        patientRecordService.register(new PatientRecordFormDTO(formDTO.observation(),
                formDTO.appointment(),
                patient.getId(),
                professional.getId()));
        //TODO: send notification to external service
        System.out.println("New appointment: " + scheduling);
        return new SchedulingDTO(scheduling);
    }

    public List<SchedulingDTO> findAllByPatientId(Long id, Optional<LocalDate> date) {
        return date.map(d -> schedulingRepository.findAllByPatient_IdAndDate(id, d))
                .orElseGet(() -> schedulingRepository.findAllByPatient_Id(id))
                .stream().map(SchedulingDTO::new).toList();
    }

    public List<SchedulingDTO> findAllByProfessionalId(Long id, Optional<LocalDate> date) {
        return date.map(d -> schedulingRepository.findAllByProfessional_IdAndDate(id, d))
                .orElseGet(() -> schedulingRepository.findAllByProfessional_Id(id))
                .stream().map(SchedulingDTO::new).toList();
    }

    public Scheduling findById(Long id) {
        return schedulingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
    }

    @Transactional
    public SchedulingDTO update(Long id, SchedulingFormDTO formDTO){
        Scheduling scheduling = findById(id);
        Patient patient = patientService.findByDocumentOrCreate(formDTO.patientName(), formDTO.patientDocument());
        Procedure procedure = procedureService.findById(formDTO.procedureId());
        Professional professional = professionalService.findProfessionalById(formDTO.professionalId());
        scheduling.merge(new SchedulingUpdateDTO(patient, procedure, professional, formDTO.appointment(), formDTO.status()));
        return new SchedulingDTO(scheduling);
    }

    /*
     * A expressão cron "0 0/15 9-20 * * MON-SAT" significa:
     * 0: No início do minuto (segundos).
     * 0/15: A cada 15 minutos.
     * 9-20: Entre as 9 da manhã e as 8 da noite (horas).
     * *: Qualquer dia do mês.
     * *: Qualquer mês.
     * MON-SAT: De segunda a sábado.
     * O método checkAppointments será executado a cada 15 minutos de segunda a sábado, das 09:00 da manhã às 20:00 da noite.
     * */
//    @Scheduled(cron = "0 0/15 9-20 * * MON-SAT")
    @Async
    @Transactional
    @Scheduled(cron = "*/5 * * * * *") //Apenas para testes locais, roda a cada 5s
    public void checkLateAppointments() {
        LocalDateTime now = LocalDateTime.now();
        List<Scheduling> lateSchedules = schedulingRepository.findAllByAppointmentsToDay(now.toLocalDate())
                .stream().filter(scheduling -> scheduling.getAppointment().isBefore(now)).toList();
        lateSchedules.forEach(scheduling -> {
            scheduling.late();
            patientRecordService.findLastByPatientId(scheduling.getPatientId()).isLate();
            //TODO: send notification to external service
            System.out.println("Late appointment: " + scheduling);
        });
    }

    /*
     * A expressão cron "0 0 9-20 * * MON-SAT" significa:
     * 0: No início do minuto (segundos).
     * 0: No início da hora (minutos).
     * 9-20: Entre as 9 da manhã e as 8 da noite (horas).
     * *: Qualquer dia do mês.
     * *: Qualquer mês.
     * MON-SAT: De segunda a sábado.
     * O método será executado a cada hora, de segunda a sábado, das 9 da manhã às 8 da noite.
     * */
//    @Scheduled(cron = "0 0 9-20 * * MON-SAT")
    @Async
    @Transactional
    @Scheduled(cron = "*/20 * * * * *") //Apenas para testes locais, roda a cada 20s
    public void checkLateSchedulesHourly() {
        List<Scheduling> lateSchedules = schedulingRepository.findAllByStatus(LATE);
        lateSchedules.forEach(scheduling -> {
            scheduling.cancel();
            patientRecordService.findLastByPatientId(scheduling.getPatient().getId()).isCanceled();
            //TODO: send notification to external service
            System.out.println("Canceled appointment: " + scheduling);
        });
    }

    /*
     * A expressão cron "0 0 9-20 * * MON-SAT" significa:
     * 0: No início do minuto (segundos).
     * 0: No início da hora (minutos).
     * 20: às 8 da noite.
     * *: Qualquer dia do mês.
     * *: Qualquer mês.
     * MON-SAT: De segunda a sábado.
     * O método será executado uma vez por dia, de segunda a sábado, às 20:00 (8 da noite).
     * */
    @Async
    @Transactional
    @Scheduled(cron = "0 0 20 * * MON-SAT") // Executa uma vez por dia, de segunda a sábado, das 09:00 às 20:00
    public void checkAppointmentsInNext24Hours() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);
        List<Scheduling> upcomingSchedules = schedulingRepository.findAllByAppointmentBetween(now, next24Hours);
        upcomingSchedules.forEach(scheduling -> {
            // TODO: send notification to external service
            System.out.println("Upcoming appointment in next 24 hours: " + scheduling);
        });
    }

    //TODO: Implementar lógica de reagendamento de consultas (permitir com no mínimo 6hrs de antecedência)
}
