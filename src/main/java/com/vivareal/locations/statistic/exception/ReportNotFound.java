package com.vivareal.locations.statistic.exception;

import org.springframework.http.HttpStatus;

public class ReportNotFound extends HttpException {

	private static final long serialVersionUID = 6642026604379864154L;

	public ReportNotFound() {
		super(HttpStatus.NOT_FOUND, "report.not.found");
	}

}
