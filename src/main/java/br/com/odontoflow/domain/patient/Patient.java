package br.com.odontoflow.domain.patient;

import br.com.odontoflow.application.patient.PatientFormDTO;
import br.com.odontoflow.domain.address.Address;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "document", length = 11, nullable = false, unique = true)
    private String document;
    @OneToOne
    private Address address;
    private LocalDateTime createdAt;
    //TODO: Migrar lógica para scheduler
    private boolean late;

    public Patient(PatientFormDTO patientFormDTO) {
        this.name = patientFormDTO.name();
        this.document = patientFormDTO.document();
        this.createdAt = LocalDateTime.now();
    }

    @Deprecated(since = "Only for use of frameworks")
    public Patient() {

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isLate() {
        return late;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void merge(PatientFormDTO patientFormDTO) {
        this.name = patientFormDTO.name();
        this.document = patientFormDTO.document();
        this.address.merge(patientFormDTO.address());
    }
}
