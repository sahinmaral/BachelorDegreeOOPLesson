package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.entities.concretes.CandidateKnowledge;
import kodlamaio.hrms.entities.concretes.Cv;

import java.util.List;

public interface CandidateKnowledgeService {
    Result add(CandidateKnowledge candidateKnowledge);
    Result deleteByCvId(int cvId);
    Result deleteById(int id);
    DataResult<CandidateKnowledge> getCandidateKnowledgeById(int id);
    Result addOneByOne(List<CandidateKnowledge> candidateKnowledges, Cv cv);
}
