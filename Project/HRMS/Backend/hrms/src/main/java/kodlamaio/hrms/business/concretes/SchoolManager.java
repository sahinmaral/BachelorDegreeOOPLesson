package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.SchoolService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.result.*;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.CvDao;
import kodlamaio.hrms.dataAccess.abstracts.SchoolDao;
import kodlamaio.hrms.entities.concretes.School;
import kodlamaio.hrms.entities.dtos.SchoolDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SchoolManager implements SchoolService {

    @Autowired
    public SchoolManager(SchoolDao schoolDao, CandidateDao candidateDao, CvDao cvDao) {
        this.schoolDao = schoolDao;
        this.candidateDao = candidateDao;
        this.cvDao = cvDao;
    }

    private CandidateDao candidateDao;
    private CvDao cvDao;
    private SchoolDao schoolDao;
    
    @Override
    public Result add(School school) {
        if(school.getGraduatedDate().compareTo(LocalDate.now()) == 1)
            school.setGraduated(false);
        else
            school.setGraduated(true);

        schoolDao.save(school);
        return new SuccessResult(Messages.SuccessfullyAdded);
    }

    @Override
    public DataResult<List<School>> getAll() {

        var schools = schoolDao.findAll();

        return new SuccessDataResult<>(schools,Messages.SuccessfullyRetrieved);
    }



    @Override
    public Result deleteSchool(int schoolId) {

        School foundSchool = schoolDao.getById(schoolId);

        if (foundSchool == null)
            return new ErrorResult(Messages.SchoolDoesntExisted);



        schoolDao.delete(foundSchool);
        return new SuccessResult(Messages.SuccessfullyDeleted);
    }

    @Override
    public DataResult<School> getById(int id) {
        School foundSchool = schoolDao.getById(id);

        if (foundSchool == null)
            return new ErrorDataResult<>(Messages.SchoolDoesntExisted);

        return new SuccessDataResult<>(foundSchool,Messages.SuccessfullyDeleted);
    }

    @Override
    public Result update(School school) {
        School foundSchool = schoolDao.getById(school.getId());

        if (foundSchool.equals(null))
            return new ErrorResult(Messages.SchoolDoesntExisted);

        if (!foundSchool.getDepartment().equals(school.getDepartment()))
            foundSchool.setDepartment(school.getDepartment());

        if (!foundSchool.getName().equals(school.getName()))
            foundSchool.setName(school.getName());

        if (!foundSchool.getStartedDate().equals(school.getStartedDate()))
            foundSchool.setStartedDate(school.getStartedDate());

        if (foundSchool.getGraduatedDate() != school.getGraduatedDate())
            foundSchool.setGraduatedDate(school.getGraduatedDate());


        if (school.getGraduatedDate() != null)
            school.setGraduated(true);
        else
            school.setGraduated(false);


        foundSchool.setCv(school.getCv());

        schoolDao.save(foundSchool);

        return new SuccessResult(Messages.SuccessfullyUpdated);
    }

    @Override
    public DataResult<List<School>> getAllByCandidateId(int candidateId) {
        var schools = schoolDao.findAll();
        for (var school : schools){
            if (school.getCv().getCandidate().getId() != candidateId)
                schools.remove(schools);
        }

        return new SuccessDataResult<>(schools,Messages.SuccessfullyRetrieved);
    }

    @Override
    public DataResult<List<School>> getAllByCvId(int cvId) {
        var schools = schoolDao.getAllByCvId(cvId);
        return new SuccessDataResult<>(schools,Messages.SuccessfullyRetrieved);
    }
}
