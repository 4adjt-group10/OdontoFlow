package br.com.odontologia.demo.dto.professional;

import br.com.odontologia.demo.domain.professional.Professional;
import br.com.odontologia.demo.domain.professional.ProfessionalType;

public record ProfessionalDTO(String name, String document, ProfessionalType type) {

    public ProfessionalDTO(Professional professional) {
        this(professional.getName(), professional.getDocument(), professional.getType());
    }
}
