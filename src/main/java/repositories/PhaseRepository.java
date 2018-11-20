
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

	@Query("select count(p) from Phase p where p.fixUpTask.id = ?1")
	Integer findPhasesByFixUpTaskId(int fixUpTaskId);

}
