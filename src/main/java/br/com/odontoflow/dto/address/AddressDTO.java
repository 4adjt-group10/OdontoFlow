package br.com.odontoflow.dto.address;

import br.com.odontoflow.domain.Address;

public record AddressDTO(String street, int number, String neighborhood, String city, String state) {

    public AddressDTO(Address address) {
        this(address.getStreet(), address.getNumber(), address.getNeighborhood(), address.getCity(), address.getState());
    }
}
