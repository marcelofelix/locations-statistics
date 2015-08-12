package com.vivareal.locations.statistic.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vivareal.locations.statistic.Cell;
import com.vivareal.locations.statistic.Table;

@JsonInclude(Include.NON_EMPTY)
public class Node implements Comparable<Node> {

	private String name;
	private Integer value;
	private Long percent;
	private List<Node> children = new ArrayList<>();

	public Node() {
		super();
	}

	public Node(Cell cell) {
		this.name = cell.getName();
		this.value = cell.getValue();
		this.percent = cell.getPercent();
		cell.getChildren().values().forEach(v -> {
			children.add(new Node(v));
		});
	}

	public Node(String name, Table table) {
		this.name = name;
		this.children = table.getCells().values().stream()
				.map(c -> new Node(c))
				.collect(toList());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Long getPercent() {
		return percent;
	}

	public void setPercent(Long percent) {
		this.percent = percent;
	}

	public List<Node> getChildren() {
		Collections.sort(children);
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	@Override
	public int compareTo(Node o) {
		if (value != null && o.value != null) {
			return o.value.compareTo(value);
		}
		return 0;
	}

}
