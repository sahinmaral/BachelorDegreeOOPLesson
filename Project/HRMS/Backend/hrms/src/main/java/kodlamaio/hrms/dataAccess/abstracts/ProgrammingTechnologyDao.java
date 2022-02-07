package kodlamaio.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammingTechnologyDao extends JpaRepository<kodlamaio.hrms.entities.concretes.ProgrammingTechnology,Integer> {
}
