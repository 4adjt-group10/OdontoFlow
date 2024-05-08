package br.com.odontoflow.dto.professional;

import br.com.odontoflow.domain.professional.ProfessionalType;
import br.com.odontoflow.dto.address.AddressFormDTO;

public record ProfessionalFormDTO(String name, String document, AddressFormDTO address, ProfessionalType type) {

}
