package br.com.odontoflow.application.procedure;

import java.math.BigDecimal;
import java.util.List;

public record ProcedureFormDTO(String name, BigDecimal price, List<Long> professionalsIds) {

}
