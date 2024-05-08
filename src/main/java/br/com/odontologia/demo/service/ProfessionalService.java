package br.com.odontologia.demo.service;

import br.com.odontologia.demo.domain.Address;
import br.com.odontologia.demo.dto.professional.ProfessionalDTO;
import br.com.odontologia.demo.domain.professional.Professional;
import br.com.odontologia.demo.dto.professional.ProfessionalFormDTO;
import br.com.odontologia.demo.repository.ProfessionalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final AddressService addressService;

    public ProfessionalService(ProfessionalRepository professionalRepository, AddressService addressService) {
        this.professionalRepository = professionalRepository;
        this.addressService = addressService;
    }

    @Transactional
    public ProfessionalDTO register(ProfessionalFormDTO professionalFormDto) {
        Professional professional = new Professional(professionalFormDto);
        professionalRepository.save(professional);
        Address address = addressService.register(professionalFormDto.address());
        professional.setAddress(address);
        return new ProfessionalDTO(professional);
    }

    public Professional findByDocument(String document) {
        return professionalRepository.findByDocument(document).orElseThrow(() -> new EntityNotFoundException("Professional not found"));
    }

    public Professional findProfessionalById(Long id) {
        return professionalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professional not found"));
    }

}
