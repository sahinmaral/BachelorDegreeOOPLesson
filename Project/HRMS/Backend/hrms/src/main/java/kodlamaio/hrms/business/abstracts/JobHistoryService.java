package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.entities.concretes.Cv;
import kodlamaio.hrms.entities.concretes.JobHistory;
import kodlamaio.hrms.entities.dtos.JobHistoryDto;

import java.util.List;

public interface JobHistoryService {
    Result add(JobHistory jobHistory);
    Result update(JobHistory jobHistory);
    Result delete(int jobHistoryId);
    DataResult<List<JobHistory>> getAllByCvId(int cvId);
    DataResult<List<JobHistory>> getAllByCandidateId(int candidateId);
}
