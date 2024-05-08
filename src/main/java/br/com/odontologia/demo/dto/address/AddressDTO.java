package br.com.odontologia.demo.dto.address;

import br.com.odontologia.demo.domain.Address;

public record AddressDTO(String street, int number, String neighborhood, String city, String state) {

    public AddressDTO(Address address) {
        this(address.getStreet(), address.getNumber(), address.getNeighborhood(), address.getCity(), address.getState());
    }
}
