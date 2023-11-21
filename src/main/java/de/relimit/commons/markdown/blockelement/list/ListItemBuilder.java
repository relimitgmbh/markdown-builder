package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.BlockElementNodeBuilder;
import de.relimit.commons.markdown.builder.MarkdownElementAppender;

public abstract class ListItemBuilder<B extends ListItemBuilder<B, P, E>, P, E extends ListItem>
		extends BlockElementNodeBuilder<B, P, E> {

	public ListItemBuilder(E listItem) {
		super(listItem);
	}

	public ListItemBuilder(E listItem, MarkdownElementAppender<P, E> parentAppender) {
		super(listItem, parentAppender);
	}

}
