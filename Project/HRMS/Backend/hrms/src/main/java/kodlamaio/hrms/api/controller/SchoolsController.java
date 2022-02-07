package kodlamaio.hrms.api.controller;

import kodlamaio.hrms.business.abstracts.SchoolService;
import kodlamaio.hrms.core.utilities.result.*;
import kodlamaio.hrms.entities.concretes.Cv;
import kodlamaio.hrms.entities.concretes.School;
import kodlamaio.hrms.entities.dtos.SchoolDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolsController {

    @Autowired
    public SchoolsController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    private SchoolService schoolService;

    @GetMapping("/getAllByCandidateId")
    public DataResult<List<School>> getAllByCandidateId(int candidateId){
        return schoolService.getAllByCandidateId(candidateId);
    }

    @GetMapping("/getAllCvId")
    public DataResult<List<School>> getAllByCvId(int cvId){
        return schoolService.getAllByCvId(cvId);
    }

    @PostMapping("/addSchool")
    public Result addSchool(@RequestBody School school){
        return schoolService.add(school);
    }

    @DeleteMapping("/deleteSchool")
    public Result deleteSchool(@RequestParam int schoolId){
        return schoolService.deleteSchool(schoolId);
    }

    @GetMapping("/getAll")
    public DataResult<List<School>> getAll(){
        return schoolService.getAll();
    }

    @PutMapping("/updateSchool")
    public Result updateSchool(@RequestBody School school){
        return schoolService.update(school);
    }
}
