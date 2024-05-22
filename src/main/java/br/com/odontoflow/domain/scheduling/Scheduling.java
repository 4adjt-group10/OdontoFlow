package br.com.odontoflow.domain.scheduling;

import br.com.odontoflow.application.RescheduleException;
import br.com.odontoflow.application.scheduling.SchedulingUpdateDTO;
import br.com.odontoflow.domain.patient.Patient;
import br.com.odontoflow.domain.procedure.Procedure;
import br.com.odontoflow.domain.professional.Professional;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static br.com.odontoflow.domain.scheduling.SchedulingStatus.*;
import static java.time.LocalDateTime.now;

@Entity
@Table(name = "Scheduling")
public class Scheduling {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;
    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;
    //TODO: renomear?
    private LocalDateTime appointment;
    @Enumerated(EnumType.STRING)
    private SchedulingStatus status;

    @Deprecated(since = "Only for use of frameworks")
    public Scheduling() {
    }

    public Scheduling(Patient patient,
                      Procedure procedure,
                      Professional professional,
                      LocalDateTime appointment,
                      SchedulingStatus status) {
        this.id = UUID.randomUUID();
        this.patient = patient;
        this.procedure = procedure;
        this.professional = professional;
        this.appointment = appointment;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public Professional getProfessional() {
        return professional;
    }

    public LocalDateTime getAppointment() {
        return appointment;
    }

    public String getPatientName() {
        return patient.getName();
    }

    public String getProcedureName() {
        return procedure.getName();
    }

    public String getProfessionalName() {
        return professional.getName();
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public UUID getPatientId() {
        return this.patient.getId();
    }

    public String getAppointmentFormated() {
        return appointment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public boolean hasStatus(SchedulingStatus status) {
        return this.status.equals(status);
    }

    public void cancel() {
        this.status = CANCELED;
    }

    public void done() {
        this.status = DONE;
    }

    public void late() {
        this.status = LATE;
    }

    private void reschedule(LocalDateTime newAppointment) {
        if (this.appointment.isAfter(now().plusHours(6))) {
            this.appointment = newAppointment;
        } else {
            throw new RescheduleException("It is not possible to reschedule an appointment with less than 6 hours in advance.");
        }
    }

    public void merge(SchedulingUpdateDTO updateDTO) {
        reschedule(updateDTO.appointment());
        this.patient = updateDTO.patient();
        this.procedure = updateDTO.procedure();
        this.professional = updateDTO.professional();
        this.status = updateDTO.status();
    }

}
