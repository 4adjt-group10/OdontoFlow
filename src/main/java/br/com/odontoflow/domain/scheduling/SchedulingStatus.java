package br.com.odontoflow.domain.scheduling;

public enum SchedulingStatus {

    SCHEDULED("Agendado"),
    RESCHEDULED("Reagendado"),
    CANCELED("Cancelado"),
    DONE("Realizado"),
    ATTENDING("Atendendo"),
    LATE("Atrasado");

    private final String description;

    SchedulingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
