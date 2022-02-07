package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.FileUploadService;
import kodlamaio.hrms.business.abstracts.ImageUploadService;
import kodlamaio.hrms.core.utilities.result.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ImageUploadManager implements ImageUploadService {

    @Autowired
    public ImageUploadManager(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    private FileUploadService fileUploadService;

    @Override
    public DataResult<Map> uploadPicture(MultipartFile multipartFile) {

        return new SuccessDataResult<>(fileUploadService.
                upload(multipartFile));
    }

    @Override
    public Result deletePicture(String publicImageId) {
        try {
            fileUploadService.delete(publicImageId);
        }catch (Exception ex){
            return new ErrorResult(ex.getMessage());
        }
        return new SuccessResult();
    }
}
