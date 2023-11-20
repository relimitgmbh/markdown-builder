package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.BlockElementNodeBuilder;
import de.relimit.commons.markdown.builder.MarkdownElementAppender;

public class ListItemBuilder<P, E extends ListItem> extends BlockElementNodeBuilder<ListItemBuilder<P, E>, P, E> {

	public ListItemBuilder(E listItem) {
		super(listItem);
	}

	public ListItemBuilder(E listItem, MarkdownElementAppender<P, E> parentAppender) {
		super(listItem, parentAppender);
	}

	@Override
	protected ListItemBuilder<P, E> getBuilder() {
		return this;
	}

}
