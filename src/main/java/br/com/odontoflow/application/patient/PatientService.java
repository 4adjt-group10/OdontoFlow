package br.com.odontoflow.application.patient;

import br.com.odontoflow.application.ControllerNotFoundException;
import br.com.odontoflow.application.address.AddressService;
import br.com.odontoflow.domain.address.Address;
import br.com.odontoflow.domain.patient.Patient;
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
        Patient patient = findUserById(id);
        patient.merge(patientFormDTO);
    }

    public Patient findUserById(Long userId) {
        return patientRepository.findById(userId).orElseThrow(() -> new ControllerNotFoundException("User not found"));
    }

    public List<PatientDTO> listAllUsers() {
        return patientRepository.findAll().stream().map(PatientDTO::new).toList();
    }

}
