package br.com.odontoflow.domain.patient;

import br.com.odontoflow.application.patient.PatientDTO;
import br.com.odontoflow.application.patient.PatientFormDTO;
import br.com.odontoflow.domain.address.Address;
import br.com.odontoflow.domain.address.AddressService;
import br.com.odontoflow.domain.scheduling.Scheduling;
import br.com.odontoflow.infrastructure.patient.PatientRepository;
import br.com.odontoflow.infrastructure.scheduling.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.odontoflow.domain.scheduling.SchedulingStatus.CANCELED;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AddressService addressService;
    private final SchedulingRepository schedulingRepository;

    public PatientService(PatientRepository patientRepository,
                          AddressService addressService,
                          SchedulingRepository schedulingRepository) {
        this.patientRepository = patientRepository;
        this.addressService = addressService;
        this.schedulingRepository = schedulingRepository;
    }

    @Transactional
    public PatientDTO register(PatientFormDTO patientFormDTO) {
        Patient patient = new Patient(patientFormDTO);
        Address address = addressService.register(patientFormDTO.address());
        patient.setAddress(address);
        patientRepository.save(patient);
        return new PatientDTO(patient);
    }

    @Transactional
    public PatientDTO update(UUID id, PatientFormDTO patientFormDTO) {
        Patient patient = findPatientById(id);
        if(!patient.hasAddess()) {
            Address address = addressService.register(patientFormDTO.address());
            patient.setAddress(address);
        }
        patient.merge(patientFormDTO);
        return new PatientDTO(patient);
    }

    public Patient findPatientById(UUID patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    public List<PatientDTO> listAll() {
        return patientRepository.findAll().stream().map(PatientDTO::new).toList();
    }

    public Patient findByDocumentOrCreate(String patientName, String patientDocument, String phone) {
        return patientRepository.findByDocument(patientDocument)
                .orElseGet(() -> patientRepository.save(new Patient(patientName, patientDocument, phone)));
    }

    /**
     * This method will unblock all patients that have a canceled schedule older than 7 days.
     * {@code @Scheduled(cron = "0 0 0 * * *")} means this method will be executed every day at midnight.
     */
    @Async
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void unblockAllPatients() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Patient> blockedPatients = patientRepository.findAllByBlockedTrue();
        blockedPatients.forEach(patient -> {
            Optional<Scheduling> canceledSchedule = schedulingRepository
                    .findByPatientDocumentAndStatus(patient.getDocument(), CANCELED);
            canceledSchedule.ifPresent(scheduling -> {
                if (scheduling.getAppointment().isBefore(sevenDaysAgo)) {
                    patient.unblock();
                }
            });
        });
        patientRepository.saveAll(blockedPatients);
    }

}
