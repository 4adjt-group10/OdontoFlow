package br.com.odontoflow.domain.patient;

import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.application.patient.PatientRecordFormDTO;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class PatientRecord {

    @Id
    private UUID id;
    @Column(columnDefinition = "TEXT", length = 1000)
    private String description;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @Deprecated(since = "Only for use of frameworks")
    public PatientRecord() {
    }

    public PatientRecord(String description, LocalDateTime date, Patient patient, Professional professional) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.date = date;
        this.patient = patient;
        this.professional = professional;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Patient getPatient() {
        return patient;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void merge(PatientRecordFormDTO formDTO) {
        this.description = formDTO.description();
        this.date = formDTO.date();
    }

    public void isLate() {
        this.description = description.concat("  OBS: Agendamento com atraso do paciente.");
    }

    public void isCanceled() {
        this.description = "OBS: Agendamento cancelado por atraso do paciente.";
    }
}
