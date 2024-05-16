package br.com.odontoflow.application.scheduling;

import br.com.odontoflow.domain.patient.Patient;
import br.com.odontoflow.domain.procedure.Procedure;
import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.domain.scheduling.SchedulingStatus;

import java.time.LocalDateTime;

public record SchedulingUpdateDTO(
        Patient patient,
        Procedure procedure,
        Professional professional,
        LocalDateTime appointment,
        SchedulingStatus status
) {

}
