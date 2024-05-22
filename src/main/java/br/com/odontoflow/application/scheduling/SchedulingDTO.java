package br.com.odontoflow.application.scheduling;

import br.com.odontoflow.domain.scheduling.Scheduling;
import br.com.odontoflow.domain.scheduling.SchedulingStatus;


public record SchedulingDTO(
        Long id,
        String patientName,
        String procedureName,
        String professionalName,
        String appointment,
        SchedulingStatus status
) {

    public SchedulingDTO(Scheduling scheduling) {
        this(scheduling.getId(),
                scheduling.getPatientName(),
                scheduling.getProcedureName(),
                scheduling.getProfessionalName(),
                scheduling.getAppointmentFormated(),
                scheduling.getStatus());
    }

}
