package com.vivareal.locations.statistic.builders;

public abstract class BuilderRepository {

	public BuilderRepository() {
		Builders.addRepository(this);
	}

	abstract void save(AbstractBuilder<?> b);

	abstract void clean();
}