package br.com.odontoflow.service;

import br.com.odontoflow.domain.Procedure;
import br.com.odontoflow.domain.Scheduler;
import br.com.odontoflow.repository.SchedulerRepository;
import br.com.odontoflow.dto.scheduler.SchedulerDTO;
import br.com.odontoflow.domain.Patient;
import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.dto.scheduler.SchedulerRegisterFormDTO;
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
