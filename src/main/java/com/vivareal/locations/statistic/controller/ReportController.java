package com.vivareal.locations.statistic.controller;

import static com.vivareal.locations.statistic.model.Status.QUEUED;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.locations.statistic.exception.ReportNotFound;
import com.vivareal.locations.statistic.exception.ReportResultNotReady;
import com.vivareal.locations.statistic.model.Node;
import com.vivareal.locations.statistic.model.Report;
import com.vivareal.locations.statistic.repository.ReportRepository;
import com.vivareal.locations.statistic.service.Storage;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Storage storage;

	@RequestMapping
	public Collection<ReportDTO> list() {
		return reportRepository.findAll().stream()
				.map(r -> new ReportDTO(r))
				.sorted()
				.collect(toList());
	}

	@RequestMapping("/{id}/result")
	public Collection<Node> get(@PathVariable("id") String id) throws Exception {
		Report report = reportRepository.findOne(id);
		if (report == null) {
			throw new ReportNotFound();
		} else if (!report.isReady()) {
			throw new ReportResultNotReady();
		}

		return report.getResult();
	}

	@RequestMapping(value = "/schedule", method = POST)
	@ResponseStatus(value = CREATED)
	@Transactional
	public ReportDTO schedule() {
		Report report = new Report();
		report.setStatus(QUEUED);
		reportRepository.save(report);
		return new ReportDTO(report);
	}

}
