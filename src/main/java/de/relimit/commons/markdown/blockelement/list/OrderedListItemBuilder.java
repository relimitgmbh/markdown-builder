package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;

public class OrderedListItemBuilder<P> extends ListItemBuilder<OrderedListItemBuilder<P>, P, OrderedListItem> {

	public OrderedListItemBuilder(OrderedListItem listItem,
			MarkdownElementAppender<P, OrderedListItem> parentAppender) {
		super(listItem, parentAppender);
	}

	public OrderedListItemBuilder(OrderedListItem listItem) {
		super(listItem);
	}

	@Override
	protected OrderedListItemBuilder<P> getBuilder() {
		return this;
	}

}
