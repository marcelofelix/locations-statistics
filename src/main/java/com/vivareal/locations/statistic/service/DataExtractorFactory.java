package com.vivareal.locations.statistic.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataExtractorFactory {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("sqlInmuebles")
	private String sqlInmuebles;

	@Autowired
	@Qualifier("sqlCuentas")
	private String sqlCuentas;

	public ListConsumer<Integer> getCuentas() {
		
		JdbcTemplate template = new JdbcTemplate(dataSource);
		ListConsumer<Integer> consumer = new ListConsumer<>(template.queryForList(sqlCuentas, Integer.class));
		consumer.setSize(100);
		return consumer;
	}

	public DataExtractor create() throws Exception {
		return new DataExtractor(dataSource, getCuentas(), sqlInmuebles);
	}

}
