package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.MarkdownElementAppender;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.builder.NodeBuilder;

public abstract class ListBuilder<P, E extends AbstractList<L>, L extends ListItem>
		extends NodeBuilder<ListBuilder<P, E, L>, P, E, L> {

	protected ListBuilder(E element, MarkdownElementAppender<P, E> parentAppender) {
		super(element, parentAppender);
	}

	protected ListBuilder(E element) {
		super(element);
	}

	abstract L createListItem(int indentationLevel, Paragraph listItemParagraph);

	public ListItemBuilder<ListBuilder<P, E, L>, L> startItem(Paragraph paragraph) {
		final int indentationLevel = getElement().getIndentationLevel();
		final L listItem = createListItem(indentationLevel + 1, paragraph);
		return new ListItemBuilder<>(listItem, this::append);
	}

	@Override
	public ListBuilder<P, E, L> append(L element) {
		final int indentationLevel = getElement().getIndentationLevel();
		element.setIndentationLevel(indentationLevel + 1);
		return super.append(element);
	}

	@Override
	protected ListBuilder<P, E, L> getBuilder() {
		return this;
	}

}
