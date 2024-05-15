package br.com.odontoflow.domain.scheduler;

import br.com.odontoflow.application.scheduler.SchedulerDTO;
import br.com.odontoflow.application.scheduler.SchedulerRegisterFormDTO;
import br.com.odontoflow.domain.patient.PatientService;
import br.com.odontoflow.domain.procedure.ProcedureService;
import br.com.odontoflow.domain.professional.ProfessionalService;
import br.com.odontoflow.domain.procedure.Procedure;
import br.com.odontoflow.infrastructure.scheduler.SchedulerRepository;
import br.com.odontoflow.domain.patient.Patient;
import br.com.odontoflow.domain.professional.Professional;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    private final PatientService patientService;
    private final ProcedureService procedureService;
    private final SchedulerRepository schedulerRepository;
    private final ProfessionalService professionalService;

    public SchedulerService(SchedulerRepository schedulerRepository,
                            PatientService patientService,
                            ProfessionalService professionalService,
                            ProcedureService procedureService) {
        this.schedulerRepository = schedulerRepository;
        this.patientService = patientService;
        this.professionalService = professionalService;
        this.procedureService = procedureService;
    }

    public SchedulerDTO schedulerRegister(SchedulerRegisterFormDTO schedulerRegisterFormDTO) {
        Patient patient = patientService.findUserById(schedulerRegisterFormDTO.userId());
        Procedure procedure = procedureService.findById(schedulerRegisterFormDTO.procedureId());
        Professional professional = professionalService.findProfessionalById(schedulerRegisterFormDTO.professionalId());
        Scheduler scheduler = new Scheduler(patient, procedure, professional, schedulerRegisterFormDTO.appointment());
        schedulerRepository.save(scheduler);
        return new SchedulerDTO(scheduler);
    }
}
