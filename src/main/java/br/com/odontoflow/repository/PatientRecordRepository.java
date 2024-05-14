package br.com.odontoflow.repository;

import br.com.odontoflow.domain.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {

    List<PatientRecord> findByProfessional_Id(Long id);

    List<PatientRecord> findByPatient_Id(Long id);
}