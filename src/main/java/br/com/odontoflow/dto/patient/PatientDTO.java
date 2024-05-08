package br.com.odontoflow.dto.patient;

import br.com.odontoflow.domain.Patient;


public record PatientDTO(String name, String document, String patientRecords) {

    public PatientDTO(Patient patient) {
        this(patient.getName(), patient.getDocument(), patient.getPatientRecords());
    }
}
