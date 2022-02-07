package kodlamaio.hrms.dataAccess.abstracts;

import kodlamaio.hrms.entities.concretes.JobHistory;
import kodlamaio.hrms.entities.dtos.JobHistoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobHistoryDao extends JpaRepository<JobHistory,Integer> {

    JobHistory getById(int jobHistoryId);
    List<JobHistory> getAllByCvId(int cvId);
    List<JobHistory> getAllByCvCandidateId(int candidateId);
}
