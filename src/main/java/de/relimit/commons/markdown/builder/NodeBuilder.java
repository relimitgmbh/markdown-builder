package de.relimit.commons.markdown.builder;

import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.Node;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 * @param <B>
 *            The builder itself for method chaining
 * @param <BE>
 *            The element that is built by this builder (a node)
 * @param <NE>
 *            The child element that is added to the node
 */
public abstract class NodeBuilder<P, B extends NodeBuilder<P, B, BE, NE>, BE extends Node<NE>, NE extends MarkdownSerializable>
		extends NestableBuilder<P, B, BE> implements MarkdownElementAppender<B, NE> {

	protected NodeBuilder(BE element, MarkdownElementAppender<P, BE> parent) {
		super(element, parent);
	}

	protected NodeBuilder(BE element) {
		super(element);
	}

	@Override
	public B append(NE element) {
		getElement().append(element);
		return getBuilder();
	}

}
