package kodlamaio.hrms.api.controller;

import kodlamaio.hrms.business.abstracts.CvService;
import kodlamaio.hrms.core.utilities.result.*;
import kodlamaio.hrms.entities.concretes.Cv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/cvs")
public class CvController {

    @Autowired
    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    private CvService cvService;

    @PostMapping("/addCv")
    public DataResult<Cv> addCv(@RequestBody Cv cv){

        var result = cvService.add(cv);
        if (!result.isSuccess()) return new ErrorDataResult<>(result.getMessage());

        return new SuccessDataResult<>(result.getData(),result.getMessage());
    }

    @GetMapping("/getAll")
    public DataResult<List<Cv>> getAll(){
        return new SuccessDataResult<>(cvService.getAll().getData());
    }

    @GetMapping("/getAllByCandidateId")
    public DataResult<List<Cv>> getAllByCandidateId(@RequestParam int candidateId){
        return new SuccessDataResult<>(cvService.getAllByCandidateId(candidateId).getData());
    }

    @PostMapping("/uploadImageToCv")
    public Result uploadImage(@RequestParam int cvId , @RequestParam MultipartFile multipartFile){
        return cvService.uploadImage(cvId , multipartFile);
    }

    @DeleteMapping("/deleteCv")
    public Result deleteCv(@RequestParam int cvId){
        return cvService.deleteCv(cvId);
    }

    @DeleteMapping("/deleteImageToCv")
    public Result deleteImageToCv(@RequestParam int cvId){
        return cvService.deleteImageToCv(cvId);
    }

    @PutMapping("/updateCv")
    public Result updateCv(@RequestBody Cv cv){

        var result = cvService.update(cv);
        if (!result.isSuccess()) return new ErrorResult(result.getMessage());

        return result;
    }
}
