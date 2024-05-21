package br.com.odontoflow.application.patient;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PatientRecordFormDTO(
        @Nullable String description,
        @NotNull LocalDateTime date,
        @NotNull Long patientId,
        @NotNull Long professionalId
) {

}
