package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.ProfessionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessionalDTO> professionalRegister(@RequestBody ProfessionalFormDTO professionalFormDTO){
        return new ResponseEntity<>(professionalService.register(professionalFormDTO), CREATED);
    }

    @GetMapping("/list")
    public List<ProfessionalDTO> listAll() {
        return professionalService.listAllProfessionals();
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody ProfessionalFormDTO professionalFormDTO) {
        professionalService.update(id, professionalFormDTO);
    }
}
