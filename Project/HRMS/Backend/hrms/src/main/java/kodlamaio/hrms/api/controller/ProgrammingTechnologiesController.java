package kodlamaio.hrms.api.controller;

import kodlamaio.hrms.business.abstracts.ProgrammingTechnologyService;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.entities.concretes.ProgrammingTechnology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/programmingTechnologies")
public class ProgrammingTechnologiesController {

    private ProgrammingTechnologyService programmingTechnologyService;

    @Autowired
    public ProgrammingTechnologiesController(ProgrammingTechnologyService programmingTechnologyService) {
        this.programmingTechnologyService = programmingTechnologyService;
    }

    @GetMapping("/getAll")
    public DataResult<List<ProgrammingTechnology>> getAll(){
        return programmingTechnologyService.getAll();
    }


}
