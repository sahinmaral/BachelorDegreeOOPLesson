package kodlamaio.hrms.dataAccess.abstracts;

import kodlamaio.hrms.entities.concretes.CandidateForeignLanguage;
import kodlamaio.hrms.entities.concretes.CandidateKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateForeignLanguageDao extends JpaRepository<CandidateForeignLanguage,Integer> {
    List<CandidateForeignLanguage> getCandidateForeignLanguagesByCv_Id(int id);
    CandidateForeignLanguage getCandidateForeignLanguageById(int id);
}
