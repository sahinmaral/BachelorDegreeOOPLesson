package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.entities.concretes.Cv;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CvService {
    DataResult<Cv> add(Cv cv);
    Result update(Cv cv);
    DataResult<List<Cv>> getAll();
    DataResult<List<Cv>> getAllByCandidateId(int candidateId);
    Result uploadImage(int cvId , MultipartFile multipartFile);
    DataResult<Cv> findById(int id);

    Result deleteCv(int cvId);

    Result deleteImageToCv(int cvId);
}
