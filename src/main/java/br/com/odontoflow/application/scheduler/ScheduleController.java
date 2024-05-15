package br.com.odontoflow.application.scheduler;

import br.com.odontoflow.domain.scheduler.SchedulerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class ScheduleController {

    private final SchedulerService schedulerService;

    public ScheduleController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/create")
    public void schedulerRegister(@RequestBody SchedulerRegisterFormDTO schedulerRegisterFormDTO){
        schedulerService.schedulerRegister(schedulerRegisterFormDTO);
    }
}
