package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 */
public class OrderedListItemBuilder<P> extends ListItemBuilder<P, OrderedListItemBuilder<P>, OrderedListItem> {

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
