package br.com.odontoflow.domain.scheduling;

import br.com.odontoflow.application.PatientException;
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
import br.com.odontoflow.domain.professional.ProfessionalAvailabilityService;
import br.com.odontoflow.domain.professional.ProfessionalService;
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
import java.util.UUID;

import static br.com.odontoflow.domain.scheduling.SchedulingStatus.*;

@Service
public class SchedulingService {

    private final PatientService patientService;
    private final ProcedureService procedureService;
    private final SchedulingRepository schedulingRepository;
    private final ProfessionalService professionalService;
    private final PatientRecordService patientRecordService;
    private final ProfessionalAvailabilityService professionalAvailabilityService;

    public SchedulingService(SchedulingRepository schedulingRepository,
                             PatientService patientService,
                             ProfessionalService professionalService,
                             ProcedureService procedureService,
                             PatientRecordService patientRecordService,
                             ProfessionalAvailabilityService professionalAvailabilityService) {
        this.schedulingRepository = schedulingRepository;
        this.patientService = patientService;
        this.professionalService = professionalService;
        this.procedureService = procedureService;
        this.patientRecordService = patientRecordService;
        this.professionalAvailabilityService = professionalAvailabilityService;
    }

    @Transactional
    public SchedulingDTO register(SchedulingFormDTO formDTO) {
        Patient patient = patientService.findByDocumentOrCreate(formDTO.patientName(), formDTO.patientDocument(), formDTO.patientPhone());
        if(patient.isBlocked()) {
            throw new PatientException("Patient is blocked");
        }
        Procedure procedure = procedureService.findById(formDTO.procedureId());
        Professional professional = professionalService.findProfessionalById(formDTO.professionalId());
        ProfessionalAvailability availability = professionalAvailabilityService
                .findAvailabilityByProfessionalAndAvailableTime(professional.getId(), formDTO.appointment());
        Scheduling scheduling = new Scheduling(patient, procedure, professional, formDTO.appointment(), formDTO.status());
        schedulingRepository.save(scheduling);
        professionalAvailabilityService.deleteAvailability(availability);
        patientRecordService.register(new PatientRecordFormDTO(formDTO.observation(),
                formDTO.appointment(),
                patient.getId(),
                professional.getId()));
        //TODO: send notification to external service
        System.out.println("New appointment: " + scheduling);
        return new SchedulingDTO(scheduling);
    }

    public List<SchedulingDTO> findAllByPatientId(UUID id, Optional<LocalDate> date) {
        return date.map(d -> schedulingRepository.findAllByPatientIdAndDate(id, d))
                .orElseGet(() -> schedulingRepository.findAllByPatient_Id(id))
                .stream().map(SchedulingDTO::new).toList();
    }

    public List<SchedulingDTO> findAllByProfessionalId(UUID id, Optional<LocalDate> date) {
        return date.map(d -> schedulingRepository.findAllByProfessionalIdAndDate(id, d))
                .orElseGet(() -> schedulingRepository.findAllByProfessional_Id(id))
                .stream().map(SchedulingDTO::new).toList();
    }

    public Scheduling findById(UUID id) {
        return schedulingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
    }

    @Transactional
    public SchedulingDTO update(UUID id, SchedulingFormDTO formDTO){
        Scheduling scheduling = findById(id);
        Patient patient = patientService.findByDocumentOrCreate(formDTO.patientName(), formDTO.patientDocument(), formDTO.patientPhone());
        Procedure procedure = procedureService.findById(formDTO.procedureId());
        Professional professional = professionalService.findProfessionalById(formDTO.professionalId());
        scheduling.merge(new SchedulingUpdateDTO(patient, procedure, professional, formDTO.appointment(), formDTO.status()));
        return new SchedulingDTO(scheduling);
    }

    public SchedulingDTO done(UUID id) {
        Scheduling scheduling = findById(id);
        scheduling.done();
        schedulingRepository.save(scheduling);
        return new SchedulingDTO(scheduling);
    }

    /**
     * This method checks for late appointments and updates their status accordingly.
     * {@code @Scheduled(cron = "0 0/15 9-20 * * MON-SAT")} means this method will be executed every 15 minutes from Monday to Saturday, between 9 AM and 8 PM.
     */
    @Async
    @Transactional
    @Scheduled(cron = "0 0/15 9-20 * * MON-SAT")
    public void checkLateAppointments() {
        LocalDateTime now = LocalDateTime.now();
        List<Scheduling> lateSchedules = schedulingRepository
                .findAllByAppointmentsToDay(now.toLocalDate())
                .stream()
                .filter(scheduling -> scheduling.getAppointment().isBefore(now)
                        && (scheduling.hasStatus(SCHEDULED) || scheduling.hasStatus(RESCHEDULED)))
                .toList();
        lateSchedules.forEach(scheduling -> {
            scheduling.late();
            patientRecordService.findLastByPatientId(scheduling.getPatientId()).isLate();
            //TODO: send notification to external service
            System.out.println("Late appointment: " + scheduling);
        });
    }

    /**
     * This method checks for late schedules and updates their status to canceled.
     * {@code @Scheduled(cron = "0 0 9-20 * * MON-SAT")} means this method will be executed every hour from Monday to Saturday, between 9 AM and 8 PM.
     */
    @Async
    @Transactional
    @Scheduled(cron = "0 0 9-20 * * MON-SAT")
    public void checkLateSchedulesHourly() {
        List<Scheduling> lateSchedules = schedulingRepository.findAllByStatus(LATE);
        lateSchedules.forEach(scheduling -> {
            scheduling.cancel();
            scheduling.getPatient().block();
            schedulingRepository.save(scheduling);
            patientRecordService.findLastByPatientId(scheduling.getPatient().getId()).isCanceled();
            //TODO: send notification to external service
            System.out.println("Canceled appointment: " + scheduling);
        });
    }

    /**
     * This method checks for appointments scheduled in the next 24 hours and calls an external service to send patient notification.
     * {@code @Scheduled(cron = "0 0 20 * * MON-SAT")} means this method will be executed once per day from Monday to Saturday, at 8 PM.
     */
    @Async
    @Transactional
    @Scheduled(cron = "0 0 20 * * MON-SAT")
    public void checkAppointmentsInNext24Hours() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);
        List<Scheduling> upcomingSchedules = schedulingRepository
                .findAllByAppointmentBetweenAndStatusIn(now, next24Hours, List.of(SCHEDULED, RESCHEDULED));
        upcomingSchedules.forEach(scheduling -> {
            // TODO: send notification to external service
            System.out.println("Upcoming appointment in next 24 hours: " + scheduling);
        });
    }

}
