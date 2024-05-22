package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.domain.professional.ProfessionalType;

import java.util.List;
import java.util.UUID;

public record ProfessionalDTO(UUID id, String name, String document, ProfessionalType type, List<String> procedures) {

    public ProfessionalDTO(Professional professional) {
        this(professional.getId(),
                professional.getName(),
                professional.getDocument(),
                professional.getType(),
                professional.getProceduresNames());
    }
}
