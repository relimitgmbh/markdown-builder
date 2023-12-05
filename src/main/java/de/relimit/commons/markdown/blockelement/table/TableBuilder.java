package de.relimit.commons.markdown.blockelement.table;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 */
public class TableBuilder<P> extends NodeBuilder<P, TableBuilder<P>, Table, TableRow> {

	public TableBuilder(MarkdownElementAppender<P, Table> parent) {
		super(new Table(), parent);
	}

	public TableBuilder() {
		super(new Table());
	}

	@Override
	protected TableBuilder<P> getBuilder() {
		return this;
	}

	// General table properties

	public TableBuilder<P> align(Alignment... alignments) {
		int index = 0;
		for (final Alignment alignment : alignments) {
			align(index, alignment);
			index++;
		}
		return this;
	}

	public TableBuilder<P> align(int index, Alignment alignment) {
		getElement().setAlignment(index, alignment);
		return this;
	}

	public TableBuilder<P> defaultAlignment(Alignment alignment) {
		getElement().setDefaultAlignment(alignment);
		return this;
	}

	public TableBuilder<P> firstRowisHeader() {
		getElement().useFirstRowAsHeader(true);
		return this;
	}

	public TableBuilder<P> firstRowisHeader(boolean firstRowIsHeader) {
		getElement().useFirstRowAsHeader(firstRowIsHeader);
		return this;
	}

	public TableBuilder<P> minColumnWidth(int minColumnWidth) {
		getElement().setMinimumColumnWidth(minColumnWidth);
		return this;
	}

	// Rows

	public TableRowBuilder<TableBuilder<P>> startRow() {
		return new TableRowBuilder<>(this::append);
	}

}
