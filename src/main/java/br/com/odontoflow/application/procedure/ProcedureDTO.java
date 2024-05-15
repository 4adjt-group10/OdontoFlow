package br.com.odontoflow.application.procedure;

import br.com.odontoflow.domain.procedure.Procedure;

public record ProcedureDTO(String nome, Double valor) {

    public ProcedureDTO(Procedure procedimento) {
        this(procedimento.getNome(), procedimento.getValor());
    }
}
