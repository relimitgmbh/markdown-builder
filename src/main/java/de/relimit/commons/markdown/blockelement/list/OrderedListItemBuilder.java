package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 */
public class OrderedListItemBuilder<P> extends ListItemBuilder<P, OrderedListItemBuilder<P>, OrderedListItem> {

	public OrderedListItemBuilder(OrderedListItem listItem,
			MarkdownSerializableAppender<P, OrderedListItem> parentAppender) {
		super(listItem, parentAppender);
	}

	public OrderedListItemBuilder() {
		super(new OrderedListItem());
	}

	@Override
	protected OrderedListItemBuilder<P> getBuilder() {
		return this;
	}

}
