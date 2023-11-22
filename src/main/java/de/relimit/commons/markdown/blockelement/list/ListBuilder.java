package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;

public abstract class ListBuilder<B extends ListBuilder<B, P, E, B2, L>, P, E extends AbstractList<L>, B2 extends ListItemBuilder<B2, B, L>, L extends ListItem>
		extends NodeBuilder<B, P, E, L> {

	protected ListBuilder(E element, MarkdownElementAppender<P, E> parentAppender) {
		super(element, parentAppender);
	}

	protected ListBuilder(E element) {
		super(element);
	}

	abstract L createListItem(int indentationLevel);

	abstract B2 createListItemBuilder(L listItem, MarkdownElementAppender<B, L> appender);

	public B2 startItem() {
		final int indentationLevel = getElement().getIndentationLevel();
		final L listItem = createListItem(indentationLevel + 1);
		return createListItemBuilder(listItem, this::append);
	}

	@Override
	public B append(L element) {
		final int indentationLevel = getElement().getIndentationLevel();
		element.setIndentationLevel(indentationLevel + 1);
		return super.append(element);
	}

	public B item(Object stringifyable) {
		return startItem().paragraph(stringifyable).end();
	}

}
