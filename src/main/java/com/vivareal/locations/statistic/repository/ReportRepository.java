package com.vivareal.locations.statistic.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vivareal.locations.statistic.model.Report;
import com.vivareal.locations.statistic.model.Status;

public interface ReportRepository extends MongoRepository<Report, String> {

	Collection<Report> findByStatus(Status queued);

	Collection<Report> findByStatusOrderByIdAsc(Status status);

	int countByStatus(Status processing);

	Report findFirstByStatusOrderByDateAsc(Status status);

}
