package kodlamaio.hrms.api.controller;

import kodlamaio.hrms.business.abstracts.WorkingTimeService;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.SuccessDataResult;
import kodlamaio.hrms.entities.concretes.WorkingTime;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/workingTimes")
@RestController
public class WorkingTimesController {
    public WorkingTimesController(WorkingTimeService workingTimeService) {
        this.workingTimeService = workingTimeService;
    }

    private WorkingTimeService workingTimeService;

    @GetMapping("/getAll")
    public DataResult<List<WorkingTime>> getAll(){
        return new SuccessDataResult<List<WorkingTime>>(workingTimeService.getAll().getData());
    }

}
