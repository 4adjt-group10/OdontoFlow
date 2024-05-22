package br.com.odontoflow.application.patient;


import br.com.odontoflow.application.address.AddressFormDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record PatientFormDTO(
        @NotBlank String name,
        @NotBlank String document,
        @NotBlank String phone,
        Optional<String> email,
        AddressFormDTO address
) {

}
