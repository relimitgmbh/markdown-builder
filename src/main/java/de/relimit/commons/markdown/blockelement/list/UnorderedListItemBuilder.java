package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 */
public class UnorderedListItemBuilder<P> extends ListItemBuilder<P, UnorderedListItemBuilder<P>, UnorderedListItem> {

	public UnorderedListItemBuilder(UnorderedListItem listItem,
			MarkdownElementAppender<P, UnorderedListItem> parentAppender) {
		super(listItem, parentAppender);
	}

	public UnorderedListItemBuilder(UnorderedListItem listItem) {
		super(listItem);
	}

	@Override
	protected UnorderedListItemBuilder<P> getBuilder() {
		return this;
	}

}
