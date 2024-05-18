package br.com.odontoflow.domain.scheduling;

import br.com.odontoflow.application.scheduling.SchedulingUpdateDTO;
import br.com.odontoflow.domain.patient.Patient;
import br.com.odontoflow.domain.procedure.Procedure;
import br.com.odontoflow.domain.professional.Professional;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import static br.com.odontoflow.domain.scheduling.SchedulingStatus.*;

@Entity
@Table(name = "Scheduling")
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
        this.patient = patient;
        this.procedure = procedure;
        this.professional = professional;
        this.appointment = appointment;
        this.status = status;
    }

    public Long getId() {
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

    public Long getPatientId() {
        return this.patient.getId();
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

    public void reschedule(LocalDateTime newAppointment) {
        this.appointment = newAppointment;
        this.status = RESCHEDULED;
    }

    public void merge (SchedulingUpdateDTO updateDTO){
        this.appointment = updateDTO.appointment();
        this.patient = updateDTO.patient();
        this.procedure = updateDTO.procedure();
        this.professional = updateDTO.professional();
        this.status = updateDTO.status();
    }
}
