package br.com.odontoflow.infrastructure.patient;

import br.com.odontoflow.domain.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByDocument(String document);

    List<Patient> findAllByBlockedTrue();

}
