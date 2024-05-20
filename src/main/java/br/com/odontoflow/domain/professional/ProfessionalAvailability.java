package br.com.odontoflow.domain.professional;

import br.com.odontoflow.application.professional.ProfessionalAvailabilityFormDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProfessionalAvailability")
public class ProfessionalAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    private LocalDateTime availableTime;

    public ProfessionalAvailability(Professional professional, LocalDateTime availableTime) {
        this.professional = professional;
        this.availableTime = availableTime;
    }

    @Deprecated(since = "Only for use of frameworks")
    public ProfessionalAvailability() {
    }

    public Long getId() {
        return id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public LocalDateTime getAvailableTime() {
        return availableTime;
    }

    public String getProfessionalName() {
        return professional.getName();
    }

    public void merge(ProfessionalAvailabilityFormDTO formDTO) {
        this.availableTime = formDTO.availableTime();
    }
}
