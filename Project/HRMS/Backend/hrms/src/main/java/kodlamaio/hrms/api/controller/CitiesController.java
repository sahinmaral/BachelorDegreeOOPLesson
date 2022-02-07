package kodlamaio.hrms.api.controller;

import kodlamaio.hrms.business.abstracts.CityService;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.core.utilities.result.SuccessDataResult;
import kodlamaio.hrms.core.utilities.result.SuccessResult;
import kodlamaio.hrms.entities.concretes.City;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/cities")
@RestController
public class CitiesController {
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    private CityService cityService;

    @GetMapping("/getAll")
    public DataResult<List<City>> getAll(){
        return new SuccessDataResult<List<City>>(cityService.getAll().getData());
    }


    @PostMapping("/add")
    public Result add(City city){
        cityService.add(city);
        return new SuccessResult();
    }

}
