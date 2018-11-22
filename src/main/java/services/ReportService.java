
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository

	@Autowired
	private ReportRepository	reportRepository;


	// Suporting services

	// Simple CRUD methods

	public Report create() {

		final Report result = new Report();

		return result;

	}

	public Collection<Report> findAll() {

		final Collection<Report> reports = this.reportRepository.findAll();

		Assert.notNull(reports);

		return reports;
	}

	public Report findOne(final int reportsID) {

		final Report report = this.reportRepository.findOne(reportsID);

		Assert.notNull(report);

		return report;

	}

	public Report save(final Report s) {

		final Report report = this.reportRepository.save(s);

		Assert.notNull(report);

		return report;

	}

	public void delete(final Report report) {

		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		this.reportRepository.delete(report);
	}

	// Other business methods

	public Collection<Double> statsOfNotesPerReport() {

		final Collection<Double> result = this.reportRepository.statsOfNotesPerReport();
		Assert.notNull(result);
		return result;

	}

}
