package de.relimit.commons.markdown.blockelement.table;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;

public class TableCell extends SpanElementNode {

	public static final String DEFAULT_TABLE_CELL_LINE_SEPARATOR = "<br />";

	public TableCell() {
	}

	public TableCell(Object stringifyable) {
		super(stringifyable);
	}

	public TableCell(SpanElement... elements) {
		super(elements);
	}

	public TableCell(SpanElement element) {
		super(element);
	}

	public TableCell(String text) {
		super(text);
	}

	@Override
	public Fences getFences() {
		return Fences.none();
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
		return MarkdownElement.serialize(this, options, options.getTableCellLineSeparator());
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
