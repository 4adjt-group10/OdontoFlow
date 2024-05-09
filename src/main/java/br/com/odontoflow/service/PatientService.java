package br.com.odontoflow.service;

import br.com.odontoflow.controller.exception.ControllerNotFoundException;
import br.com.odontoflow.domain.Address;
import br.com.odontoflow.domain.Patient;
import br.com.odontoflow.dto.patient.PatientDTO;
import br.com.odontoflow.dto.patient.PatientFormDTO;
import br.com.odontoflow.repository.PatientRepository;
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

    public Patient findUserById(Long userId) {
        return patientRepository.findById(userId).orElseThrow(() -> new ControllerNotFoundException("User not found"));
    }

    public List<PatientDTO> listAllUsers() {
        return patientRepository.findAll().stream().map(PatientDTO::new).toList();
    }

}
