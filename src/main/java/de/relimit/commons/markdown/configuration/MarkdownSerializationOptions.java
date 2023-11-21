package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.table.TableCell;
import de.relimit.commons.markdown.span.textual.Textual;

public class MarkdownSerializationOptions {

	public static final MarkdownSerializationOptions DEFAULT_OPTIONS = new MarkdownSerializationOptions();

	private TextSerializer<Object> serializer;

	private TextEscaper escaper;

	private String lineSeparator;

	private String tableCellLineSeparator;

	public MarkdownSerializationOptions() {
		this.serializer = TextSerializer.DEFAULT_SERIALIZER;
		this.escaper = DefaultEscaper.DEFAULT_ESCAPER;
		this.lineSeparator = System.lineSeparator();
		this.tableCellLineSeparator = TableCell.DEFAULT_TABLE_CELL_LINE_SEPARATOR;
	}

	/**
	 * Copy constructor for shallow copy. The serializer in particular is not
	 * cloned. So be careful augmenting the serializer of the copy on the fly.
	 * It will change the original serializer.
	 * 
	 * @param template
	 */
	public MarkdownSerializationOptions(MarkdownSerializationOptions template) {
		this.serializer = template.serializer;
		this.lineSeparator = template.lineSeparator;
		this.tableCellLineSeparator = template.tableCellLineSeparator;
	}

	public TextSerializer<Object> getSerializer() {
		return serializer;
	}

	public void setSerializer(TextSerializer<Object> plainTextSerializer) {
		this.serializer = plainTextSerializer;
	}

	public TextEscaper getEscaper() {
		return escaper;
	}

	public void setEscaper(TextEscaper escaper) {
		this.escaper = escaper;
	}

	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}

	public String getTableCellLineSeparator() {
		return tableCellLineSeparator;
	}

	public void setTableCellLineSeparator(String tableCellLineSeparator) {
		this.tableCellLineSeparator = tableCellLineSeparator;
	}

	public MarkdownSerializationOptions copy() {
		return new MarkdownSerializationOptions(this);
	}

	public String stringify(Textual element) throws MarkdownSerializationException {
		String text = getSerializer().serialize(element, element.getStringyfiable());
		text = getEscaper().escape(element, text);
		return text;
	}

}
