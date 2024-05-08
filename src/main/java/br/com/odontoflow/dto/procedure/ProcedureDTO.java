package br.com.odontoflow.dto.procedure;

import br.com.odontoflow.domain.Procedure;

public record ProcedureDTO(String nome, Double valor) {

    public ProcedureDTO(Procedure procedimento) {
        this(procedimento.getNome(), procedimento.getValor());
    }
}
