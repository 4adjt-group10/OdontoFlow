package br.com.odontoflow.domain.procedure;

import br.com.odontoflow.application.procedure.ProcedureFormDTO;
import br.com.odontoflow.domain.professional.Professional;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Procedure")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private BigDecimal price;
    @ManyToMany(mappedBy = "procedures")
    private List<Professional> professionals;

    public Procedure(ProcedureFormDTO procedureFormDTO, List<Professional> professionals) {
        this.name = procedureFormDTO.name();
        this.price = procedureFormDTO.price();
        this.professionals = professionals;
    }

    @Deprecated(since = "Only for use of frameworks")
    public Procedure() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Professional> getProfessionals() {
        return professionals;
    }

    public List<String> getProfessionalsNames() {
        return Optional.ofNullable(professionals)
                .map(professionalList -> professionalList.stream().map(Professional::getName).toList())
                .orElse(List.of());
    }

    public void addProfessional(Professional professional) {
        professionals.add(professional);
    }

    public void merge(ProcedureFormDTO procedureFormDTO, List<Professional> professionals) {
        this.name = procedureFormDTO.name();
        this.price = procedureFormDTO.price();
        this.professionals = professionals;
    }
}
