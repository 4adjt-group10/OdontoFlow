package br.com.odontoflow.application.professional;

import br.com.odontoflow.application.address.AddressFormDTO;
import br.com.odontoflow.domain.professional.ProfessionalType;

public record ProfessionalFormDTO(String name, String document, AddressFormDTO address, ProfessionalType type) {

}
