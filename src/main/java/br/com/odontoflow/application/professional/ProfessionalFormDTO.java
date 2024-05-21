package br.com.odontoflow.application.professional;

import br.com.odontoflow.application.address.AddressFormDTO;
import br.com.odontoflow.domain.professional.ProfessionalType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record ProfessionalFormDTO(
        @NotBlank String name,
        @NotBlank String document,
        @NotNull AddressFormDTO address,
        @NotNull ProfessionalType type,
        @Nullable List<Long> proceduresIds,
        @Nullable List<LocalDateTime> availabilities
) {

}
