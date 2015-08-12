package com.vivareal.locations.statistic.service;

import static com.vivareal.locations.statistic.model.Status.PROCESSING;
import static com.vivareal.locations.statistic.model.Status.QUEUED;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.locations.ReportsFactory;
import com.vivareal.locations.statistic.Inmuebles;
import com.vivareal.locations.statistic.Reports;
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

	public void extractData(Report report) throws Exception {
		updateStatus(report, PROCESSING);
		try {

			Path path = storage.getPath(report.getExtractedDataFileName());

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
			processReports(report);
		} catch (Exception e) {
			log.error("Error processing report", e);
			updateStatus(report, QUEUED);
		}
	}

	public void processReports(Report report) {
		try {

			Inmuebles inmuebles = new Inmuebles(storage.getPath(report.getExtractedDataFileName()));
			Reports reports = factory.create();
			reports.run(inmuebles);
			report.setResult(reports.getResult());
			updateStatus(report, Status.READY);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Scheduled(initialDelay = 10000, fixedDelay = 10000)
	public void processQueuedReport() throws Exception {
		if (reportRepository.countByStatus(PROCESSING) == 0) {
			log.info("Cheking for report queued");
			Iterator<Report> reports = reportRepository.findByStatusOrderByIdAsc(QUEUED).iterator();
			if (reports.hasNext()) {
				extractData(reports.next());
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
