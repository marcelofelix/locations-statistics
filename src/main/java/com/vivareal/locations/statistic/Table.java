package com.vivareal.locations.statistic;

import java.util.HashMap;
import java.util.Map;

public class Table {
	private Map<String, Cell> cells = new HashMap<>();

	public Cell get(String name) {
		Cell cell = cells.get(name);
		if (cell == null) {
			cell = new Cell(name);
			cells.put(name, cell);
		}
		return cell;
	}

	public Map<String, Cell> getCells() {
		return cells;
	}

}
