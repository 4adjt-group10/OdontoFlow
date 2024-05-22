package br.com.odontoflow.application.procedure;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProcedureFormDTO(@NotBlank String name, @Min(0) BigDecimal price, @Nullable List<UUID> professionalsIds) {

}
