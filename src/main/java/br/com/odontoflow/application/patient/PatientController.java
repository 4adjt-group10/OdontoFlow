package br.com.odontoflow.application.patient;

import br.com.odontoflow.domain.patient.PatientService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PatientDTO> register(@RequestBody @Valid PatientFormDTO patientFormDTO) {
        return ResponseEntity.status(CREATED).body(patientService.register(patientFormDTO));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<PatientDTO> search(@PathVariable("id") Long patientId) {
        return ResponseEntity.ok(new PatientDTO(patientService.findPatientById(patientId)));
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientDTO>> listAll() {
        return ResponseEntity.ok(patientService.listAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientDTO> update(@PathVariable("id") Long id, @RequestBody @Valid PatientFormDTO patientFormDTO) {
        return ResponseEntity.ok(patientService.update(id, patientFormDTO));
    }
}
