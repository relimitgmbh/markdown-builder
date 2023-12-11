package de.relimit.commons.markdown.converter;

import java.util.Map;

import de.relimit.commons.markdown.span.textual.Textual;

/**
 * A {@link Escaper} that can target specific {@link Textual}s. The way this
 * works is that classes are used as keys in a {@link Map} of {@link Escaper}s.
 * Once the {@link ConfigurableEscaper} is asked to escape a stringified text it
 * looks for the closest class entry in the map of known escapers and uses this
 * escaper to process the text. The class used for the lookup is whatever
 * {@link Textual}'s class is. In other words: The class of the markdown element
 * the text to escape resides in.
 */
public class ConfigurableEscaper extends ConfigurableConverter<Escaper, Textual> implements Escaper {

	@Override
	protected Escaper getDefault() {
		return Escaper.ESCAPE_MARKDOWN;
	}

	@Override
	protected Class<Textual> getBaseType() {
		return Textual.class;
	}

	@Override
	public String escape(Textual element, Object stringifyable, String stringified) {
		final Escaper escaper = forClass(element.getClass());
		return escaper.escape(element, stringifyable, stringified);
	}

}
