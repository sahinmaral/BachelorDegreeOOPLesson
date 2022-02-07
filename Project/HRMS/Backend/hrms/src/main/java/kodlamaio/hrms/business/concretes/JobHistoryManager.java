package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.JobHistoryService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.result.*;
import kodlamaio.hrms.dataAccess.abstracts.JobHistoryDao;
import kodlamaio.hrms.entities.concretes.JobHistory;
import kodlamaio.hrms.entities.concretes.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobHistoryManager implements JobHistoryService {

    @Autowired
    public JobHistoryManager(JobHistoryDao jobHistoryDao) {
        this.jobHistoryDao = jobHistoryDao;
    }

    private JobHistoryDao jobHistoryDao;
    
    @Override
    public Result add(JobHistory jobHistory) {
        if(jobHistory.getFinishedDate().compareTo(LocalDate.now()) == 1)
            jobHistory.setFinished(false);
        else
            jobHistory.setFinished(true);

        jobHistoryDao.save(jobHistory);
        return new SuccessResult(Messages.SuccessfullyAdded);
    }


    @Override
    public DataResult<List<JobHistory>> getAllByCvId(int cvId) {
        var jobHistories = jobHistoryDao.getAllByCvId(cvId);

        return new SuccessDataResult<>(jobHistories,Messages.SuccessfullyRetrieved);
    }

    @Override
    public Result update(JobHistory jobHistory) {
        JobHistory foundJobHistory = jobHistoryDao.getById(jobHistory.getId());

        if (foundJobHistory.equals(null))
            return new ErrorResult(Messages.SchoolDoesntExisted);

        if (!foundJobHistory.getCompanyName().equals(jobHistory.getCompanyName()))
            foundJobHistory.setCompanyName(jobHistory.getCompanyName());

        if (foundJobHistory.getJobPosition().getId() != jobHistory.getJobPosition().getId())
            foundJobHistory.setJobPosition(jobHistory.getJobPosition());

        if (!foundJobHistory.getStartedDate().equals(jobHistory.getStartedDate()))
            foundJobHistory.setStartedDate(jobHistory.getStartedDate());

        if (foundJobHistory.getFinishedDate() != jobHistory.getFinishedDate())
            foundJobHistory.setFinishedDate(jobHistory.getFinishedDate());


        if (jobHistory.getFinishedDate() != null)
            jobHistory.setFinished(true);
        else
            jobHistory.setFinished(false);


        foundJobHistory.setCv(jobHistory.getCv());

        jobHistoryDao.save(foundJobHistory);

        return new SuccessResult(Messages.SuccessfullyUpdated);
    }

    @Override
    public Result delete(int jobHistoryId) {
        JobHistory foundJobHistory = jobHistoryDao.getById(jobHistoryId);

        if (foundJobHistory == null)
            return new ErrorResult(Messages.JobHistoryDoesntExisted);

        jobHistoryDao.delete(foundJobHistory);
        return new SuccessResult(Messages.SuccessfullyDeleted);
    }

    @Override
    public DataResult<List<JobHistory>> getAllByCandidateId( int candidateId) {
        var jobHistories = jobHistoryDao.getAllByCvCandidateId(candidateId);

        return new SuccessDataResult<>(jobHistories,Messages.SuccessfullyRetrieved);
    }

}
