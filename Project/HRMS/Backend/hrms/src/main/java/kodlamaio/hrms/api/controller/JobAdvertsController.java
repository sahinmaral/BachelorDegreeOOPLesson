package kodlamaio.hrms.api.controller;

import kodlamaio.hrms.business.abstracts.JobAdvertService;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.core.utilities.result.SuccessDataResult;
import kodlamaio.hrms.entities.concretes.JobAdvert;
import kodlamaio.hrms.entities.dtos.JobAdvertDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/jobAdverts")
@RestController
public class JobAdvertsController {
    public JobAdvertsController(JobAdvertService jobAdvertService) {
        this.jobAdvertService = jobAdvertService;
    }

    private JobAdvertService jobAdvertService;

    @GetMapping("/getAll")
    public DataResult<List<JobAdvert>> getAll() {
        return new SuccessDataResult<List<JobAdvert>>(jobAdvertService.getAll().getData());
    }

    @GetMapping("/getAllAdsByDetails")
    public DataResult<List<JobAdvertDto>> getAllAdsByDetails() {
        return new SuccessDataResult<List<JobAdvertDto>>(jobAdvertService.getAllAdsByDetails().getData());
    }

    @GetMapping("/getAllActiveAdsByDetails")
    public DataResult<List<JobAdvertDto>> getAllActiveAdsByDetails() {
        return new SuccessDataResult<List<JobAdvertDto>>(jobAdvertService.getAllActiveAdsByDetails().getData());
    }

    @GetMapping("/getAllActiveAdsByDeadline")
    public DataResult<List<JobAdvertDto>> getAllActiveAdsByDeadline
            (@RequestParam(name="deadline")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     LocalDate deadline)
    {
        return new SuccessDataResult<List<JobAdvertDto>>
                (jobAdvertService.getAllActiveAdsByDeadline(deadline).getData());
    }

    @GetMapping("/getAllActiveAdsByEmployerid")
    public DataResult<List<JobAdvertDto>> getAllActiveAdsByEmployerId(@RequestParam int employerId)
    {
        return new SuccessDataResult<List<JobAdvertDto>>
                (jobAdvertService.getAllActiveAdsByEmployerId(employerId).getData());
    }

    @GetMapping("/getJobAdvertById")
    public DataResult<JobAdvertDto> getJobAdvertById(@RequestParam int id)
    {
        return new SuccessDataResult<JobAdvertDto>
                (jobAdvertService.getJobAdvertById(id).getData());
    }


    @PostMapping("/disableById")
    public Result disableById(@RequestParam int jobAdvertId) {
    return jobAdvertService.disableById(jobAdvertId);
    }

    @PostMapping("/enableById")
    public Result enablebleById(@RequestParam int jobAdvertId) {
        return jobAdvertService.enableById(jobAdvertId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody JobAdvert jobAdvert) {
       return jobAdvertService.add(jobAdvert);
    }

}
