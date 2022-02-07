package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.entities.concretes.Cv;
import kodlamaio.hrms.entities.concretes.School;
import kodlamaio.hrms.entities.dtos.SchoolDto;

import java.util.List;

public interface SchoolService {
    Result add(School school);
    DataResult<List<School>> getAll();
    Result deleteSchool(int schoolId);

    DataResult<School> getById(int id);
    Result update(School school);
    DataResult<List<School>> getAllByCandidateId(int candidateId);
    DataResult<List<School>> getAllByCvId(int cvId);
}
