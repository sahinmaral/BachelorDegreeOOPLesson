package kodlamaio.hrms.dataAccess.abstracts;

import kodlamaio.hrms.entities.concretes.CandidateKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateKnowledgeDao extends JpaRepository<CandidateKnowledge,Integer> {

    List<CandidateKnowledge> getCandidateKnowledgesByCv_Id(int id);
    CandidateKnowledge getCandidateKnowledgeById(int id);
}
