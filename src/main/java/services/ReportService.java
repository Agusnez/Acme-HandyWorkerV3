
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.Authority;
import domain.Actor;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository

	@Autowired
	private ReportRepository	reportRepository;

	// Suporting services

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods

	public Report create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		final Report result = new Report();

		final Collection<Note> notes = new HashSet<>();
		result.setNotes(notes);
		final Collection<String> attachments = new HashSet<>();
		result.setAttachments(attachments);

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

	public Report save(final Report report) {

		Assert.notNull(report);

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		report.setMoment(currentMoment);

		final Report result = this.reportRepository.save(report);

		return result;

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
