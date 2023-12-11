package de.relimit.commons.markdown.converter;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.span.textual.Textual;

@FunctionalInterface
public interface Stringifier<T> {

	public static final Stringifier<Object> DEFAULT_SERIALIZER = (e, o) -> o.toString();

	/**
	 * This method is supposed to serialize the Object handed over via
	 * stringifyable. The default implementation uses {@link #toString()}. The
	 * {@link ConfigurableStringifier} supports targeting specific classes.
	 * 
	 * @see ConfigurableStringifier
	 * 
	 * @param element
	 * @param stringifyable
	 * @return
	 * @throws MarkdownSerializationException
	 */
	String stringify(Textual element, T stringifyable) throws MarkdownSerializationException;

}
