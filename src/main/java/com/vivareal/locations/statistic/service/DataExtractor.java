package com.vivareal.locations.statistic.service;

import static java.util.Collections.singletonMap;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DataExtractor {
	

	private DataSource dataSource;
	private ListConsumer<Integer> consumer;
	private ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
	private String sql;

	public DataExtractor(DataSource dataSource, ListConsumer<Integer> consumer, String sql) {
		super();
		this.dataSource = dataSource;
		this.consumer = consumer;
		this.sql = sql;
	}

	public List<Map<String, Object>> fetch() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		return template.query(sql, singletonMap("ids", consumer.consume()), rowMapper);
	}

	public boolean isEmpty() {
		return consumer.isEmpty();
	}

	public Integer getLeft() {
		return consumer.getLeft();
	}
}
