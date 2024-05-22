package br.com.odontoflow.infrastructure.scheduling;


import br.com.odontoflow.domain.scheduling.Scheduling;
import br.com.odontoflow.domain.scheduling.SchedulingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, UUID> {

    List<Scheduling> findAllByPatient_Id(UUID id);

    List<Scheduling> findAllByProfessional_Id(UUID id);

    @Query(value = "SELECT * FROM Scheduling s WHERE CAST(s.appointment AS DATE) = :date", nativeQuery = true)
    List<Scheduling> findAllByAppointmentsToDay(@Param("date") LocalDate date);

    List<Scheduling> findAllByStatus(SchedulingStatus status);

    List<Scheduling> findAllByAppointmentBetweenAndStatusIn(LocalDateTime now, LocalDateTime next24Hours, List<SchedulingStatus> statusList);

    Optional<Scheduling> findFirstByPatient_DocumentAndStatusOrderByAppointmentDesc(String document, SchedulingStatus status);

    default Optional<Scheduling> findByPatientDocumentAndStatus(String document, SchedulingStatus status) {
        return findFirstByPatient_DocumentAndStatusOrderByAppointmentDesc(document, status);
    }

    @Query(value = "SELECT * FROM Scheduling s WHERE s.patient_id =:id and CAST(s.appointment AS DATE) = :date", nativeQuery = true)
    List<Scheduling> findAllByPatientIdAndDate(UUID id, LocalDate date);

    @Query(value = "SELECT * FROM Scheduling s WHERE s.professional_id =:id and CAST(s.appointment AS DATE) = :date", nativeQuery = true)
    List<Scheduling> findAllByProfessionalIdAndDate(UUID id, LocalDate date);
}
