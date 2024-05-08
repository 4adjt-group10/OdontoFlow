package br.com.odontologia.demo.service;

import br.com.odontologia.demo.domain.Address;
import br.com.odontologia.demo.domain.Patient;
import br.com.odontologia.demo.dto.patient.PatientFormDTO;
import br.com.odontologia.demo.repository.PatientRepository;
import br.com.odontologia.demo.dto.patient.PatientDTO;
import jakarta.persistence.EntityNotFoundException;
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
        return patientRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public List<PatientDTO> listAllUsers() {
        return patientRepository.findAll().stream().map(PatientDTO::new).toList();
    }

}
