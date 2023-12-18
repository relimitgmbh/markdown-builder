package de.relimit.commons.markdown.converter;

import java.util.Map;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.span.textual.Textual;

/**
 * A {@link Stringifier} that can target specific classes. The way this works
 * is that classes are used as keys in a {@link Map} of {@link Stringifier}s.
 * Once the {@link ConfigurableStringifier} is asked to serialize an object it
 * looks for the closest class entry in the map of known serializers and uses
 * this serializer to produce output.
 * <p>
 * The default serializer performs a trivial {@link #toString()} and is
 * registered for {@link Object}. This means it is a "catch all" serializer as
 * long as no other more specific and matching serializer is present. Any number
 * of serializers can be registered but only one for each class.
 */
public class ConfigurableStringifier extends ConfigurableConverter<Stringifier<?>, Object>
		implements Stringifier<Object> {

	@Override
	protected Stringifier<?> getDefault() {
		return Stringifier.DEFAULT_SERIALIZER;
	}

	@Override
	protected Class<Object> getBaseType() {
		return Object.class;
	}

	@Override
	public String stringify(Textual element, Object stringifyable) throws MarkdownSerializationException {
		final Stringifier<Object> serializer = (Stringifier<Object>) forClass(stringifyable.getClass());
		return serializer.stringify(element, stringifyable);
	}

}
