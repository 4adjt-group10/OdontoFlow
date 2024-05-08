package br.com.odontologia.demo.controller;

import br.com.odontologia.demo.dto.patient.PatientFormDTO;
import br.com.odontologia.demo.dto.patient.PatientDTO;
import br.com.odontologia.demo.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController()
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
