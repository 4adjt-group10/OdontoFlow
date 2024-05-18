package br.com.odontoflow.domain.professional;

import br.com.odontoflow.application.ControllerNotFoundException;
import br.com.odontoflow.application.professional.ProfessionalDTO;
import br.com.odontoflow.application.professional.ProfessionalFormDTO;
import br.com.odontoflow.domain.address.Address;
import br.com.odontoflow.domain.address.AddressService;
import br.com.odontoflow.domain.procedure.Procedure;
import br.com.odontoflow.infrastructure.procedure.ProcedureRepository;
import br.com.odontoflow.infrastructure.professional.ProfessionalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final AddressService addressService;
    private final ProcedureRepository procedureRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository,
                               AddressService addressService,
                               ProcedureRepository procedureRepository) {
        this.professionalRepository = professionalRepository;
        this.addressService = addressService;
        this.procedureRepository = procedureRepository;
    }

    @Transactional
    public ProfessionalDTO register(ProfessionalFormDTO professionalFormDto) {
        List<Procedure> procedures = procedureRepository.findAllById(professionalFormDto.proceduresIds());
        Professional professional = new Professional(professionalFormDto);
        professionalRepository.save(professional);
        procedures.forEach(procedure -> procedure.addProfessional(professional));
        Address address = addressService.register(professionalFormDto.address());
        professional.setAddress(address);
        return new ProfessionalDTO(professional);
    }

    @Transactional
    public void update(Long id, ProfessionalFormDTO professionalFormDTO) {
        List<Procedure> procedures = procedureRepository.findAllById(professionalFormDTO.proceduresIds());
        Professional professional = findProfessionalById(id);
        professional.merge(professionalFormDTO, procedures);
        procedures.forEach(procedure -> procedure.addProfessional(professional));
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
