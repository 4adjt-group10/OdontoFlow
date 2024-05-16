package br.com.odontoflow.infrastructure.scheduling;


import br.com.odontoflow.domain.scheduling.Scheduling;
import br.com.odontoflow.domain.scheduling.SchedulingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    List<Scheduling> findAllByPatient_Id(Long id);

    List<Scheduling> findAllByProfessional_Id(Long id);

    @Query(value = "SELECT * FROM Scheduling s WHERE CAST(s.appointment AS DATE) = :date", nativeQuery = true)
    List<Scheduling> findAllByAppointmentsToDay(@Param("date") LocalDate date);

    List<Scheduling> findAllByStatus(SchedulingStatus status);
}
