package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.WorkingTimeService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.SuccessDataResult;
import kodlamaio.hrms.dataAccess.abstracts.WorkingTimeDao;
import kodlamaio.hrms.entities.concretes.WorkingTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingTimeManager implements WorkingTimeService {

    @Autowired
    public WorkingTimeManager(WorkingTimeDao workingTimeDao) {
        this.workingTimeDao = workingTimeDao;
    }

    private WorkingTimeDao workingTimeDao;

    @Override
    public DataResult<List<WorkingTime>> getAll() {
        return new SuccessDataResult<>(workingTimeDao.findAll(), Messages.SuccessfullyRetrieved);
    }


}
