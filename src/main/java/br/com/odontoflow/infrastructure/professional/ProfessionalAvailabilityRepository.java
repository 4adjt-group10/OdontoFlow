package br.com.odontoflow.infrastructure.professional;

import br.com.odontoflow.domain.professional.ProfessionalAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalAvailabilityRepository extends JpaRepository<ProfessionalAvailability, UUID> {

    List<ProfessionalAvailability> findByProfessionalId(UUID professionalId);

    @Query("SELECT pa FROM ProfessionalAvailability pa WHERE CAST(pa.availableTime AS date) = :date")
    List<ProfessionalAvailability> findByAvailableByDate(@Param("date") LocalDate date);

    /*
    * Retorna as disponibilidades de um profissional em um determinado dia da semana.
    * Considere Domingo = 1 e sábado = 7.
    * */
    @Query("SELECT pa FROM ProfessionalAvailability pa WHERE FUNCTION('DAY_OF_WEEK', pa.availableTime) = :dayOfWeek")
    List<ProfessionalAvailability> findByAvailableTimeByDayOfWeek(@Param("dayOfWeek") Integer dayOfWeek);

     /*
     * Retorna as disponibilidades de um profissional em um determinado horário do dia.
     * Considere 0 a 23 horas.
     * */
    @Query("SELECT pa FROM ProfessionalAvailability pa WHERE FUNCTION('HOUR', pa.availableTime) = :hour ORDER BY pa.availableTime ASC")
    List<ProfessionalAvailability> findByAvailableByHour(@Param("hour") Integer hour);

    List<ProfessionalAvailability> findAllByAvailableTimeBefore(LocalDateTime now);

    Optional<ProfessionalAvailability> findByProfessionalIdAndAvailableTime(UUID professionalId, LocalDateTime date);

    List<ProfessionalAvailability> findAllByProfessional_Procedures_id(UUID procedureId);
}
