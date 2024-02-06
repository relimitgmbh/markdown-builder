package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 */
public class UnorderedListItemBuilder<P> extends ListItemBuilder<P, UnorderedListItemBuilder<P>, UnorderedListItem> {

	public UnorderedListItemBuilder(UnorderedListItem listItem,
			MarkdownSerializableAppender<P, UnorderedListItem> parentAppender) {
		super(listItem, parentAppender);
	}

	public UnorderedListItemBuilder() {
		super(new UnorderedListItem());
	}

	@Override
	protected UnorderedListItemBuilder<P> getBuilder() {
		return this;
	}

}
