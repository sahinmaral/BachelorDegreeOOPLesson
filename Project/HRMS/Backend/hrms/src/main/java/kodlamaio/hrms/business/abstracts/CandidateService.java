package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.entities.Candidate;
import kodlamaio.hrms.core.utilities.result.Result;

public interface CandidateService extends UserService<Candidate> {
    Result verifyAccountByVerificationCode(String email,String code);
}
