package br.com.odontoflow.application.professional;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ProfessionalAvailabilityFormDTO(@NotNull Long professionalId,
                                              @NotNull LocalDateTime availableTime) {

}
