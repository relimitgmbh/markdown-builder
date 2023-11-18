package de.relimit.commons.markdown.configuration;

public class MarkdownSerializationOptions {

	public static final PlainTextSerializer<Object> DEFAULT_SERIALIZER = (e, o) -> o.toString();

	public static final String DEFAULT_TABLE_CELL_LINE_SEPARATOR = "<br />";

	private PlainTextSerializer<Object> plainTextSerializer;

	private String lineSeparator;

	private String tableCellLineSeparator;

	public MarkdownSerializationOptions() {
		this.plainTextSerializer = DEFAULT_SERIALIZER;
		this.lineSeparator = System.lineSeparator();
		this.tableCellLineSeparator = DEFAULT_TABLE_CELL_LINE_SEPARATOR;
	}

	/**
	 * Copy constructor for shallow copy. The serializer in particular is not
	 * cloned. So be careful augmenting the serializer of the copy on the fly.
	 * It will change the original serializer.
	 * 
	 * @param template
	 */
	public MarkdownSerializationOptions(MarkdownSerializationOptions template) {
		this.plainTextSerializer = template.plainTextSerializer;
		this.lineSeparator = template.lineSeparator;
		this.tableCellLineSeparator = template.tableCellLineSeparator;
	}

	public PlainTextSerializer<Object> getPlainTextSerializer() {
		return plainTextSerializer;
	}

	public void setPlainTextSerializer(PlainTextSerializer<Object> plainTextSerializer) {
		this.plainTextSerializer = plainTextSerializer;
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

}
