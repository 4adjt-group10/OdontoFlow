package br.com.odontoflow.domain.professional;

import br.com.odontoflow.application.professional.ProfessionalAvailabilityDTO;
import br.com.odontoflow.application.professional.ProfessionalAvailabilityFormDTO;
import br.com.odontoflow.infrastructure.professional.ProfessionalAvailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProfessionalAvailabilityService {

    private final ProfessionalService professionalService;
    private final ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    public ProfessionalAvailabilityService(ProfessionalService professionalService,
                                           ProfessionalAvailabilityRepository professionalAvailabilityRepository) {
        this.professionalService = professionalService;
        this.professionalAvailabilityRepository = professionalAvailabilityRepository;
    }

    @Transactional
    public ProfessionalAvailabilityDTO registerAvailability(ProfessionalAvailabilityFormDTO formDTO) {
        Professional professional = professionalService.findProfessionalById(formDTO.professionalId());
        ProfessionalAvailability availability = new ProfessionalAvailability(professional, formDTO.availableTime());
        professionalAvailabilityRepository.save(availability);
        return new ProfessionalAvailabilityDTO(availability);
    }

    public List<ProfessionalAvailabilityDTO> listAllAvailabilities() {
        return professionalAvailabilityRepository.findAll().stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByProfessionalId(UUID professionalId) {
        return professionalAvailabilityRepository.findByProfessionalId(professionalId)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByDate(LocalDate date) {
        return professionalAvailabilityRepository.findByAvailableByDate(date)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByDayOfWeek(int dayOfWeek) {
        return professionalAvailabilityRepository.findByAvailableTimeByDayOfWeek(dayOfWeek % 7 + 1)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByHour(int hour) {
        return professionalAvailabilityRepository.findByAvailableByHour(hour)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public ProfessionalAvailability findAvailabilityByProfessionalAndAvailableTime(UUID professionalId, LocalDateTime date) {
        return professionalAvailabilityRepository.findByProfessionalIdAndAvailableTime(professionalId, date)
                .orElseThrow(() -> new EntityNotFoundException("Professional not available at this time"));
    }

    public List<ProfessionalAvailabilityDTO> findByProcedureId(UUID id) {
        return professionalAvailabilityRepository.findAllByProfessional_Procedures_id(id)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    @Transactional
    public ProfessionalAvailabilityDTO updateAvailability(UUID id, ProfessionalAvailabilityFormDTO formDTO) {
        ProfessionalAvailability availability = professionalAvailabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Availability not found"));
        availability.merge(formDTO);
        return new ProfessionalAvailabilityDTO(availability);
    }

    public void deleteAvailability(ProfessionalAvailability availability) {
        professionalAvailabilityRepository.delete(availability);
    }

    /*
     * A expressão cron "0 0/30 9-20 * * MON-SAT" significa:
     * 0: No início do minuto (segundos).
     * 0/30: A cada 30 minutos.
     * 9-20: Entre as 9 da manhã e as 8 da noite (horas).
     * *: Qualquer dia do mês.
     * *: Qualquer mês.
     * MON-SAT: De segunda a sábado.
     * O método deletePastAvailabilities será executado a cada 30 minutos de segunda a sábado, das 09:00 da manhã às 20:00 da noite.
     * */
    @Async
    @Transactional
    @Scheduled(cron = "0 0/30 9-20 * * MON-SAT")
    public void deletePastAvailabilities() {
        LocalDateTime now = LocalDateTime.now();
        List<ProfessionalAvailability> pastAvailabilities = professionalAvailabilityRepository.findAllByAvailableTimeBefore(now);
        professionalAvailabilityRepository.deleteAll(pastAvailabilities);
    }
}
