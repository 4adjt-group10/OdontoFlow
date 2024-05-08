package br.com.odontoflow.dto.scheduler;

import java.time.LocalDateTime;

public record SchedulerRegisterFormDTO(Long userId, Long procedureId, Long professionalId, LocalDateTime appointment) {


}
