package br.com.odontoflow.application.patient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/patient-record")
public class PatientRecordController {

    private final PatientRecordService patientRecordService;

    public PatientRecordController(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }

    @PostMapping("/create")
    public ResponseEntity<PatientRecordDTO> register(@RequestBody PatientRecordFormDTO formDTO) {
        return new ResponseEntity<>(patientRecordService.register(formDTO), CREATED);
    }

    @GetMapping("/search/{id}")
    public PatientRecordDTO searchById(@PathVariable("id") Long patientId) {
        return patientRecordService.findById(patientId);
    }

    @GetMapping("/list/patient/{id}")
    public List<PatientRecordDTO> listByPatientId(@PathVariable("id") Long patientId) {
        return patientRecordService.findByPatientId(patientId);
    }

    @GetMapping("/list/professional/{id}")
    public List<PatientRecordDTO> listByProfessionalId(@PathVariable("id") Long professionalId) {
        return patientRecordService.findByProfessionalId(professionalId);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody PatientRecordFormDTO formDTO) {
        patientRecordService.update(id, formDTO);
    }
}
