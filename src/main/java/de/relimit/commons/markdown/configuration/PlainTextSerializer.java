package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;

@FunctionalInterface
public interface PlainTextSerializer<T> {

	String serialize(MarkdownElement element, T stringyfiable) throws MarkdownSerializationException;

}
