package com.vivareal.locations.statistic.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1719382124636961396L;

	private final HttpStatus status;

	public HttpException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
