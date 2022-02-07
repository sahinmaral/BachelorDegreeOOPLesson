package kodlamaio.hrms.api.controller;

import kodlamaio.hrms.business.abstracts.WorkingPlaceService;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.SuccessDataResult;
import kodlamaio.hrms.entities.concretes.WorkingPlace;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/workingPlaces")
@RestController
public class WorkingPlacesController {
    public WorkingPlacesController(WorkingPlaceService workingPlaceService) {
        this.workingPlaceService = workingPlaceService;
    }

    private WorkingPlaceService workingPlaceService;

    @GetMapping("/getAll")
    public DataResult<List<WorkingPlace>> getAll(){
        return new SuccessDataResult<List<WorkingPlace>>(workingPlaceService.getAll().getData());
    }

}
