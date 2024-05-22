package br.com.odontoflow.domain.professional;

import br.com.odontoflow.application.professional.ProfessionalAvailabilityDTO;
import br.com.odontoflow.application.professional.ProfessionalAvailabilityFormDTO;
import br.com.odontoflow.application.professional.ProfessionalDTO;
import br.com.odontoflow.application.professional.ProfessionalFormDTO;
import br.com.odontoflow.domain.address.Address;
import br.com.odontoflow.domain.address.AddressService;
import br.com.odontoflow.domain.procedure.Procedure;
import br.com.odontoflow.infrastructure.procedure.ProcedureRepository;
import br.com.odontoflow.infrastructure.professional.ProfessionalAvailabilityRepository;
import br.com.odontoflow.infrastructure.professional.ProfessionalRepository;
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
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final AddressService addressService;
    private final ProcedureRepository procedureRepository;
    private final ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository,
                               AddressService addressService,
                               ProcedureRepository procedureRepository,
                               ProfessionalAvailabilityRepository professionalAvailabilityRepository) {
        this.professionalRepository = professionalRepository;
        this.addressService = addressService;
        this.procedureRepository = procedureRepository;
        this.professionalAvailabilityRepository = professionalAvailabilityRepository;
    }

    @Transactional
    public ProfessionalDTO register(ProfessionalFormDTO professionalFormDto) {
        List<Procedure> procedures = procedureRepository.findAllById(professionalFormDto.proceduresIds());
        Professional professional = new Professional(professionalFormDto);
        professionalFormDto.availabilities()
                .forEach(availability -> professionalAvailabilityRepository.save(new ProfessionalAvailability(professional, availability)));
        procedures.forEach(procedure -> procedure.addProfessional(professional));
        Address address = addressService.register(professionalFormDto.address());
        professional.setAddress(address);
        professionalRepository.save(professional);
        return new ProfessionalDTO(professional);
    }

    @Transactional
    public ProfessionalDTO update(Long id, ProfessionalFormDTO professionalFormDTO) {
        List<Procedure> procedures = procedureRepository.findAllById(professionalFormDTO.proceduresIds());
        Professional professional = findProfessionalById(id);
        professionalAvailabilityRepository.deleteAll(professional.getAvailability());
        professional.merge(professionalFormDTO, procedures);
        professionalFormDTO.availabilities()
                .forEach(availability -> professionalAvailabilityRepository.save(new ProfessionalAvailability(professional, availability)));
        procedures.forEach(procedure -> procedure.addProfessional(professional));
        professionalRepository.save(professional);
        return new ProfessionalDTO(professional);
    }

    public Professional findByDocument(String document) {
        return professionalRepository.findByDocument(document)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));
    }

    public Professional findProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));
    }

    public List<ProfessionalDTO> listAllProfessionals() {
        return professionalRepository.findAll().stream().map(ProfessionalDTO::new).toList();
    }

    @Transactional
    public ProfessionalAvailabilityDTO registerAvailability(ProfessionalAvailabilityFormDTO formDTO) {
        Professional professional = findProfessionalById(formDTO.professionalId());
        ProfessionalAvailability availability = new ProfessionalAvailability(professional, formDTO.availableTime());
        professionalAvailabilityRepository.save(availability);
        return new ProfessionalAvailabilityDTO(availability);
    }

    public List<ProfessionalAvailabilityDTO> listAllAvailabilities() {
        return professionalAvailabilityRepository.findAll().stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByProfessionalId(Long professionalId) {
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

    public ProfessionalAvailability findAvailabilityByProfessionalAndAvailableTime(Long professionalId,
                                                                                     LocalDateTime date) {
        return professionalAvailabilityRepository.findByProfessionalIdAndAvailableTime(professionalId, date)
                .orElseThrow(() -> new EntityNotFoundException("Professional not available at this time"));
    }

    public List<ProfessionalAvailabilityDTO> findByProcedureId(UUID id) {
        return professionalAvailabilityRepository.findAllByProfessional_Procedures_id(id)
                .stream().map(ProfessionalAvailabilityDTO::new).toList();
    }

    @Transactional
    public ProfessionalAvailabilityDTO updateAvailability(Long id, ProfessionalAvailabilityFormDTO formDTO) {
        ProfessionalAvailability availability = professionalAvailabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Availability not found"));
        availability.merge(formDTO);
        return new ProfessionalAvailabilityDTO(availability);
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
