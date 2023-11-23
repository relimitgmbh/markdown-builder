package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;

public class UnorderedListBuilder<P> extends
		ListBuilder<P, UnorderedListBuilder<P>, UnorderedList, UnorderedListItemBuilder<UnorderedListBuilder<P>>, UnorderedListItem> {

	public UnorderedListBuilder(UnorderedList element, MarkdownElementAppender<P, UnorderedList> parentAppender) {
		super(element, parentAppender);
	}

	public UnorderedListBuilder(UnorderedList element) {
		super(element);
	}

	@Override
	UnorderedListItem createListItem(int indentationLevel) {
		return new UnorderedListItem(indentationLevel);
	}

	@Override
	UnorderedListItemBuilder<UnorderedListBuilder<P>> createListItemBuilder(UnorderedListItem listItem,
			MarkdownElementAppender<UnorderedListBuilder<P>, UnorderedListItem> appender) {
		return new UnorderedListItemBuilder<>(listItem, appender);
	}

	@Override
	protected UnorderedListBuilder<P> getBuilder() {
		return this;
	}

}
