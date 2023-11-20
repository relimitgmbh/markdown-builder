package de.relimit.commons.markdown.builder;

import de.relimit.commons.markdown.MarkdownSerializable;

public interface MarkdownElementAppender<P, E extends MarkdownSerializable> {

	P append(E element);

}
