package br.com.odontoflow.service;

import br.com.odontoflow.controller.exception.ControllerNotFoundException;
import br.com.odontoflow.domain.Address;
import br.com.odontoflow.dto.address.AddressFormDTO;
import br.com.odontoflow.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address register(AddressFormDTO addressFormDTO) {
        Address address = new Address(addressFormDTO);
        return addressRepository.save(address);
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Address not found"));
    }

}
