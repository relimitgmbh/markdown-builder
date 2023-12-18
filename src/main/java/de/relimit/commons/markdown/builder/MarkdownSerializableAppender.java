package de.relimit.commons.markdown.builder;

import de.relimit.commons.markdown.MarkdownSerializable;

/**
 * @param <A>
 *            The appender itself for method chaining
 * @param <AE>
 *            The element that is appended to this appender
 */
public interface MarkdownSerializableAppender<A, AE extends MarkdownSerializable> {

	A append(AE element);

}
