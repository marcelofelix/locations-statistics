package com.vivareal.locations.statistic.controller;

import static com.vivareal.locations.statistic.builders.ReportBuilder.report;
import static com.vivareal.locations.statistic.model.Status.QUEUED;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vivareal.locations.statistic.builders.ReportBuilder;
import com.vivareal.locations.statistic.repository.ReportRepository;
import com.vivareal.locations.statistic.service.Storage;

public class ReportControllerTest extends AbstractControllerTest {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private Storage storage;

	@Test
	public void testListReports() throws Exception {
		report("01/01/15");
		report("02/01/15");

		saveAll();
		perform(get("/report"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].date", startsWith("2015-01-02")))
				.andExpect(jsonPath("$[1].date", startsWith("2015-01-01")));
	}

	@Test
	public void testScheduleReport() throws Exception {
		perform(post("/report/schedule"))
				.andExpect(status().isCreated());
		assertThat(reportRepository.findByStatus(QUEUED), hasSize(1));
	}

	@Test
	public void testGetReportResult() throws Exception {
		ReportBuilder report = report();
		saveAll();
		perform(get("/report/{id}/result", report.get().getId()))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
