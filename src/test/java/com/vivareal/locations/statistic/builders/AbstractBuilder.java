package com.vivareal.locations.statistic.builders;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unchecked")
public class AbstractBuilder<T> {
	protected T target;

	@SuppressWarnings("rawtypes")
	public AbstractBuilder() {
		try {
			target = (T) ((Class) ((ParameterizedType) this.getClass().
					getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Builders.addBuilder(this);
	}

	public T get() {
		return target;
	}

	public <B> B set(String name, Object value) {
		Field field;
		try {
			field = get().getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(get(), value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return (B) this;
	}

	public boolean isBuilderOf(Class<?> clazz) {
		return target.getClass().equals(clazz);
	}
}
