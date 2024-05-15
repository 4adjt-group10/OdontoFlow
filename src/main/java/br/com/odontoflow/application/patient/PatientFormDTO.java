package br.com.odontoflow.application.patient;


import br.com.odontoflow.application.address.AddressFormDTO;

public record PatientFormDTO(String name, String document, AddressFormDTO address) {

}
