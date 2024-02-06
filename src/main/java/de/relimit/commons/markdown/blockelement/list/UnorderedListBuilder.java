package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;

public class UnorderedListBuilder<P> extends
		ListBuilder<P, UnorderedListBuilder<P>, UnorderedList, UnorderedListItemBuilder<UnorderedListBuilder<P>>, UnorderedListItem> {

	public UnorderedListBuilder(UnorderedList element, MarkdownSerializableAppender<P, UnorderedList> parentAppender) {
		super(element, parentAppender);
	}

	public UnorderedListBuilder() {
		super(new UnorderedList());
	}

	@Override
	UnorderedListItem createListItem(Paragraph title) {
		return new UnorderedListItem(title);
	}

	@Override
	UnorderedListItemBuilder<UnorderedListBuilder<P>> createListItemBuilder(UnorderedListItem listItem,
			MarkdownSerializableAppender<UnorderedListBuilder<P>, UnorderedListItem> appender) {
		return new UnorderedListItemBuilder<>(listItem, appender);
	}

	@Override
	protected UnorderedListBuilder<P> getBuilder() {
		return this;
	}

}
