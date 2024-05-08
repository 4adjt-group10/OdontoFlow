package br.com.odontologia.demo.dto.scheduler;

import br.com.odontologia.demo.domain.Scheduler;

import java.time.LocalDateTime;

public record SchedulerDTO(String userName, String procedureName, String professionalName, LocalDateTime appointment) {


    public SchedulerDTO(Scheduler scheduler) {
        this(scheduler.getUserName(), scheduler.getProcedureName(), scheduler.getProfessionalName(), scheduler.getAppointment());
    }
}
