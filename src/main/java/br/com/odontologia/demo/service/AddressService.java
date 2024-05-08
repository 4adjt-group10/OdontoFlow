package br.com.odontologia.demo.service;

import br.com.odontologia.demo.domain.Address;
import br.com.odontologia.demo.dto.address.AddressFormDTO;
import br.com.odontologia.demo.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        return addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Transactional
    public void update(Long id, AddressFormDTO addressFormDTO) {
        Address address = findById(id);
        address.merge(addressFormDTO);
    }
}
