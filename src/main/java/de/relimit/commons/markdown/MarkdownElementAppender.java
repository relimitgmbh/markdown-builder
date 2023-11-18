package de.relimit.commons.markdown;

public interface MarkdownElementAppender<P, E extends MarkdownSerializable> {

	P append(E element);

}
