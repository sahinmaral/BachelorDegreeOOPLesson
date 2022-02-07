package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.*;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.result.*;
import kodlamaio.hrms.dataAccess.abstracts.CvDao;
import kodlamaio.hrms.dataAccess.abstracts.ImageDao;
import kodlamaio.hrms.dataAccess.abstracts.SchoolDao;
import kodlamaio.hrms.entities.concretes.Cv;
import kodlamaio.hrms.entities.concretes.Image;
import kodlamaio.hrms.entities.concretes.School;
import kodlamaio.hrms.entities.dtos.SchoolDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CvManager implements CvService {

    @Autowired
    public CvManager(CvDao cvDao,
                     ImageUploadService imageUploadService,
                     CandidateKnowledgeService candidateKnowledgeService,
                     SchoolService schoolService,
                     JobHistoryService jobHistoryService,
                     SchoolDao schoolDao, CandidateForeignLanguageService candidateForeignLanguageService,
                     ImageDao imageDao
    )
    {
        this.cvDao = cvDao;
        this.imageDao = imageDao;
        this.candidateKnowledgeService = candidateKnowledgeService;
        this.imageUploadService = imageUploadService;
        this.schoolService = schoolService;
        this.jobHistoryService = jobHistoryService;
        this.candidateForeignLanguageService = candidateForeignLanguageService;
    }

    private final CandidateKnowledgeService candidateKnowledgeService;
    private final SchoolService schoolService;
    private final JobHistoryService jobHistoryService;
    private final ImageUploadService imageUploadService;
    private final CvDao cvDao;
    private final ImageDao imageDao;
    private final CandidateForeignLanguageService candidateForeignLanguageService;

    @Override
    public DataResult<Cv> add(Cv cv) {

        //Validator eklenecek

        /*
        for (var validator : validators){
            if (!validator.isSuccess())
                return new ErrorResult(validator.getMessage());
        }*/

        cvDao.save(cv);

        if (cv.getSchools() != null){
            for (var school : cv.getSchools()){
                school.setCv(cv);
                schoolService.add(school);
            }
        }


        candidateForeignLanguageService.addOneByOne
                (cv.getCandidateForeignLanguages(),cv);

        candidateKnowledgeService.addOneByOne
                (cv.getCandidateKnowledges(),cv);

        /*

        schoolService.addOneByOne
                (cv.getSchools(),cv);

        jobHistoryService.addOneByOne
                (cv.getJobHistories(),cv);
                */

        return new SuccessDataResult<>(cv, Messages.SuccessfullyAdded);
    }

    @Override
    public Result update(Cv cv) {
        Cv foundCv = cvDao.findCvById(cv.getId());


        if (foundCv.equals(null))
            return new ErrorResult(Messages.CvDoesntExisted);

        if (!cv.getGithubAdress().equals(foundCv.getGithubAdress()))
            foundCv.setGithubAdress(cv.getGithubAdress());

        if (!cv.getLinkedinAdress().equals(foundCv.getLinkedinAdress()))
            foundCv.setLinkedinAdress(cv.getLinkedinAdress());

        if (!cv.getGithubAdress().equals(foundCv.getCoveringLetter()))
            foundCv.setCoveringLetter(cv.getCoveringLetter());


        var difKnowledges = cv.getCandidateKnowledges();
        var myKnowledges = foundCv.getCandidateKnowledges();

        foundCv.setCandidateKnowledges(new ArrayList<>());

        for (var myKnowledge : myKnowledges){
            candidateKnowledgeService.deleteById(myKnowledge.getId());
        }

        for (var difKnowledge : difKnowledges){
            difKnowledge.setCv(foundCv);
        }

        foundCv.setCandidateKnowledges(difKnowledges);



        var difForeignLanguages = cv.getCandidateForeignLanguages();
        var myForeignLanguages = foundCv.getCandidateForeignLanguages();

        foundCv.setCandidateForeignLanguages(new ArrayList<>());

        for (var myForeignLanguage : myForeignLanguages){
            candidateForeignLanguageService.deleteById(myForeignLanguage.getId());
        }

        for (var difForeignLanguage : difForeignLanguages){
            difForeignLanguage.setCv(foundCv);
        }

        foundCv.setCandidateForeignLanguages(difForeignLanguages);

        cvDao.save(foundCv);
        return new SuccessResult(Messages.SuccessfullyUpdated);
    }

    @Override
    public DataResult<List<Cv>> getAll() {
        return new SuccessDataResult<>
                (cvDao.findAll(),Messages.SuccessfullyRetrieved);
    }

    @Override
    public DataResult<List<Cv>> getAllByCandidateId(int candidateId) {
        return new SuccessDataResult<>
                (cvDao.getAllByCandidateId(candidateId),Messages.SuccessfullyRetrieved);
    }

    //Cannot delete previous images
    @Override
    public Result uploadImage(int cvId , MultipartFile multipartFile) {

        if(cvDao.existsById(cvId)){

            var imageMap = imageUploadService.uploadPicture(multipartFile).getData();

            var imageUrl = imageMap.get("url").toString();
            var publicImageId = imageMap.get("public_id").toString();

            Image image = new Image();
            image.setPublicImageId(publicImageId);
            image.setUrl(imageUrl);

            for (var deletingImage : imageDao.getAllByCvId(cvId)){
                imageDao.delete(deletingImage);
            }

            imageDao.save(image);

            var cv = findById(cvId).getData();
            cv.setImage(image);

            cvDao.save(cv);

            return new SuccessResult(Messages.SuccessfullyAdded);
        }

        return new ErrorResult(Messages.CvDoesntExisted);

    }

    @Override
    public DataResult<Cv> findById(int id) {
        return new SuccessDataResult<>(cvDao.findCvById(id));
    }

    @Override
    public Result deleteCv(int cvId) {

        Cv foundCv = cvDao.findCvById(cvId);

        if (cvDao.existsById(cvId)){

            if (foundCv.getImage() != null){
                if (!imageUploadService.deletePicture(foundCv.getImage().getPublicImageId()).isSuccess())
                    return new ErrorResult(Messages.ErrorDeletingPicture);
            }

            if (foundCv.getCandidateKnowledges().size() != 0){
                candidateKnowledgeService.deleteByCvId(cvId);
            }

            if (foundCv.getCandidateForeignLanguages().size() != 0){
                candidateForeignLanguageService.deleteByCvId(cvId);
            }

            foundCv.getSchools().forEach(school -> schoolService.deleteSchool(school.getId()));
            foundCv.getJobHistories().forEach(jobHistory -> jobHistoryService.delete(jobHistory.getId()));

            cvDao.deleteById(cvId);
            return new SuccessResult(Messages.SuccessfullyDeleted);
        }


        return new ErrorResult(Messages.CvDoesntExisted);
    }

    @Override
    public Result deleteImageToCv(int cvId) {

        Cv foundCv = null;

        for (var cv : cvDao.findAll()){
            if (cv.getId() == cvId){
                foundCv = cv;
            }
        }

        if (foundCv.equals(null))
            return new ErrorResult(Messages.CvDoesntExisted);

        Image foundImage = imageDao.findById(foundCv.getImage().getId());
        imageDao.delete(foundImage);

        var result = imageUploadService.deletePicture(foundCv.getImage().getPublicImageId());

        if (!result.isSuccess())
            return result;

        foundCv.setImage(null);
        cvDao.save(foundCv);
        return new SuccessResult(Messages.SuccessfullyDeleted);

    }


}
