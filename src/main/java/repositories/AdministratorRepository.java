
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), stddev(c.fixUpTasks.size) from Customer c")
	Collection<Double> statsOfFixUpTasksPerCustomer();

	@Query("select avg(f.applications.size), min(f.applications.size), max(f.applications.size), stddev(f.applications.size) from FixUpTask f")
	Collection<Double> statsOfApplicationsPerFixUpTask();

	@Query("select avg(f.maximumPrice.amount), min(f.maximumPrice.amount), max(f.maximumPrice.amount), stddev(f.maximumPrice.amount) from FixUpTask f")
	Collection<Double> statsOfMaximumPricePerFixUpTask();

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

	@Query("select distinct c1.name from Customer c1 join c1.fixUpTasks f where c1.fixUpTasks.size > (select avg(c2.fixUpTasks.size)*1.1 from Customer c2) order by f.applications.size")
	Collection<Customer> customersTenPerCentMore();

	@Query("select distinct hw.name,count(a) from HandyWorker hw join hw.applications a where a.status = 'ACCEPTED' group by hw having count(a) >= (select count(a) from Application a where a.status = 'ACCEPTED')/(select count(distinct hw) from HandyWorker hw join hw.applications a where a.status = 'ACCEPTED')*1.1 order by count(a)")
	Collection<HandyWorker> handyWorkersTenPerCentMore();

	@Query("select avg(f.complaints.size), min(f.complaints.size), max(f.complaints.size), stddev(f.complaints.size) from FixUpTask f")
	Collection<Double> statsOfComplaintsPerFixUpTask();

	@Query("select avg(r.notes.size), min(r.notes.size), max(r.notes.size), stddev(r.notes.size) from Report r")
	Collection<Double> statsOfNotesPerReport();

	@Query("select (select count(f1) from FixUpTask f1 where f1.complaints.size > 0)/count(f2)*1.0 from FixUpTask f2")
	Double ratioOfFixUpTasksWithComplaint();

	@Query("select c.name, c.complaints.size from Customer c order by c.complaints.size desc")
	Collection<Customer> rankingCustomersComplaints();

	@Query("select distinct h.name, count(c) from HandyWorker h join h.applications a join a.fixUpTask f join f.complaints c where a.status='ACCEPTED' group by h order by count(c) desc")
	Collection<HandyWorker> rankingHandyWorkersComplaints();

}
