package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.WorkingPlaceService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.SuccessDataResult;
import kodlamaio.hrms.dataAccess.abstracts.WorkingPlaceDao;
import kodlamaio.hrms.entities.concretes.WorkingPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingPlaceManager implements WorkingPlaceService {

    @Autowired
    public WorkingPlaceManager(WorkingPlaceDao workingPlaceDao) {
        this.workingPlaceDao = workingPlaceDao;
    }

    private WorkingPlaceDao workingPlaceDao;

    @Override
    public DataResult<List<WorkingPlace>> getAll() {
        return new SuccessDataResult<>(workingPlaceDao.findAll(), Messages.SuccessfullyRetrieved);
    }


}
