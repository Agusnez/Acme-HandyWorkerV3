package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.HandyWorker;
import domain.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Integer>{

	@Query("select t from Tutorial where t.handyWorker = ?1")
	public Collection<Tutorial> tutorialForHW(HandyWorker h);
}
