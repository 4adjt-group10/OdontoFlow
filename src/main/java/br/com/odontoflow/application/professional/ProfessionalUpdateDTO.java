package br.com.odontoflow.application.professional;

import br.com.odontoflow.application.address.AddressFormDTO;
import br.com.odontoflow.domain.professional.ProfessionalType;

import java.util.List;

public record ProfessionalUpdateDTO(
        String name,
        String document,
        AddressFormDTO address,
        ProfessionalType type,
        List<Long> proceduresIds,
        List<Long> availabilitiesIds
) {
}
