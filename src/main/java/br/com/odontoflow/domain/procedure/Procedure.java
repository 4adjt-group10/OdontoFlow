package br.com.odontoflow.domain.procedure;

import br.com.odontoflow.application.procedure.ProcedureFormDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Procedure")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private BigDecimal price;

    public Procedure(ProcedureFormDTO procedureFormDTO) {
        this.name = procedureFormDTO.name();
        this.price = procedureFormDTO.price();
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

}
