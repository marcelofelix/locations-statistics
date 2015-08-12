package com.vivareal.locations.statistic.exception;

import org.springframework.http.HttpStatus;

public class ReportResultNotReady extends HttpException {

	public ReportResultNotReady() {
		super(HttpStatus.NOT_FOUND, "report.not.ready");
	}

	private static final long serialVersionUID = 2635737162614542355L;

}
