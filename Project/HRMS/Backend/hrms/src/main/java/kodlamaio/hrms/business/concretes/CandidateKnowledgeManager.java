package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.CandidateKnowledgeService;
import kodlamaio.hrms.business.abstracts.ProgrammingTechnologyService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.result.*;
import kodlamaio.hrms.dataAccess.abstracts.CandidateKnowledgeDao;
import kodlamaio.hrms.entities.concretes.CandidateKnowledge;
import kodlamaio.hrms.entities.concretes.Cv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateKnowledgeManager implements CandidateKnowledgeService {

    @Autowired
    public CandidateKnowledgeManager
            (
                    CandidateKnowledgeDao candidateKnowledgeDao,
                    ProgrammingTechnologyService programmingTechnologyService
            ) {
        this.candidateKnowledgeDao = candidateKnowledgeDao;
        this.programmingTechnologyService = programmingTechnologyService;
    }

    private final ProgrammingTechnologyService programmingTechnologyService;
    private final CandidateKnowledgeDao candidateKnowledgeDao;
    
    @Override
    public Result add(CandidateKnowledge candidateKnowledge) {
        candidateKnowledgeDao.save(candidateKnowledge);
        return new SuccessResult();
    }

    @Override
    public Result deleteByCvId(int cvId) {
        var knowledges = candidateKnowledgeDao.getCandidateKnowledgesByCv_Id(cvId);

        if (knowledges.size() == 0)
            return new ErrorResult(Messages.NoCandidateKnowledgeOnCv);
        else
        {
            for (var knowledge : knowledges)
                candidateKnowledgeDao.delete(knowledge);
            return new SuccessResult(Messages.SuccessfullyDeleted);
        }
    }

    @Override
    public Result deleteById(int id) {
        var knowledge  = candidateKnowledgeDao.getCandidateKnowledgeById(id);
        if (knowledge == null)
            return new ErrorResult(Messages.CandidateKnowledgeDoesntExist);

        candidateKnowledgeDao.delete(knowledge);
        return new SuccessResult(Messages.SuccessfullyDeleted);
    }

    @Override
    public DataResult<CandidateKnowledge> getCandidateKnowledgeById(int id) {
        var knowledge  = candidateKnowledgeDao.getCandidateKnowledgeById(id);
        if (knowledge == null)
            return new ErrorDataResult<>(Messages.CandidateKnowledgeDoesntExist);

        return new SuccessDataResult<>(knowledge,Messages.SuccessfullyRetrieved);
    }

    @Override
    public Result addOneByOne(List<CandidateKnowledge> candidateKnowledges, Cv cv) {
        for (var item : candidateKnowledges){
            item.setCv(cv);

            var programmingTech =
                    programmingTechnologyService.getById
                            (item.getProgrammingTechnology().getId());

            item.setProgrammingTechnology(programmingTech.getData());

            add(item);
        }

        return new SuccessResult();
    }
}
