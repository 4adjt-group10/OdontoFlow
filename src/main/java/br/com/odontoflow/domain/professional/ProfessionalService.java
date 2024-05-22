package br.com.odontoflow.domain.professional;

import br.com.odontoflow.application.professional.ProfessionalDTO;
import br.com.odontoflow.application.professional.ProfessionalFormDTO;
import br.com.odontoflow.domain.address.Address;
import br.com.odontoflow.domain.address.AddressService;
import br.com.odontoflow.domain.procedure.Procedure;
import br.com.odontoflow.infrastructure.procedure.ProcedureRepository;
import br.com.odontoflow.infrastructure.professional.ProfessionalAvailabilityRepository;
import br.com.odontoflow.infrastructure.professional.ProfessionalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final AddressService addressService;
    private final ProcedureRepository procedureRepository;
    private final ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository,
                               AddressService addressService,
                               ProcedureRepository procedureRepository,
                               ProfessionalAvailabilityRepository professionalAvailabilityRepository) {
        this.professionalRepository = professionalRepository;
        this.addressService = addressService;
        this.procedureRepository = procedureRepository;
        this.professionalAvailabilityRepository = professionalAvailabilityRepository;
    }

    @Transactional
    public ProfessionalDTO register(ProfessionalFormDTO professionalFormDto) {
        List<Procedure> procedures = procedureRepository.findAllById(professionalFormDto.proceduresIds());
        Professional professional = new Professional(professionalFormDto);
        List<ProfessionalAvailability> availabilities = professionalFormDto.availabilities()
                .stream()
                .map(availability -> new ProfessionalAvailability(professional, availability))
                .toList();
        procedures.forEach(procedure -> procedure.addProfessional(professional));
        Address address = addressService.register(professionalFormDto.address());
        professional.setAddress(address);
        professionalRepository.save(professional);
        professionalAvailabilityRepository.saveAll(availabilities);
        return new ProfessionalDTO(professional);
    }

    @Transactional
    public ProfessionalDTO update(UUID id, ProfessionalFormDTO professionalFormDTO) {
        List<Procedure> procedures = procedureRepository.findAllById(professionalFormDTO.proceduresIds());
        Professional professional = findProfessionalById(id);
        professionalAvailabilityRepository.deleteAll(professional.getAvailability());
        professional.merge(professionalFormDTO, procedures);
        professionalFormDTO.availabilities()
                .forEach(availability -> professionalAvailabilityRepository.save(new ProfessionalAvailability(professional, availability)));
        procedures.forEach(procedure -> procedure.addProfessional(professional));
        professionalRepository.save(professional);
        return new ProfessionalDTO(professional);
    }

    public Professional findByDocument(String document) {
        return professionalRepository.findByDocument(document)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));
    }

    public Professional findProfessionalById(UUID id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));
    }

    public List<ProfessionalDTO> listAllProfessionals() {
        return professionalRepository.findAll().stream().map(ProfessionalDTO::new).toList();
    }

}
