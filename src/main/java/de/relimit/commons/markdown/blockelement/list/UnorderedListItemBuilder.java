package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;

public class UnorderedListItemBuilder<P> extends ListItemBuilder<UnorderedListItemBuilder<P>, P, UnorderedListItem> {

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
