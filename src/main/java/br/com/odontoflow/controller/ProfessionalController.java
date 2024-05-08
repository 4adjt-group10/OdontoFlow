package br.com.odontoflow.controller;

import br.com.odontoflow.dto.professional.ProfessionalDTO;
import br.com.odontoflow.dto.professional.ProfessionalFormDTO;
import br.com.odontoflow.service.ProfessionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
