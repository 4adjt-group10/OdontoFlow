package br.com.odontoflow.infrastructure.patient;

import br.com.odontoflow.domain.patient.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, UUID> {

    List<PatientRecord> findByProfessional_Id(UUID id);

    List<PatientRecord> findByPatient_Id(UUID id);

    Optional<PatientRecord> findFirstByPatient_IdOrderByDateDesc(UUID id);
}
