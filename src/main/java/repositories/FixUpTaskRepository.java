
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f join f.category c where c.id=?1")
	Collection<FixUpTask> findFixUpTaskPerCategory(int categoryId);

	@Query("select f from FixUpTask f join f.complaints c where c.id=?1")
	FixUpTask findFixUpTaskPerComplaint(int complaintId);
}
