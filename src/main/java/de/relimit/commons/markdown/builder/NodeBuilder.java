package de.relimit.commons.markdown.builder;

import de.relimit.commons.markdown.MarkdownElementAppender;
import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.Node;

public abstract class NodeBuilder<B extends NodeBuilder<B, P, E, E2>, P, E extends Node<E2>, E2 extends MarkdownSerializable>
		extends NestableBuilder<B, P, E> implements MarkdownElementAppender<B, E2> {

	protected NodeBuilder(E element, MarkdownElementAppender<P, E> parent) {
		super(element, parent);
	}

	protected NodeBuilder(E element) {
		super(element);
	}

	@Override
	public B append(E2 element) {
		getElement().append(element);
		return getBuilder();
	}

}
