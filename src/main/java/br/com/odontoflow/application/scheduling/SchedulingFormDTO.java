package br.com.odontoflow.application.scheduling;

import br.com.odontoflow.domain.scheduling.SchedulingStatus;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public record SchedulingFormDTO(
        String patientName,
        String patientDocument,
        Long procedureId,
        Long professionalId,
        String appointment,
        SchedulingStatus status,
        String observation
) {
    public LocalDateTime getAppointment() {
        return parse(appointment, ofPattern("dd-MM-yyy HH:mm:ss"));
    }
}
