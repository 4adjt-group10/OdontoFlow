package br.com.odontoflow.application.patient;

import java.time.LocalDateTime;

public record PatientRecordFormDTO(String description, LocalDateTime date, Long patientId, Long professionalId) {

}
