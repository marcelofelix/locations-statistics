package com.vivareal.locations.statistic.controller;

import java.util.Date;

import com.vivareal.locations.statistic.model.Report;
import com.vivareal.locations.statistic.model.Status;

public class ReportDTO implements Comparable<ReportDTO> {

	private final String id;
	private final Date date;
	private final Status status;

	public ReportDTO(Report report) {
		this.id = report.getId();
		this.date = report.getDate();
		this.status = report.getStatus();
	}

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public int compareTo(ReportDTO o) {
		return o.date.compareTo(date);
	}

}
