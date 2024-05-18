package br.com.odontoflow.application.scheduling;

import br.com.odontoflow.domain.scheduling.Scheduling;
import br.com.odontoflow.domain.scheduling.SchedulingStatus;

import java.time.LocalDateTime;

public record SchedulingDTO(String patientName, String procedureName, String professionalName, String appointment, SchedulingStatus status) {

    public SchedulingDTO(Scheduling scheduling) {
        this(scheduling.getPatientName(), scheduling.getProcedureName(), scheduling.getProfessionalName(), scheduling.getAppointmentFormated(), scheduling.getStatus());
    }

}
