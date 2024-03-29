package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 * @param <B>
 *            The list builder itself for method chaining
 * @param <BE>
 *            The list that is built by this builder
 * @param <B2>
 *            The list item builder that builds list items compatible with the
 *            list
 * @param <NE>
 *            The list item compatible with the list
 */
public abstract class ListBuilder<P, B extends ListBuilder<P, B, BE, B2, NE>, BE extends AbstractList<NE>, B2 extends ListItemBuilder<B, B2, NE>, NE extends ListItem>
		extends NodeBuilder<P, B, BE, NE> {

	protected ListBuilder(BE element, MarkdownSerializableAppender<P, BE> parentAppender) {
		super(element, parentAppender);
	}

	protected ListBuilder(BE element) {
		super(element);
	}

	abstract NE createListItem(Paragraph title);

	abstract B2 createListItemBuilder(NE listItem, MarkdownSerializableAppender<B, NE> appender);

	public B2 startItem(Object title) {
		return startItem(new Paragraph(title));
	}

	public B2 startItem(Paragraph title) {
		/*
		 * Indentation leven doesn't matter because it will be set upon
		 * insertion
		 */
		final NE listItem = createListItem(title);
		return createListItemBuilder(listItem, this::append);
	}

	@Override
	protected NE gateKeep(NE element) {
		element.setIndentationLevel(1);
		return element;
	}

	public B item(Paragraph title) {
		return startItem(title).end();
	}

	public B item(Object stringifyable) {
		return item(new Paragraph(stringifyable));
	}

}
