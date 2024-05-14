package br.com.odontoflow.service;

import br.com.odontoflow.controller.exception.ControllerNotFoundException;
import br.com.odontoflow.domain.Address;
import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.dto.professional.ProfessionalDTO;
import br.com.odontoflow.dto.professional.ProfessionalFormDTO;
import br.com.odontoflow.repository.ProfessionalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Transactional
    public void update(Long id, ProfessionalFormDTO professionalFormDTO) {
        Professional professional = findProfessionalById(id);
        professional.merge(professionalFormDTO);
    }

    public Professional findByDocument(String document) {
        return professionalRepository.findByDocument(document).orElseThrow(() -> new ControllerNotFoundException("Professional not found"));
    }

    public Professional findProfessionalById(Long id) {
        return professionalRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Professional not found"));
    }

    public List<ProfessionalDTO> listAllProfessionals() {
        return professionalRepository.findAll().stream().map(ProfessionalDTO::new).toList();
    }

}
