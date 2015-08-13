package com.vivareal.locations.statistic.exception;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HandlerException implements HandlerExceptionResolver {

	private static final Logger log = LoggerFactory.getLogger(HandlerException.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (ex instanceof HttpException) {
			log.info("Error {}", ex.getMessage());
			HttpException e = (HttpException) ex;
			response.setStatus(e.getStatus().value());
			writeResult(response, singletonMap("message", ex.getMessage()));
		} else {
			response.setStatus(INTERNAL_SERVER_ERROR.value());
			writeResult(response, singletonMap("message", "Ocorreu um erro"));
			log.error("Error", ex);
		}
		return new ModelAndView();
	}

	private void writeResult(HttpServletResponse response, Object value) {
		try {
			response.setCharacterEncoding("UTF-8");
			mapper.writeValue(response.getOutputStream(), value);
		} catch (Exception e) {
			log.error("Fail to log", e);
		}
	}

}
