
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select avg(a.offeredPrice.amount), min(a.offeredPrice.amount), max(a.offeredPrice.amount), stddev(a.offeredPrice.amount) from Application a")
	Collection<Double> statsOfOfferedPricePerApplication();

	@Query("select (select count(a1) from Application a1 where a1.status='PENDING')/count(a2)*1.0 from Application a2")
	Double ratioOfApplicationsPending();

	@Query("select (select count(a1) from Application a1 where a1.status='ACCEPTED')/count(a2)*1.0 from Application a2")
	Double ratioOfApplicationsAccepted();

	@Query("select (select count(a1) from Application a1 where a1.status='REJECTED')/count(a2)*1.0 from Application a2")
	Double ratioOfApplicationsRejected();

	@Query("select (select count(a1) from Application a1 where (a1.status='PENDING' and a1.fixUpTask.startDate < CURRENT_TIMESTAMP))/count(a2)*1.0 from Application a2")
	Double ratioOfApplicationsPendingElapsedPeriod();

	@Query("select a from Customer c join c.fixUpTasks f join f.applications a where c.id = ?1")
	Collection<Application> findApplicationsByCustomer(final int customerId);

	@Query("select hw.applications from HandyWorker hw  where hw.id = ?1")
	Collection<Application> findApplicationsByHandyWorker(final int handyWorkerId);
}
