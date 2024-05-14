package br.com.odontoflow.service;

import br.com.odontoflow.domain.Patient;
import br.com.odontoflow.domain.PatientRecord;
import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.dto.patient.PatientRecordDTO;
import br.com.odontoflow.dto.patient.PatientRecordFormDTO;
import br.com.odontoflow.repository.PatientRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientRecordService {

    private final PatientRecordRepository patientRecordRepository;
    private final PatientService patientService;
    private final ProfessionalService professionalService;

    public PatientRecordService(PatientRecordRepository patientRecordRepository,
                                PatientService patientService,
                                ProfessionalService professionalService) {
        this.patientRecordRepository = patientRecordRepository;
        this.patientService = patientService;
        this.professionalService = professionalService;
    }

    public PatientRecordDTO register(PatientRecordFormDTO formDTO){
        Patient patient = patientService.findUserById(formDTO.patientId());
        Professional professional = professionalService.findProfessionalById(formDTO.professionalId());
        PatientRecord patientRecord = new PatientRecord(formDTO.description(), formDTO.getDate(), patient, professional);
        patientRecordRepository.save(patientRecord);
        return new PatientRecordDTO(patientRecord);
    }

    @Transactional
    public void update(Long id, PatientRecordFormDTO formDTO){
        PatientRecord patientRecord = patientRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient record not found"));
        patientRecord.merge(formDTO);
    }

    public List<PatientRecordDTO> findByPatientId(Long patientId){
        return patientRecordRepository.findByPatient_Id(patientId).stream().map(PatientRecordDTO::new).toList();
    }

    public List<PatientRecordDTO> findByProfessionalId(Long professionalId){
        return patientRecordRepository.findByProfessional_Id(professionalId).stream().map(PatientRecordDTO::new).toList();
    }

    public PatientRecordDTO findById(Long id){
        PatientRecord patientRecord = patientRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient record not found"));
        return new PatientRecordDTO(patientRecord);
    }
}
