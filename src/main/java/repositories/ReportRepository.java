
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select avg(r.notes.size), min(r.notes.size), max(r.notes.size), stddev(r.notes.size) from Report r")
	Collection<Double> statsOfNotesPerReport();

}
