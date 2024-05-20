package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.ProfessionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessionalDTO> professionalRegister(@RequestBody ProfessionalFormDTO professionalFormDTO){
        return new ResponseEntity<>(professionalService.register(professionalFormDTO), CREATED);
    }

    @GetMapping("/list")
    public List<ProfessionalDTO> listAll() {
        return professionalService.listAllProfessionals();
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody ProfessionalFormDTO professionalFormDTO) {
        professionalService.update(id, professionalFormDTO);
    }

    @GetMapping("/availability/list-all")
    public List<ProfessionalAvailabilityDTO> listAllAvailabilities() {
        return professionalService.listAllAvailabilities();
    }

    @GetMapping("/availability/{professionalId}")
    public List<ProfessionalAvailabilityDTO> listAvailabilities(@PathVariable("professionalId") Long id) {
        return professionalService.listAvailabilitiesByProfessionalId(id);
    }

    @GetMapping("/availability/date/{date}")
    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByDate(@PathVariable("date") LocalDate date) {
        return professionalService.listAvailabilitiesByDate(date);
    }

    @GetMapping("/availability/day/{dayOfWeek}")
    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByDayOfWeek(@PathVariable("dayOfWeek") DayOfWeek dayOfWeek) {
        return professionalService.listAvailabilitiesByDayOfWeek(dayOfWeek.getValue());
    }

    @GetMapping("/availability/hour/{hour}")
    public List<ProfessionalAvailabilityDTO> listAvailabilitiesByHour(@PathVariable("hour") Integer hour) {
        return professionalService.listAvailabilitiesByHour(hour);
    }

    @GetMapping("/availability/procedure/{id}")
    public ProfessionalAvailabilityDTO listAvailabilitiesByProcedure(@PathVariable("id") Long id) {
        return new ProfessionalAvailabilityDTO(professionalService.findByProdecureId(id));
    }

    @PutMapping("/availability/update/{id}")
    public void updateAvailability(@PathVariable("id") Long id,
                                   @RequestBody ProfessionalAvailabilityFormDTO professionalAvailabilityFormDTO) {
        professionalService.updateAvailability(id, professionalAvailabilityFormDTO);
    }

}
