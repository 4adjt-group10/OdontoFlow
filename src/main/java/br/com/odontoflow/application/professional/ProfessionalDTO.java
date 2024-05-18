package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.ProfessionalType;
import br.com.odontoflow.domain.professional.Professional;

import java.util.List;

public record ProfessionalDTO(String name, String document, ProfessionalType type, List<String> procedures) {

    public ProfessionalDTO(Professional professional) {
        this(professional.getName(),
            professional.getDocument(),
            professional.getType(),
            professional.getProceduresNames());
    }
}
