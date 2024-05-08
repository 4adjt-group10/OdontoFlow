package br.com.odontologia.demo.domain;

import br.com.odontologia.demo.domain.professional.Professional;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Scheduler")
//TODO: Rename to schedule or appointment
public class Scheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;
    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;
    private LocalDateTime appointment;

    @Deprecated(since = "Only for use of frameworks")
    public Scheduler() {
    }

    public Scheduler(Patient patient, Procedure procedure, Professional professional, LocalDateTime appointment) {
        this.patient = patient;
        this.procedure = procedure;
        this.professional = professional;
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public Patient getUser() {
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

    public String getUserName() {
        return patient.getName();
    }

    public String getProcedureName() {
        return procedure.getNome();
    }

    public String getProfessionalName() {
        return professional.getName();
    }
}
