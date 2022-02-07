package kodlamaio.hrms.dataAccess.abstracts;

import kodlamaio.hrms.entities.concretes.VerificationCodeCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeCandidateDao extends JpaRepository<VerificationCodeCandidate, Integer> {
}
