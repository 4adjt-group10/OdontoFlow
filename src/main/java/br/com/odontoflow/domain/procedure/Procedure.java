package br.com.odontoflow.domain.procedure;

import br.com.odontoflow.application.procedure.ProcedureFormDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "Procedure")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Double valor;

    public Procedure(ProcedureFormDTO procedureFormDTO) {
        this.nome = procedureFormDTO.name();
        this.valor = procedureFormDTO.price();
    }

    @Deprecated(since = "Only for use of frameworks")
    public Procedure() {

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getValor() {
        return valor;
    }

}
