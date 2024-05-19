package br.com.odontoflow.application.professional;

import br.com.odontoflow.application.address.AddressFormDTO;
import br.com.odontoflow.domain.professional.ProfessionalType;

import java.time.LocalDateTime;
import java.util.List;

public record ProfessionalFormDTO(
        String name,
        String document,
        AddressFormDTO address,
        ProfessionalType type,
        List<Long> proceduresIds,
        List<LocalDateTime> availabilities
) {

}
