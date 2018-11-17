
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		return null;

	}

	public Collection<Report> findAll() {

		return null;

	}

	public Report findOne(final int reportID) {

		return null;

	}

	public Report save(final Report report) {

		return null;

	}

	public void delete(final Report report) {

	}

	// Other business methods

}
