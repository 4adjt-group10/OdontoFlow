package br.com.odontoflow.application.patient;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public record PatientRecordFormDTO(String description, String date, Long patientId, Long professionalId) {

    public LocalDateTime getDate() {
        return parse(date, ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

}
