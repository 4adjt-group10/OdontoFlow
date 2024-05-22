package br.com.odontoflow.domain.address;

import br.com.odontoflow.application.address.AddressFormDTO;
import br.com.odontoflow.infrastructure.address.AddressRepository;
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

}
