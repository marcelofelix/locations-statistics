package com.vivareal.locations.statistic.builders;

import java.util.ArrayList;
import java.util.Collection;

public class Builders {
	private static Collection<AbstractBuilder<?>> builders = new ArrayList<>();
	private static Collection<BuilderRepository> repositories = new ArrayList<>();

	public static void addRepository(BuilderRepository repository) {
		repositories.add(repository);
	}

	public static void addBuilder(AbstractBuilder<?> builder) {
		builders.add(builder);
	}

	public static void saveAll() {
		builders.forEach(b -> {
			repositories.stream()
					.forEach(r -> r.save(b));
		});
		builders.clear();
	}

	public static void clean() {
		repositories.forEach(b -> b.clean());
	}
}