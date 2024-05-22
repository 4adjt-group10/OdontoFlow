package br.com.odontoflow.application.patient;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record PatientRecordFormDTO(
        @Nullable String description,
        @NotNull LocalDateTime date,
        @NotNull UUID patientId,
        @NotNull UUID professionalId
) {

}
