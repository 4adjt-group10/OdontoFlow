package br.com.odontoflow.domain.patient;

import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.application.patient.PatientRecordFormDTO;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
public class PatientRecord {
    //TODO: Mudar ids para UUID?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
        this.description = description;
        this.date = date;
        this.patient = patient;
        this.professional = professional;
    }

    public Long getId() {
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
        this.date = formDTO.getDate();
    }
}
