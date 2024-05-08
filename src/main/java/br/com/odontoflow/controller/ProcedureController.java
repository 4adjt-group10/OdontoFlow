package br.com.odontoflow.controller;

import br.com.odontoflow.dto.procedure.ProcedureDTO;
import br.com.odontoflow.dto.procedure.ProcedureFormDTO;
import br.com.odontoflow.service.ProcedureService;
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
    public ResponseEntity<ProcedureDTO> procedureRegister(@RequestBody ProcedureFormDTO procedureFormDTO){
        return new ResponseEntity<>(procedureService.createProcedure(procedureFormDTO), CREATED);
    }

    @GetMapping("/list")
    public List<ProcedureDTO> findAllProcedures(){
        return procedureService.findAll();
    }

    @GetMapping("/search/{name}")
    public ProcedureDTO findProcedureForName(@PathVariable("name") String name){
        return new ProcedureDTO(procedureService.findForName(name));
    }
}
