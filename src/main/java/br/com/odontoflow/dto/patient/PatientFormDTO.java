package br.com.odontoflow.dto.patient;


import br.com.odontoflow.dto.address.AddressFormDTO;

public record PatientFormDTO(String name, String document, AddressFormDTO address, String patientRecords) {

}
