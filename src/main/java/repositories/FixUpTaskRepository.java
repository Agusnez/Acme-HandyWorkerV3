
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select avg(f.applications.size), min(f.applications.size), max(f.applications.size), stddev(f.applications.size) from FixUpTask f")
	Collection<Double> statsOfApplicationsPerFixUpTask();

	@Query("select avg(f.maximumPrice.amount), min(f.maximumPrice.amount), max(f.maximumPrice.amount), stddev(f.maximumPrice.amount) from FixUpTask f")
	Collection<Double> statsOfMaximumPricePerFixUpTask();

	@Query("select avg(f.complaints.size), min(f.complaints.size), max(f.complaints.size), stddev(f.complaints.size) from FixUpTask f")
	Collection<Double> statsOfComplaintsPerFixUpTask();

	@Query("select (select count(f1) from FixUpTask f1 where f1.complaints.size > 0)/count(f2)*1.0 from FixUpTask f2")
	Double ratioOfFixUpTasksWithComplaint();

	@Query("select f from FixUpTask f join f.category c where c.id=?1")
	Collection<FixUpTask> findFixUpTaskPerCategory(int categoryId);

	@Query("select f from FixUpTask f join f.complaints c where c.id=?1")
	FixUpTask findFixUpTaskPerComplaint(int complaintId);

	@Query("select count(f) from FixUpTask f where f.warranty.id = ?1")
	Integer countFixUpTaskByWarrantyId(int warrantyId);

}
