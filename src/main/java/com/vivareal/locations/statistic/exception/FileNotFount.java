package com.vivareal.locations.statistic.exception;

import org.springframework.http.HttpStatus;

public class FileNotFount extends HttpException {

	public FileNotFount() {
		super(HttpStatus.NOT_FOUND, "Arquivo não encontrado");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1133152173482627979L;

}
