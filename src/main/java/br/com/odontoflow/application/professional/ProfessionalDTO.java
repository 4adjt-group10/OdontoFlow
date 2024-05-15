package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.ProfessionalType;
import br.com.odontoflow.domain.professional.Professional;

public record ProfessionalDTO(String name, String document, ProfessionalType type) {

    public ProfessionalDTO(Professional professional) {
        this(professional.getName(), professional.getDocument(), professional.getType());
    }
}
