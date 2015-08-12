package com.vivareal.locations.statistic.builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vivareal.locations.statistic.model.Report;
import com.vivareal.locations.statistic.repository.ReportRepository;

@Component
public class MongoBuilderRepository extends BuilderRepository {

	@Autowired
	private ReportRepository reportRepository;

	@Override	
	void save(AbstractBuilder<?> b) {
		if (b.isBuilderOf(Report.class)) {
			reportRepository.save((Report) b.get());
		}

	}

	@Override
	void clean() {
		reportRepository.deleteAll();
	}

}
