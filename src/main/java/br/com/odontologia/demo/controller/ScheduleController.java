package br.com.odontologia.demo.controller;

import br.com.odontologia.demo.dto.scheduler.SchedulerRegisterFormDTO;
import br.com.odontologia.demo.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
