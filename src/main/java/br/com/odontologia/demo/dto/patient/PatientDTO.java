package br.com.odontologia.demo.dto.patient;

import br.com.odontologia.demo.domain.Patient;


public record PatientDTO(String name, String document, String patientRecords) {

    public PatientDTO(Patient patient) {
        this(patient.getName(), patient.getDocument(), patient.getPatientRecords());
    }
}
