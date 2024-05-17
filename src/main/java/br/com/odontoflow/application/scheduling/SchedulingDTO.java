package br.com.odontoflow.application.scheduling;

import br.com.odontoflow.domain.scheduling.Scheduling;
import br.com.odontoflow.domain.scheduling.SchedulingStatus;

import java.time.LocalDateTime;

public record SchedulingDTO(String userName, String procedureName, String professionalName, LocalDateTime appointment, SchedulingStatus status) {

    public SchedulingDTO(Scheduling scheduling) {
        this(scheduling.getUserName(), scheduling.getProcedureName(), scheduling.getProfessionalName(), scheduling.getAppointment(), scheduling.getStatus());
    }
}
