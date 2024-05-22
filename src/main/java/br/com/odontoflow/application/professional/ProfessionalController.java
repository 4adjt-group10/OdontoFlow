package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessionalDTO> professionalRegister(@RequestBody @Valid ProfessionalFormDTO professionalFormDTO){
        return ResponseEntity.status(CREATED).body(professionalService.register(professionalFormDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProfessionalDTO>> listAll() {
        return ResponseEntity.ok(professionalService.listAllProfessionals());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfessionalDTO> update(@PathVariable("id") UUID id, @RequestBody @Valid ProfessionalFormDTO professionalFormDTO) {
        return ResponseEntity.ok(professionalService.update(id, professionalFormDTO));
    }

}
