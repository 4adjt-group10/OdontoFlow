package br.com.odontoflow.application.procedure;

import br.com.odontoflow.domain.procedure.Procedure;

import java.math.BigDecimal;

public record ProcedureDTO(String name, BigDecimal price) {

    public ProcedureDTO(Procedure procedure) {
        this(procedure.getName(), procedure.getPrice());
    }
}
