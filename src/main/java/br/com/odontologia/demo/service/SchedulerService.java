package br.com.odontologia.demo.service;

import br.com.odontologia.demo.dto.scheduler.SchedulerDTO;
import br.com.odontologia.demo.domain.Patient;
import br.com.odontologia.demo.domain.Procedure;
import br.com.odontologia.demo.domain.professional.Professional;
import br.com.odontologia.demo.domain.Scheduler;
import br.com.odontologia.demo.dto.scheduler.SchedulerRegisterFormDTO;
import br.com.odontologia.demo.repository.SchedulerRepository;
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
