package kodlamaio.hrms.api.controller;

import kodlamaio.hrms.business.abstracts.JobHistoryService;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.entities.concretes.JobHistory;
import kodlamaio.hrms.entities.dtos.JobHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobHistories")
public class JobHistoriesController {

    @Autowired
    public JobHistoriesController(JobHistoryService jobHistoryService) {
        this.jobHistoryService = jobHistoryService;
    }

    private JobHistoryService jobHistoryService;

    @GetMapping("/getAllByCandidateId")
    public DataResult<List<JobHistory>> getAllByCandidateId(@RequestParam int candidateId){
        return jobHistoryService.getAllByCandidateId(candidateId);
    }

    @GetMapping("/getAllByCvId")
    public DataResult<List<JobHistory>> getAllByCvId(@RequestParam int cvId){
        return jobHistoryService.getAllByCvId(cvId);
    }

    @PostMapping("/addJobHistory")
    public Result add(@RequestBody JobHistory jobHistory){
        return jobHistoryService.add(jobHistory);
    }

    @PutMapping("/updateJobHistory")
    public Result update(@RequestBody JobHistory jobHistory){
        return jobHistoryService.update(jobHistory);
    }

    @DeleteMapping("/deleteJobHistory")
    public Result delete(@RequestParam int jobHistoryId){
        return jobHistoryService.delete(jobHistoryId);
    }

}
