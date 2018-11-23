
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;
import domain.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {
	
	@Query("select t from Tutorial t where t.handyWorker = ?1")
	Collection<Tutorial> findTutorialForHW(HandyWorker handyWorker);

}
