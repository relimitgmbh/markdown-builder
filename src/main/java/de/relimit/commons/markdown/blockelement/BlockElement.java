package de.relimit.commons.markdown.blockelement;

import de.relimit.commons.markdown.MarkdownSerializable;

/**
 * Since markdown is essentially a preliminary stage of HTML the general
 * paradigm is the same. There are block elements like paragraphs and span
 * elements. Markdown elements implementing this interface represent
 * {@link BlockElement}s.
 */
public interface BlockElement extends MarkdownSerializable {

}
