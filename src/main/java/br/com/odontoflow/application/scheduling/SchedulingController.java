package br.com.odontoflow.application.scheduling;

import br.com.odontoflow.domain.scheduling.SchedulingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

    private final SchedulingService schedulingService;

    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @PostMapping("/create")
    public void schedulerRegister(@RequestBody SchedulingFormDTO schedulingFormDTO){
        schedulingService.register(schedulingFormDTO);
    }

    @GetMapping("/list/patient/{id}")
    public List<SchedulingDTO> listByPatient(@PathVariable("id") Long id,
                                             @RequestParam(value = "date", required = false) Optional<LocalDate> date) {
        return schedulingService.findAllByPatientId(id, date);
    }

    @GetMapping("/list/professional/{id}")
    public List<SchedulingDTO> listByProfessional(@PathVariable("id") Long id,
                                                  @RequestParam(value = "date", required = false) Optional<LocalDate> date) {
        return schedulingService.findAllByProfessionalId(id, date);
    }

    @PutMapping("/update/{id}")
    public void updateSchedule(@PathVariable("id") Long id, @RequestBody SchedulingFormDTO formDTO) {
        schedulingService.update(id, formDTO);
    }

}
