package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.ProfessionalAvailability;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProfessionalAvailabilityDTO(UUID id, String professionalName, LocalDateTime availableTime) {

    public ProfessionalAvailabilityDTO(ProfessionalAvailability professionalAvailability) {
        this(professionalAvailability.getId(),
                professionalAvailability.getProfessionalName(),
                professionalAvailability.getAvailableTime());
    }
}
