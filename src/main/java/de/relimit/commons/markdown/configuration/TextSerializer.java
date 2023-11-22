package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.span.textual.Textual;

@FunctionalInterface
public interface TextSerializer<T> {

	public static final TextSerializer<Object> DEFAULT_SERIALIZER = (e, o) -> o.toString();

	/**
	 * This method is supposed to serialize the Object handed over via
	 * stringifyable. The default implementation uses {@link #toString()}. The
	 * {@link ConfigurableSerializer} supports targeting specific classes.
	 * 
	 * @see ConfigurableSerializer
	 * 
	 * @param element
	 * @param stringifyable
	 * @return
	 * @throws MarkdownSerializationException
	 */
	String serialize(Textual element, T stringifyable) throws MarkdownSerializationException;

}
