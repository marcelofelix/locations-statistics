package com.vivareal.locations.statistic;

import static java.util.stream.Collectors.toList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.vivareal.locations.statistic.model.Node;

public class Reports {
	private Map<String, Table> tables = new LinkedHashMap<>();
	private Map<String, Consumer<Counter>> process = new LinkedHashMap<>();

	public Reports process(String name, Consumer<Counter> consumer) {
		process.put(name, consumer);
		return this;
	}

	public void run(Inmuebles inmuebles) {
		process.forEach((n, p) -> {
			Table table = new Table();
			Counter counter = new Counter(table);
			Counter total = new Counter("Total de imoveis", i -> true);
			counter.add(total);
			p.accept(total);
			tables.put(n, table);
			while (inmuebles.hasNext()) {
				counter.eval(inmuebles.next());
			}
			inmuebles.start();
		});
	}

	public List<Node> getResult() {
		return tables.entrySet().stream()
				.map(e -> new Node(e.getKey(), e.getValue()))
				.collect(toList());
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		tables.forEach((n, v) -> {
			result.append(n + "\n");
			result.append(v.toString());
			result.append("\n\n");
		});
		return result.toString();
	}

}
