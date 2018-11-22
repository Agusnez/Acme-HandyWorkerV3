
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select hw from HandyWorker hw where hw.userAccount.id = ?1")
	HandyWorker findByUserAccountId(int userAccountId);

	@Query("select distinct hw.name,count(a) from HandyWorker hw join hw.applications a where a.status = 'ACCEPTED' group by hw having count(a) >= (select count(a) from Application a where a.status = 'ACCEPTED')/(select count(distinct hw) from HandyWorker hw join hw.applications a where a.status = 'ACCEPTED')*1.1 order by count(a)")
	Collection<HandyWorker> handyWorkersTenPerCentMore();

	@Query("select distinct h.name, count(c) from HandyWorker h join h.applications a join a.fixUpTask f join f.complaints c where a.status='ACCEPTED' group by h order by count(c) desc")
	Collection<HandyWorker> rankingHandyWorkersComplaints();
}
