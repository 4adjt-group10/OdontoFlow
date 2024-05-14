package br.com.odontoflow.controller;

import br.com.odontoflow.dto.patient.PatientDTO;
import br.com.odontoflow.dto.patient.PatientFormDTO;
import br.com.odontoflow.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

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
    public PatientDTO search(@PathVariable("id") Long userId) {
        return new PatientDTO(patientService.findUserById(userId));
    }

    @GetMapping("/list")
    public List<PatientDTO> listAll() {
        return patientService.listAllUsers();
    }
}
