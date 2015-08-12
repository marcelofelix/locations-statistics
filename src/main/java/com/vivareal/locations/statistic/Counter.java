package com.vivareal.locations.statistic;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class Counter {

	private Table table;
	private String name;
	private Predicate<Inmueble> predicate;
	private Function<Inmueble, String> function;
	private Collection<Counter> children = new ArrayList<>();

	public Counter(Table table) {
		this.table = table;
	}

	public Counter(String name, Predicate<Inmueble> predicate) {
		super();
		this.name = name;
		this.predicate = predicate;
	}

	public Counter(Function<Inmueble, String> function) {
		super();
		this.function = function;
	}

	public void eval(Inmueble inmueble) {
		if (predicate != null) {
			if (predicate.test(inmueble)) {
				Cell cell = table.get(name);
				cell.inc();
				children.forEach(c -> c.eval(inmueble, cell));
			}
		} else if (function != null) {
			Cell cell = table.get(ofNullable(function.apply(inmueble)).orElse("null"));
			cell.inc();
			children.forEach(c -> c.eval(inmueble, cell));
		} else {
			children.forEach(c -> c.eval(inmueble));
		}
	}

	private void eval(Inmueble inmueble, Cell cell) {
		if (predicate != null) {
			if (predicate.test(inmueble)) {
				Cell child = cell.get(name);
				child.inc();
				children.forEach(c -> c.eval(inmueble, child));
			}
		} else if (function != null) {
			Cell child = cell.get(ofNullable(function.apply(inmueble)).orElse("null"));
			child.inc();
			children.forEach(c -> c.eval(inmueble, child));
		} else {
			children.forEach(c -> c.eval(inmueble));
		}

	}

	public Counter add(Counter child) {
		children.add(child);
		child.table = table;
		return this;
	}

}
