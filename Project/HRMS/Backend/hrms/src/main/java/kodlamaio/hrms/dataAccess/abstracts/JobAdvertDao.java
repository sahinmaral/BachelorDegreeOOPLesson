package kodlamaio.hrms.dataAccess.abstracts;

import kodlamaio.hrms.entities.concretes.JobAdvert;
import kodlamaio.hrms.entities.dtos.JobAdvertDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface JobAdvertDao extends JpaRepository<JobAdvert,Integer> {
    JobAdvert getById(int jobAdvertId);
    JobAdvert findById(int jobAdvertId);

    @Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertDto" +
            "(ja.id,e.companyName,ja.maxSalary,ja.minSalary," +
            "c.name,ja.deadline,ja.publishedDate,ja.countOfReceivableCandidate," +
            "ja.jobRequirements,jb.title,ja.isActive,ja.isVerified,wt.name,wp.name)" +
            "FROM JobAdvert ja " +
            "INNER JOIN ja.jobPositions jb " +
            "INNER JOIN ja.cities c " +
            "INNER JOIN ja.employers e " +
            "INNER JOIN ja.workingTimes wt " +
            "INNER JOIN ja.workingPlaces wp " )
   List<JobAdvertDto> getAllAdsByDetails();

    @Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertDto" +
            "(ja.id,e.companyName,ja.maxSalary,ja.minSalary," +
            "c.name,ja.deadline,ja.publishedDate,ja.countOfReceivableCandidate," +
            "ja.jobRequirements,jb.title,ja.isActive,ja.isVerified,wt.name,wp.name)" +
            "FROM JobAdvert ja " +
            "INNER JOIN ja.jobPositions jb " +
            "INNER JOIN ja.cities c " +
            "INNER JOIN ja.employers e " +
            "INNER JOIN ja.workingTimes wt " +
            "INNER JOIN ja.workingPlaces wp " +
            "WHERE ja.id=:id" )
    JobAdvertDto getJobAdvertById(int id);

    @Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertDto" +
            "(ja.id,e.companyName,ja.maxSalary,ja.minSalary," +
            "c.name,ja.deadline,ja.publishedDate,ja.countOfReceivableCandidate," +
            "ja.jobRequirements,jb.title,ja.isActive,ja.isVerified,wt.name,wp.name)" +
            "FROM JobAdvert ja " +
            "INNER JOIN ja.jobPositions jb " +
            "INNER JOIN ja.cities c " +
            "INNER JOIN ja.employers e " +
            "INNER JOIN ja.workingTimes wt " +
            "INNER JOIN ja.workingPlaces wp " +
            "WHERE ja.isActive=true and ja.isVerified=true")
    List<JobAdvertDto> getAllActiveAdsByDetails();


    @Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertDto" +
            "(ja.id,e.companyName,ja.maxSalary,ja.minSalary," +
            "c.name,ja.deadline,ja.publishedDate,ja.countOfReceivableCandidate," +
            "ja.jobRequirements,jb.title,ja.isActive,ja.isVerified,wt.name,wp.name)" +
            "FROM JobAdvert ja " +
            "INNER JOIN ja.jobPositions jb " +
            "INNER JOIN ja.cities c " +
            "INNER JOIN ja.employers e " +
            "INNER JOIN ja.workingTimes wt " +
            "INNER JOIN ja.workingPlaces wp " +
            "WHERE ja.deadline=:deadline")
    List<JobAdvertDto> getAllActiveAdsByDeadline(LocalDate deadline);


    @Query("SELECT new kodlamaio.hrms.entities.dtos.JobAdvertDto" +
            "(ja.id,e.companyName,ja.maxSalary,ja.minSalary," +
            "c.name,ja.deadline,ja.publishedDate,ja.countOfReceivableCandidate," +
            "ja.jobRequirements,jb.title,ja.isActive,ja.isVerified,wt.name,wp.name)" +
            "FROM JobAdvert ja " +
            "INNER JOIN ja.jobPositions jb " +
            "INNER JOIN ja.cities c " +
            "INNER JOIN ja.employers e " +
            "INNER JOIN ja.workingTimes wt " +
            "INNER JOIN ja.workingPlaces wp " +
            "WHERE e.id=:employerId AND ja.isActive=true")
    List<JobAdvertDto> getAllActiveAdsByEmployerId(int employerId);

}
