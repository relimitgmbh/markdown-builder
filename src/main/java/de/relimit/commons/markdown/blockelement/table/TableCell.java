package de.relimit.commons.markdown.blockelement.table;

import java.util.List;
import java.util.Optional;

import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;

public class TableCell extends SpanElementNode {

	private TableRow parent;

	public TableCell() {
	}

	public TableCell(SpanElement... elements) {
		super(elements);
	}

	public TableCell(String text) {
		super(text);
	}

	public Optional<TableRow> getParent() {
		return Optional.ofNullable(parent);
	}

	void setParent(TableRow parent) {
		this.parent = parent;
	}

	@Override
	protected Optional<String> getPredecessor(List<String> lines) {
		return Optional.empty();
	}

	@Override
	protected Optional<String> getSuccessor(List<String> lines) {
		return Optional.empty();
	}

	/**
	 * Table cells cannot contain line breaks. The library still splits the span
	 * elements at line breaks to stay consistent. If the lines are merged, we
	 * need to insert the configured table cell line break character (&lt;br
	 * /&gt; by default) instead of a real line break
	 * (<code>System.lineSeparator()</code> by default).
	 */
	@Override
	public String serialize(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		return MarkdownSerializable.serialize(this, options, options.getTableCellLineSeparator());
	}

	/**
	 * Table cells cannot contain line breaks. The library still splits the span
	 * elements at line breaks to stay consistent. If the lines are merged, we
	 * need to suppress the end of line double spaces that would normally be
	 * inserted.
	 */
	@Override
	protected String getEndOfLineCharacters() {
		return "";
	}

	/**
	 * Returns the width of this cell in characters. Unfortunately in order to
	 * get the width there is no practical option than to serialize its content.
	 * To minimize the impact on performance we use
	 * {@link #getSerialized(MarkdownSerializationOptions)} which caches the
	 * result of the serialization. Consecutive calls to this method should in
	 * effect just be a call to {@link String#length()}.
	 * 
	 * @param options
	 * @return The width of the cell's serialized content
	 * @throws MarkdownSerializationException
	 */
	int getCellWidth(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		return getSerialized(options).length();
	}

}