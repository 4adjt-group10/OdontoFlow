package br.com.odontoflow.application.professional;

import br.com.odontoflow.domain.professional.ProfessionalService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ProfessionalDTO> professionalRegister(@RequestBody @Valid ProfessionalFormDTO professionalFormDTO){
        return ResponseEntity.status(CREATED).body(professionalService.register(professionalFormDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProfessionalDTO>> listAll() {
        return ResponseEntity.ok(professionalService.listAllProfessionals());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfessionalDTO> update(@PathVariable("id") Long id, @RequestBody @Valid ProfessionalFormDTO professionalFormDTO) {
        return ResponseEntity.ok(professionalService.update(id, professionalFormDTO));
    }

    @PostMapping("/availability/create")
    public ResponseEntity<ProfessionalAvailabilityDTO> addRegister(@RequestBody @Valid ProfessionalAvailabilityFormDTO professionalAvailabilityFormDTO){
        return ResponseEntity.status(CREATED).body(professionalService.registerAvailability(professionalAvailabilityFormDTO));
    }

    @GetMapping("/availability/list-all")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAllAvailabilities() {
        return ResponseEntity.ok(professionalService.listAllAvailabilities());
    }

    @GetMapping("/availability/{professionalId}")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAvailabilities(@PathVariable("professionalId") Long id) {
        return ResponseEntity.ok(professionalService.listAvailabilitiesByProfessionalId(id));
    }

    @GetMapping("/availability/date/{date}")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAvailabilitiesByDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(professionalService.listAvailabilitiesByDate(date));
    }

    @GetMapping("/availability/day/{dayOfWeek}")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAvailabilitiesByDayOfWeek(@PathVariable("dayOfWeek") DayOfWeek dayOfWeek) {
        return ResponseEntity.ok(professionalService.listAvailabilitiesByDayOfWeek(dayOfWeek.getValue()));
    }

    @GetMapping("/availability/hour/{hour}")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAvailabilitiesByHour(@PathVariable("hour") Integer hour) {
        return ResponseEntity.ok(professionalService.listAvailabilitiesByHour(hour));
    }

    @GetMapping("/availability/procedure/{id}")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAvailabilitiesByProcedure(@PathVariable("id") Long id) {
        return ResponseEntity.ok(professionalService.findByProcedureId(id));
    }

    @PutMapping("/availability/update/{id}")
    public ResponseEntity<ProfessionalAvailabilityDTO> updateAvailability(@PathVariable("id") Long id,
                                                                          @RequestBody @Valid ProfessionalAvailabilityFormDTO professionalAvailabilityFormDTO) {
        return ResponseEntity.ok(professionalService.updateAvailability(id, professionalAvailabilityFormDTO));
    }

}
