package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.builder.MarkdownElementAppender;

public class OrderedListBuilder<P> extends ListBuilder<P, OrderedList, OrderedListItem> {

	public OrderedListBuilder(OrderedList element, MarkdownElementAppender<P, OrderedList> parentAppender) {
		super(element, parentAppender);
	}

	public OrderedListBuilder(OrderedList element) {
		super(element);
	}

	@Override
	OrderedListItem createListItem(int indentationLevel, Paragraph listItemParagraph) {
		return new OrderedListItem(indentationLevel, listItemParagraph);
	}

}
