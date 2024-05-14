package br.com.odontoflow.service;

import br.com.odontoflow.controller.exception.ControllerNotFoundException;
import br.com.odontoflow.domain.Procedure;
import br.com.odontoflow.dto.procedure.ProcedureDTO;
import br.com.odontoflow.dto.procedure.ProcedureFormDTO;
import br.com.odontoflow.repository.ProcedureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureService {

    private final ProcedureRepository procedureRepository;

    public ProcedureService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    public ProcedureDTO createProcedure(ProcedureFormDTO procedureFormDTO) {
        Procedure procedure = new Procedure(procedureFormDTO);
        procedureRepository.save(procedure);
        return new ProcedureDTO(procedure);
    }

    public List<ProcedureDTO> findAll() {
        List<Procedure> allProcedures = procedureRepository.findAll();
        return allProcedures.stream().map(ProcedureDTO::new).toList();
    }

    public Procedure findForName(String name) {
        return procedureRepository.findByNome(name).orElseThrow(() -> new ControllerNotFoundException("Procedure not found"));
    }

    public Procedure findById(Long id) {
        return procedureRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Procedure not found"));
    }
}
