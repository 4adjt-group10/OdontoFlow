package br.com.odontoflow.application.professional;

import br.com.odontoflow.application.address.AddressFormDTO;
import br.com.odontoflow.domain.professional.ProfessionalType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProfessionalFormDTO(
        @NotBlank String name,
        @NotBlank String document,
        @NotNull AddressFormDTO address,
        @NotNull ProfessionalType type,
        @Nullable List<UUID> proceduresIds,
        @Nullable List<LocalDateTime> availabilities
) {

}
