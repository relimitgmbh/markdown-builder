package de.relimit.commons.markdown.blockelement.table;

import java.util.List;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 */
public class TableBuilder<P> extends NodeBuilder<P, TableBuilder<P>, Table, TableRow> implements BlockElement {

	public TableBuilder(MarkdownSerializableAppender<P, Table> parent) {
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

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		return getElement().serializeLines(options);
	}

}
