package com.vivareal.locations.statistic.service;

import static com.vivareal.locations.statistic.model.Status.PROCESSING;
import static com.vivareal.locations.statistic.model.Status.QUEUED;
import static com.vivareal.locations.statistic.model.Status.READY;
import static com.vivareal.locations.statistic.model.Status.REPROCESS;
import static java.util.Arrays.asList;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.locations.ReportsFactory;
import com.vivareal.locations.statistic.Inmuebles;
import com.vivareal.locations.statistic.Reports;
import com.vivareal.locations.statistic.exception.FileNotFount;
import com.vivareal.locations.statistic.model.Report;
import com.vivareal.locations.statistic.model.Status;
import com.vivareal.locations.statistic.repository.ReportRepository;

@Service
public class ReportService {

	private static final Logger log = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private Storage storage;

	@Autowired
	private DataExtractorFactory extractorFactory;

	@Autowired
	private ReportsFactory factory;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private MongoTemplate template;

	@PostConstruct
	public void init() {
		Query query = new Query(where("status").is(PROCESSING));
		Update update = new Update().set("status", QUEUED);
		template.updateMulti(query, update, Report.class);
	}

	public void extractData(Report report) throws Exception {
		updateStatus(report, PROCESSING);
		try {

			Path path = storage.create(report);

			Files.deleteIfExists(path);
			Files.createFile(path);
			BufferedWriter writer = Files.newBufferedWriter(path);
			boolean hasHeader = false;
			List<Map<String, Object>> result = new ArrayList<>();
			DataExtractor extractor = extractorFactory.create();

			while (!extractor.isEmpty()) {

				result = extractor.fetch();
				if (!result.isEmpty()) {
					if (!hasHeader) {
						String header = result.stream().findFirst()
								.get().keySet().stream()
								.reduce("", (a, i) -> concat(a, i));
						writer.write(header);
						writer.newLine();
						hasHeader = true;
					}
					result.forEach(r -> {

						try {
							String line = r.values().stream()
									.map(v -> v == null ? "" : v.toString())
									.reduce("", (a, i) -> concat(a, i));
							writer.write(line.replaceAll("\n", ""));
							writer.newLine();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					});
					writer.flush();
					log.info("Lefting {}", extractor.getLeft());
				}
			}
			storage.upload(report);
			processReports(report);
		} catch (Exception e) {
			log.error("Error processing report", e);
			updateStatus(report, QUEUED);
		}
	}

	public void processReports(Report report) {
		try {
			updateStatus(report, PROCESSING);
			Inmuebles inmuebles = new Inmuebles(storage.getPath(report.getFileName()));
			Reports reports = factory.create();
			reports.run(inmuebles);
			report.setResult(reports.getResult());
			Report last = reportRepository.findFirstByStatusOrderByDateAsc(READY);
			if (last != null) {
				report.compareWith(last);
			}
			updateStatus(report, Status.READY);
			storage.delete(report);
		} catch (FileNotFount e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Scheduled(initialDelay = 10000, fixedDelay = 10000)
	public void processQueuedReport() throws Exception {
		Report report = null;
		try {
			if (reportRepository.countByStatus(PROCESSING) == 0) {
				log.info("Cheking for report queued");
				Iterator<Report> reports = reportRepository.findByStatusInOrderByIdAsc(asList(QUEUED, REPROCESS)).iterator();
				if (reports.hasNext()) {
					report = reports.next();
					if (QUEUED.equals(report.getStatus())) {
						extractData(report);
					} else {
						processReports(report);
					}
				}
			}
		} catch (Exception e) {
			log.error("Erro", e);
			if (report != null) {
				updateStatus(report, QUEUED);
			}
		}
	}

	private String concat(String first, String second) {
		if (first.isEmpty()) {
			return second.replaceAll(",", "");
		} else {
			return first + "," + second.replaceAll(",", "");
		}
	}

	public void updateStatus(Report report, Status status) {
		report.setStatus(status);
		reportRepository.save(report);
	}

}
