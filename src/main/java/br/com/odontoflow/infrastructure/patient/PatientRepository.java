package br.com.odontoflow.infrastructure.patient;

import br.com.odontoflow.domain.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByDocument(String document);
}
