package br.com.odontoflow.application.patient;

import br.com.odontoflow.domain.patient.Patient;


public record PatientDTO(String name, String document) {

    public PatientDTO(Patient patient) {
        this(patient.getName(), patient.getDocument());
    }
}
