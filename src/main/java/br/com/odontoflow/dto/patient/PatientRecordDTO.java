package br.com.odontoflow.dto.patient;

import br.com.odontoflow.domain.PatientRecord;
import br.com.odontoflow.dto.professional.ProfessionalDTO;

import java.time.LocalDateTime;

public record PatientRecordDTO(String description, LocalDateTime date, PatientDTO patient, ProfessionalDTO professional) {

    public PatientRecordDTO(PatientRecord patientRecord) {
        this(patientRecord.getDescription(), patientRecord.getDate(), new PatientDTO(patientRecord.getPatient()), new ProfessionalDTO(patientRecord.getProfessional()));
    }
}
