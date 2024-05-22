package br.com.odontoflow.application.patient;

import br.com.odontoflow.domain.patient.PatientRecordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/patient-record")
public class PatientRecordController {

    private final PatientRecordService patientRecordService;

    public PatientRecordController(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }

    @PostMapping("/create")
    public ResponseEntity<PatientRecordDTO> register(@RequestBody @Valid PatientRecordFormDTO formDTO) {
        return ResponseEntity.status(CREATED).body(patientRecordService.register(formDTO));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<PatientRecordDTO> searchById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(patientRecordService.findById(id));
    }

    @GetMapping("/list/patient/{id}")
    public ResponseEntity<List<PatientRecordDTO>> listByPatientId(@PathVariable("id") UUID patientId) {
        return ResponseEntity.ok(patientRecordService.findByPatientId(patientId));
    }

    @GetMapping("/list/professional/{id}")
    public ResponseEntity<List<PatientRecordDTO>> listByProfessionalId(@PathVariable("id") UUID professionalId) {
        return ResponseEntity.ok(patientRecordService.findByProfessionalId(professionalId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientRecordDTO> updatePatientRecord(@PathVariable("id") UUID id,
                                                                @RequestBody @Valid PatientRecordFormDTO formDTO) {
        return ResponseEntity.ok(patientRecordService.update(id, formDTO));
    }
}
