package br.com.odontoflow.application.procedure;

import br.com.odontoflow.domain.procedure.Procedure;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProcedureDTO(UUID id, String name, BigDecimal price, List<String> professionals) {

    public ProcedureDTO(Procedure procedure) {
        this(procedure.getId(), procedure.getName(), procedure.getPrice(), procedure.getProfessionalsNames());
    }
}
