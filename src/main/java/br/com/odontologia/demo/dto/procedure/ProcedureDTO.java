package br.com.odontologia.demo.dto.procedure;

import br.com.odontologia.demo.domain.Procedure;

public record ProcedureDTO(String nome, Double valor) {

    public ProcedureDTO(Procedure procedimento) {
        this(procedimento.getNome(), procedimento.getValor());
    }
}
