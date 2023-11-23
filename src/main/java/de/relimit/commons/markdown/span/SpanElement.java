package de.relimit.commons.markdown.span;

import de.relimit.commons.markdown.MarkdownSerializable;

/**
 * Since markdown is essentially a preliminary stage of HTML the general
 * paradigm is the same. There are block elements like paragraphs and span
 * elements. Markdown elements implementing this interface represent
 * {@link SpanElement}s.
 */
public interface SpanElement extends MarkdownSerializable {

}
