package br.com.odontoflow.application.patient;

import br.com.odontoflow.domain.patient.Patient;

import java.util.UUID;


public record PatientDTO(UUID id, String name, String document, String phone) {

    public PatientDTO(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getDocument(), patient.getPhone());
    }
}
