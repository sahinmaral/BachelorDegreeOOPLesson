package kodlamaio.hrms.dataAccess.abstracts;

import kodlamaio.hrms.entities.concretes.Cv;
import kodlamaio.hrms.entities.dtos.SchoolDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CvDao extends JpaRepository<Cv,Integer> {

    Cv findCvById(int id);
    List<Cv> getAllByCandidateId(int candidateId);
}
