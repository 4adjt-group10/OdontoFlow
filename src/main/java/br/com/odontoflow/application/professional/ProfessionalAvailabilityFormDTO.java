package br.com.odontoflow.application.professional;

import java.time.LocalDateTime;

public record ProfessionalAvailabilityFormDTO(Long professionalId, LocalDateTime availableTime) {

}
