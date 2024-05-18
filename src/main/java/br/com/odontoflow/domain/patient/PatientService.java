package br.com.odontoflow.domain.patient;

import br.com.odontoflow.application.ControllerNotFoundException;
import br.com.odontoflow.application.patient.PatientDTO;
import br.com.odontoflow.application.patient.PatientFormDTO;
import br.com.odontoflow.domain.address.AddressService;
import br.com.odontoflow.domain.address.Address;
import br.com.odontoflow.infrastructure.patient.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AddressService addressService;

    public PatientService(PatientRepository patientRepository, AddressService addressService) {
        this.patientRepository = patientRepository;
        this.addressService = addressService;
    }

    @Transactional
    public PatientDTO register(PatientFormDTO patientFormDTO) {
        Patient patient = new Patient(patientFormDTO);
        patientRepository.save(patient);
        Address address = addressService.register(patientFormDTO.address());
        patient.setAddress(address);
        return new PatientDTO(patient);
    }

    @Transactional
    public void update(Long id, PatientFormDTO patientFormDTO) {
        Patient patient = findPatientById(id);
        patient.merge(patientFormDTO);
    }

    public Patient findPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new ControllerNotFoundException("Patient not found"));
    }

    public List<PatientDTO> listAll() {
        return patientRepository.findAll().stream().map(PatientDTO::new).toList();
    }

    public Patient findByDocumentOrCreate(String patientName, String patientDocument) {
        return patientRepository.findByDocument(patientDocument)
                .orElseGet(() -> patientRepository.save(new Patient(patientName, patientDocument)));
    }

    //TODO: Implementar lógica para atualizar dados do paciente caso a ultima consulta tenha ocorrido a mais de 6 meses

}
