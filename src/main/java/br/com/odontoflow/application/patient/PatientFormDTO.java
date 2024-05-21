package br.com.odontoflow.application.patient;


import br.com.odontoflow.application.address.AddressFormDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record PatientFormDTO(@NotBlank String name, @NotBlank String document, @Nullable AddressFormDTO address) {

}
