package br.com.odontoflow.infrastructure.patient;

import br.com.odontoflow.domain.patient.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {

    List<PatientRecord> findByProfessional_Id(Long id);

    List<PatientRecord> findByPatient_Id(Long id);

    Optional<PatientRecord> findFirstByPatient_IdOrderByDateDesc(Long id);
}
