package br.com.odontoflow.controller;

import br.com.odontoflow.dto.patient.PatientDTO;
import br.com.odontoflow.dto.patient.PatientFormDTO;
import br.com.odontoflow.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> register(@RequestBody PatientFormDTO patientFormDTO) {
        return new ResponseEntity<>(patientService.register(patientFormDTO), CREATED);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<PatientDTO> search(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(new PatientDTO(patientService.findUserById(userId)), OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientDTO>> listAll() {
        return new ResponseEntity<>(patientService.listAllUsers(), OK);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody PatientFormDTO patientFormDTO) {
        patientService.update(id, patientFormDTO);
    }
}
