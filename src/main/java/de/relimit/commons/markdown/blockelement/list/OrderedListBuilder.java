package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;

public class OrderedListBuilder<P> extends
		ListBuilder<P, OrderedListBuilder<P>, OrderedList, OrderedListItemBuilder<OrderedListBuilder<P>>, OrderedListItem> {

	public OrderedListBuilder(OrderedList element, MarkdownSerializableAppender<P, OrderedList> parentAppender) {
		super(element, parentAppender);
	}

	public OrderedListBuilder() {
		super(new OrderedList(0));
	}

	@Override
	OrderedListItem createListItem(int indentationLevel) {
		return new OrderedListItem(indentationLevel);
	}

	@Override
	OrderedListItemBuilder<OrderedListBuilder<P>> createListItemBuilder(OrderedListItem listItem,
			MarkdownSerializableAppender<OrderedListBuilder<P>, OrderedListItem> appender) {
		return new OrderedListItemBuilder<>(listItem, appender);
	}

	@Override
	protected OrderedListBuilder<P> getBuilder() {
		return this;
	}

}
