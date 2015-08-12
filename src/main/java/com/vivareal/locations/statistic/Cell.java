package com.vivareal.locations.statistic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Cell implements Comparable<Cell> {
	private String name;
	private Cell parent;
	private AtomicInteger value = new AtomicInteger();
	private Map<String, Cell> children = new HashMap<>();

	public String getName() {
		return name;
	}

	public Cell(String name) {
		this.name = name;
	}

	public Cell(String name, Cell parent) {
		this.name = name;
		this.parent = parent;
	}

	public Map<String, Cell> getChildren() {
		return children;
	}

	@Override
	public int compareTo(Cell o) {
		return Integer.compare(o.value.get(), getValue());
	}

	public int getValue() {
		return value.get();
	}

	public void inc() {
		value.incrementAndGet();
	}

	public long getPercent() {
		if (parent != null) {
			return Math.round(value.doubleValue() / parent.value.doubleValue() * 100);
		} else {
			return 100;
		}
	}

	public Cell get(String name) {
		Cell cell = children.get(name);
		if (cell == null) {
			cell = new Cell(name, this);
			children.put(name, cell);
		}
		return cell;
	}
}