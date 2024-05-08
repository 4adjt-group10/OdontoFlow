package br.com.odontologia.demo.dto.professional;

import br.com.odontologia.demo.dto.address.AddressFormDTO;
import br.com.odontologia.demo.domain.professional.ProfessionalType;

public record ProfessionalFormDTO(String name, String document, AddressFormDTO address, ProfessionalType type) {

}
