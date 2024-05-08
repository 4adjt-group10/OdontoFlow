package br.com.odontoflow.dto.scheduler;

import br.com.odontoflow.domain.Scheduler;

import java.time.LocalDateTime;

public record SchedulerDTO(String userName, String procedureName, String professionalName, LocalDateTime appointment) {


    public SchedulerDTO(Scheduler scheduler) {
        this(scheduler.getUserName(), scheduler.getProcedureName(), scheduler.getProfessionalName(), scheduler.getAppointment());
    }
}
