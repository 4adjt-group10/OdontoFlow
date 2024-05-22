package br.com.odontoflow.application.professional;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProfessionalAvailabilityFormDTO(@NotNull UUID professionalId,
                                              @NotNull LocalDateTime availableTime) {

}
