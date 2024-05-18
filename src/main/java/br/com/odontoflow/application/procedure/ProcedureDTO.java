package br.com.odontoflow.application.procedure;

import br.com.odontoflow.domain.procedure.Procedure;

import java.math.BigDecimal;
import java.util.List;

public record ProcedureDTO(String name, BigDecimal price, List<String> professionals) {

    public ProcedureDTO(Procedure procedure) {
        this(procedure.getName(), procedure.getPrice(), procedure.getProfessionalsNames());
    }
}
