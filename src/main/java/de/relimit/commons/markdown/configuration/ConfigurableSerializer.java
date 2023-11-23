package de.relimit.commons.markdown.configuration;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.span.textual.Textual;

/**
 * A {@link TextSerializer} that can target specific classes. The way this works
 * is that classes are used as keys in a {@link Map} of {@link TextSerializer}s.
 * Once the {@link ConfigurableSerializer} is asked to serialize an object it
 * looks for the closest class entry in the map of known serializers and uses
 * this serializer to produce output.
 * <p>
 * The default serializer performs a trivial {@link #toString()} and is
 * registered for {@link Object}. This means it is a "catch all" serializer as
 * long as no other more specific and matching serializer is present. Any number
 * of serializers can be registered but only one for each class.
 */
public class ConfigurableSerializer implements TextSerializer<Object> {

	private Map<Class<?>, TextSerializer<?>> serializers = new HashMap<>();

	private Map<Class<?>, TextSerializer<?>> cache = new HashMap<>();

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

	private TextSerializer<?> forClass(Class<?> clazz) {
		// For speed
		if (serializers.isEmpty()) {
			return TextSerializer.DEFAULT_SERIALIZER;
		}
		TextSerializer<?> serializer = cache.get(clazz);
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
		serializer = TextSerializer.DEFAULT_SERIALIZER;
		cache.put(clazz, serializer);
		return serializer;
	}

	@Override
	public String serialize(Textual element, Object stringifyable) throws MarkdownSerializationException {
		final TextSerializer<Object> serializer = (TextSerializer<Object>) forClass(stringifyable.getClass());
		return serializer.serialize(element, stringifyable);
	}

	private void invalidateCache() {
		cache = new HashMap<>();
	}

	public <E extends T, T> void registerSerializer(Class<T> clazz, TextSerializer<E> serializer) {
		serializers.put(clazz, serializer);
		invalidateCache();
	}

	/**
	 * Registers a serializer for {@link Object}. This is a fallback serializer
	 * in case no class specific ones are registered.
	 * 
	 * @param defaultSerializer
	 */
	public void registerDefaultSerializer(TextSerializer<?> defaultSerializer) {
		registerSerializer(Object.class, defaultSerializer);
	}

}
