package br.com.odontoflow.application.scheduling;

import br.com.odontoflow.domain.scheduling.SchedulingStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SchedulingFormDTO(
        @NotBlank String patientName,
        @NotBlank String patientDocument,
        @NotBlank String phone,
        @NotNull Long procedureId,
        @NotNull Long professionalId,
        @NotNull @FutureOrPresent LocalDateTime appointment,
        @NotNull SchedulingStatus status,
        @Nullable String observation
) {

}
