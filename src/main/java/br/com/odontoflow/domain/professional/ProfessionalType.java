package br.com.odontoflow.domain.professional;

public enum ProfessionalType {

    DENTIST("Dentista"),
    ASSISTANT("Assistente");

    private final String description;

    ProfessionalType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
