package br.com.odontoflow.domain.procedure;

import br.com.odontoflow.application.ControllerNotFoundException;
import br.com.odontoflow.application.procedure.ProcedureDTO;
import br.com.odontoflow.application.procedure.ProcedureFormDTO;
import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.infrastructure.procedure.ProcedureRepository;
import br.com.odontoflow.infrastructure.professional.ProfessionalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureService {

    private final ProcedureRepository procedureRepository;
    private final ProfessionalRepository professionalRepository;

    public ProcedureService(ProcedureRepository procedureRepository, ProfessionalRepository professionalRepository) {
        this.procedureRepository = procedureRepository;
        this.professionalRepository = professionalRepository;
    }

    @Transactional
    public ProcedureDTO createProcedure(ProcedureFormDTO procedureFormDTO) {
        List<Professional> professionals = professionalRepository.findAllById(procedureFormDTO.professionalsIds());
        Procedure procedure = new Procedure(procedureFormDTO, professionals);
        procedureRepository.save(procedure);
        professionals.forEach(professional -> professional.addProcedure(procedure));
        return new ProcedureDTO(procedure);
    }

    @Transactional
    public void update(Long id, ProcedureFormDTO procedureFormDTO) {
        List<Professional> professionals = professionalRepository.findAllById(procedureFormDTO.professionalsIds());
        Procedure procedure = findById(id);
        procedure.merge(procedureFormDTO, professionals);
        professionals.forEach(professional -> professional.addProcedure(procedure));
    }

    public List<ProcedureDTO> findAll() {
        List<Procedure> allProcedures = procedureRepository.findAll();
        return allProcedures.stream().map(ProcedureDTO::new).toList();
    }

    public Procedure findForName(String name) {
        return procedureRepository.findByName(name).orElseThrow(() -> new ControllerNotFoundException("Procedure not found"));
    }

    public Procedure findById(Long id) {
        return procedureRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Procedure not found"));
    }
}
