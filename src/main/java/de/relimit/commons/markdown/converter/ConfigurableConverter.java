package de.relimit.commons.markdown.converter;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class ConfigurableConverter<C, T> {

	private Map<Class<? extends T>, C> converters = new HashMap<>();

	private Map<Class<? extends T>, C> cache = new HashMap<>();

	protected abstract C getDefault();

	protected abstract Class<T> getBaseType();

	/**
	 * See <a href="https://stackoverflow.com/a/9797689">Stack Overflow</a>
	 * 
	 * @param clazz
	 * @return
	 */
	private static Set<Class<?>> getNearestClasses(Class<?> clazz) {
		final Set<Class<?>> classes = new LinkedHashSet<>();
		final Set<Class<?>> nextLevel = new LinkedHashSet<>();
		nextLevel.add(clazz);
		do {
			classes.addAll(nextLevel);
			final Set<Class<?>> thisLevel = new LinkedHashSet<>(nextLevel);
			nextLevel.clear();
			for (final Class<?> each : thisLevel) {
				final Class<?> superClass = each.getSuperclass();
				if (superClass != null && superClass != Object.class) {
					nextLevel.add(superClass);
				}
				for (final Class<?> eachInt : each.getInterfaces()) {
					nextLevel.add(eachInt);
				}
			}
		} while (!nextLevel.isEmpty());
		return classes;
	}

	protected C forClass(Class<? extends T> clazz) {
		// For speed
		if (converters.isEmpty()) {
			return getDefault();
		}
		C converter = cache.get(clazz);
		if (converter != null) {
			// Cache hit
			return converter;
		}
		final Set<Class<?>> classes = getNearestClasses(clazz);
		for (final Class<?> relatedClazz : classes) {
			converter = converters.entrySet().stream().filter(e -> e.getKey().equals(relatedClazz)).findFirst()
					.map(Entry::getValue).orElse(null);
			if (converter != null) {
				/*
				 * We've matched the class to serialize to a serializer. Put in
				 * cache.
				 */
				cache.put(clazz, converter);
				return converter;
			}
		}
		// Put down default serializer
		converter = getDefault();
		cache.put(clazz, converter);
		return converter;
	}

	private void invalidateCache() {
		cache = new HashMap<>();
	}

	public void register(Class<? extends T> clazz, C converter) {
		converters.put(clazz, converter);
		invalidateCache();
	}

	/**
	 * Registers a converter for {@link Object}. This is a fallback converter in
	 * case no class specific ones are registered.
	 * 
	 * @param defaultConverter
	 */
	public void registerDefault(C defaultConverter) {
		register(getBaseType(), defaultConverter);
	}

}
