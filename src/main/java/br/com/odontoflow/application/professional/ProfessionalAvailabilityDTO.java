package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.ProfessionalAvailability;

import java.time.LocalDateTime;

public record ProfessionalAvailabilityDTO(String professionalName, LocalDateTime availableTime) {

    public ProfessionalAvailabilityDTO(ProfessionalAvailability professionalAvailability) {
        this(professionalAvailability.getProfessionalName(), professionalAvailability.getAvailableTime());
    }
}
