package com.vivareal.locations.statistic.service;

import static com.vivareal.locations.statistic.builders.ReportBuilder.report;
import static com.vivareal.locations.statistic.model.Status.QUEUED;
import static com.vivareal.locations.statistic.model.Status.READY;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vivareal.locations.statistic.builders.ReportBuilder;
import com.vivareal.locations.statistic.controller.AbstractControllerTest;
import com.vivareal.locations.statistic.model.Report;
import com.vivareal.locations.statistic.repository.ReportRepository;

public class ReportServiceTest extends AbstractControllerTest {

	@Autowired
	private ReportService service;

	@Autowired
	private Storage storage;

	@Autowired
	private ReportRepository repository;

	@Test
	public void testExtractData() throws Exception {
		ReportBuilder report = report();
		saveAll();
		service.extractData(report.get());
	}

	@Test
	public void testProcessReport() throws Exception {
		Report report = report().status(QUEUED).get();
		saveAll();
		storage.copy(getFile("report.csv"), report.getExtractedDataFileName());
		assertThat(repository.findOne(report.getId()).getStatus(), equalTo(QUEUED));
		service.processReports(report);

		assertThat(repository.findOne(report.getId()).getStatus(), equalTo(READY));
	}

	@Test
	public void testProcessQueuedReport() throws Exception {
		ReportBuilder report = report().status(QUEUED);
		report().status(QUEUED);
		saveAll();
		storage.copy(getFile("report.csv"), report.get().getExtractedDataFileName());

		service.processQueuedReport();
		assertThat(repository.findByStatus(QUEUED), hasSize(1));
		assertThat(repository.findByStatus(READY), hasSize(1));
	}
}
