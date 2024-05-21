package br.com.odontoflow.application.procedure;

import br.com.odontoflow.domain.procedure.ProcedureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
@RestController
@RequestMapping("/procedure")
public class ProcedureController {

    private final ProcedureService procedureService;

    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProcedureDTO> procedureRegister(@RequestBody @Valid ProcedureFormDTO procedureFormDTO){
        return ResponseEntity.status(CREATED).body(procedureService.createProcedure(procedureFormDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProcedureDTO>> findAllProcedures(){
        return ResponseEntity.ok(procedureService.findAll());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ProcedureDTO> findProcedureForName(@PathVariable("name") String name){
        return ResponseEntity.ok(procedureService.findByName(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProcedureDTO> updateProcedure(@PathVariable("id") Long id,
                                                        @RequestBody @Valid ProcedureFormDTO procedureFormDTO){
        return ResponseEntity.ok(procedureService.update(id, procedureFormDTO));
    }
}
