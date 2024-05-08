package br.com.odontologia.demo.dto.patient;


import br.com.odontologia.demo.dto.address.AddressFormDTO;

public record PatientFormDTO(String name, String document, AddressFormDTO address, String patientRecords) {

}
