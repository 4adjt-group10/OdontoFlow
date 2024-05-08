package br.com.odontologia.demo.service;

import br.com.odontologia.demo.domain.Procedure;
import br.com.odontologia.demo.repository.ProcedureRepository;
import br.com.odontologia.demo.dto.procedure.ProcedureDTO;
import br.com.odontologia.demo.dto.procedure.ProcedureFormDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return procedureRepository.findByNome(name).orElseThrow(() -> new EntityNotFoundException("Procedure not found"));
    }

    public Procedure findById(Long id) {
        return procedureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Procedure not found"));
    }
}
