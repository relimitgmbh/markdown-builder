package de.relimit.commons.markdown.blockelement.table;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;
import de.relimit.commons.markdown.span.SpanElementNodeBuilder;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 */
public class TableRowBuilder<P> extends NodeBuilder<P, TableRowBuilder<P>, TableRow, TableCell> {

	public TableRowBuilder(TableRow element, MarkdownElementAppender<P, TableRow> parent) {
		super(element, parent);
	}

	public TableRowBuilder(TableRow element) {
		super(element);
	}

	@Override
	protected TableRowBuilder<P> getBuilder() {
		return this;
	}

	public SpanElementNodeBuilder<TableRowBuilder<P>, TableCell> startCell() {
		return new SpanElementNodeBuilder<>(new TableCell(), this::append);
	}

}
