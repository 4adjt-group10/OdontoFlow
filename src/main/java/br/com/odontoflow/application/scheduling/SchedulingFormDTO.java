package br.com.odontoflow.application.scheduling;

import br.com.odontoflow.domain.scheduling.SchedulingStatus;

import java.time.LocalDateTime;

public record SchedulingFormDTO(
        String patientName,
        String patientDocument,
        Long procedureId,
        Long professionalId,
        LocalDateTime appointment,
        SchedulingStatus status,
        String observation
) {

}
