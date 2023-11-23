package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.BlockElementNodeBuilder;
import de.relimit.commons.markdown.builder.MarkdownElementAppender;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 * @param <B>
 *            The builder itself for method chaining
 * @param <BE>
 *            The element that is built by this builder
 */
public abstract class ListItemBuilder<P, B extends ListItemBuilder<P, B, BE>, BE extends ListItem>
		extends BlockElementNodeBuilder<P, B, BE> {

	public ListItemBuilder(BE listItem) {
		super(listItem);
	}

	public ListItemBuilder(BE listItem, MarkdownElementAppender<P, BE> parentAppender) {
		super(listItem, parentAppender);
	}

}
