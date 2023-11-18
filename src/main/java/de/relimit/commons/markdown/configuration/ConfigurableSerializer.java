package de.relimit.commons.markdown.configuration;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;

public class ConfigurableSerializer implements PlainTextSerializer<Object> {

	private PlainTextSerializer<?> defaultSerializer = MarkdownSerializationOptions.DEFAULT_SERIALIZER;

	private Map<Class<?>, PlainTextSerializer<?>> serializers = new HashMap<>();

	private Map<Class<?>, PlainTextSerializer<?>> cache = new HashMap<>();

	public ConfigurableSerializer() {
	}

	/**
	 * "Inspired" by https://stackoverflow.com/a/9797689
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

	private PlainTextSerializer<?> forClass(Class<?> clazz) {
		// For speed
		if (serializers.isEmpty()) {
			return MarkdownSerializationOptions.DEFAULT_SERIALIZER;
		}
		PlainTextSerializer<?> serializer = cache.get(clazz);
		if (serializer != null) {
			// Cache hit
			return serializer;
		}
		final Set<Class<?>> classes = getNearestClasses(clazz);
		for (final Class<?> relatedClazz : classes) {
			serializer = serializers.entrySet().stream().filter(e -> e.getKey().equals(relatedClazz)).findFirst()
					.map(Entry::getValue).orElse(null);
			if (serializer != null) {
				/*
				 * We've matched the class to serialize to a serializer. Put in
				 * cache.
				 */
				cache.put(clazz, serializer);
				return serializer;
			}
		}
		// Put down default serializer
		serializer = MarkdownSerializationOptions.DEFAULT_SERIALIZER;
		cache.put(clazz, serializer);
		return serializer;
	}

	@Override
	public String serialize(MarkdownElement element, Object stringyfiable) throws MarkdownSerializationException {
		final PlainTextSerializer<Object> serializer = (PlainTextSerializer<Object>) forClass(stringyfiable.getClass());
		return serializer.serialize(element, stringyfiable);
	}

	private void invalidateCache() {
		cache = new HashMap<>();
	}

	public <E extends T, T> void registerSerializer(Class<T> clazz, PlainTextSerializer<E> serializer) {
		serializers.put(clazz, serializer);
		invalidateCache();
	}

	public void registerDefaultSerializer(PlainTextSerializer<?> defaultSerializer) {
		this.defaultSerializer = defaultSerializer;
		invalidateCache();
	}

}
