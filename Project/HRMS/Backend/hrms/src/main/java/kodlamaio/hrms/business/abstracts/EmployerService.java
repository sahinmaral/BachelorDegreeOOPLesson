package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.entities.Employer;
import kodlamaio.hrms.core.utilities.result.Result;


public interface EmployerService extends UserService<Employer> {
    Result verifyAccountByVerificationCode(String email, String code);
}
