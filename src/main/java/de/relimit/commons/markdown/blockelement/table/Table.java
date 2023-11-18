package de.relimit.commons.markdown.blockelement.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.StringUtil;

public class Table extends Node<TableRow> implements BlockElement {

	public static final int DEFAULT_MINIMUM_COLUMN_WIDTH = 3;

	public static final Alignment DEFAULT_ALIGNMENT = Alignment.NEUTRAL;

	private Alignment defaultAlignment = DEFAULT_ALIGNMENT;

	private Map<Integer, Alignment> alignments;

	private boolean firstRowIsHeader;

	private int minimumColumnWidth = DEFAULT_MINIMUM_COLUMN_WIDTH;

	private TableMetrics tableMetrics;

	public static class TableMetrics {

		private Map<Integer, Integer> columnWidths;

		public TableMetrics(Map<Integer, Integer> columnWidths) {
			this.columnWidths = columnWidths;
		}

		public Map<Integer, Integer> getColumnWidths() {
			return columnWidths;
		}

	}

	public Table() {
		this.alignments = new HashMap<>();
		firstRowIsHeader = true;
	}

	public Table(List<TableRow> rows) {
		this();
		rows.forEach(this::append);
	}

	public Table(List<TableRow> rows, Alignment... alignments) {
		this(rows);
		int index = 0;
		for (final Alignment alignment : alignments) {
			this.alignments.put(index, alignment);
			index++;
		}
	}

	TableMetrics getTableMetrics(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		if (tableMetrics == null) {
			tableMetrics = calculateTableMetrics(options);
		}
		return tableMetrics;
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		int index = 0;
		for (final TableRow row : elements) {

			appendTo(options, lines, row, index);

			index++;
		}
		return lines;
	}

	private void appendTo(MarkdownSerializationOptions options, List<String> lines, TableRow row, int index)
			throws MarkdownSerializationException {
		final boolean isFirstRow = index == 0;
		if (isFirstRow && !firstRowIsHeader) {
			lines.add(generateHeaderSeparator(options));
		}

		lines.addAll(row.serializeLines(options));

		if (isFirstRow && firstRowIsHeader) {
			lines.add(generateHeaderSeparator(options));
		}
	}

	public String generateHeaderSeparator(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final TableMetrics metrics = getTableMetrics(options);
		final Map<Integer, Integer> columnWidths = metrics.getColumnWidths();

		final StringBuilder sb = new StringBuilder();
		for (int columnIndex = 0; columnIndex < columnWidths.size(); columnIndex++) {
			sb.append(TableRow.SEPARATOR);

			final String separator = StringUtil.fill(columnWidths.get(columnIndex), "-");

			final Alignment alignment = getAlignment(columnIndex);
			switch (alignment) {
			case RIGHT:
				sb.append(TableRow.WHITESPACE + separator + ":");
				break;
			case CENTER:
				sb.append(":" + separator + ":");
				break;
			case LEFT:
				sb.append(":" + separator + TableRow.WHITESPACE);
				break;
			case NEUTRAL:
				/*
				 * Leave out any alignment indicator. This might make a table
				 * more compatible across markout engines and usually defaults
				 * to left alignment.
				 */
				sb.append(TableRow.WHITESPACE + separator + TableRow.WHITESPACE);
				break;
			default:
				throw Alignment.illegalAlignment(alignment);
			}

			if (columnIndex == columnWidths.size() - 1) {
				sb.append(TableRow.SEPARATOR);
			}
		}
		return sb.toString();
	}

	private TableMetrics calculateTableMetrics(MarkdownSerializationOptions options)
			throws MarkdownSerializationException {
		final Map<Integer, Integer> columnWidths = new HashMap<>();

		for (final TableRow row : elements) {
			final List<Integer> widths = row.getCellWidths(options);
			int columnIndex = 0;
			for (final int width : widths) {
				columnWidths.merge(columnIndex, width, Integer::max);
				columnIndex++;
			}
		}

		return new TableMetrics(columnWidths);
	}

	public Alignment getAlignment(int columnIndex) {
		return alignments.getOrDefault(columnIndex, defaultAlignment);
	}

	public List<TableRow> getRows() {
		return elements;
	}

	public Map<Integer, Alignment> getAlignments() {
		return alignments;
	}

	public void setAlignment(int index, Alignment alignment) {
		if (index < 0) {
			throw new IllegalArgumentException("Index cannot be negative.");
		}
		Objects.requireNonNull(alignment);
		this.alignments.put(index, alignment);
		invalidateSerialized();
	}

	public boolean isFirstRowHeader() {
		return firstRowIsHeader;
	}

	public void useFirstRowAsHeader(boolean firstRowIsHeader) {
		this.firstRowIsHeader = firstRowIsHeader;
		invalidateSerialized();
	}

	public int getMinimumColumnWidth() {
		return minimumColumnWidth;
	}

	public void setMinimumColumnWidth(int minimumColumnWidth) {
		this.minimumColumnWidth = minimumColumnWidth;
		invalidateSerialized();
	}

	public Alignment getDefaultAlignment() {
		return defaultAlignment;
	}

	public void setDefaultAlignment(Alignment defaultAlignment) {
		this.defaultAlignment = Objects.requireNonNull(defaultAlignment);
		invalidateSerialized();
	}

	@Override
	public void append(TableRow element) {
		element.setParent(this);
		super.append(element);
	}

	Optional<TableRow> getHeaderRow() {
		if (elements.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(elements.get(0));
	}

	@Override
	public void invalidateSerialized() {
		super.invalidateSerialized();
		for (final TableRow row : elements) {
			row.invalidateSerialized();
		}
		/*
		 * Not very efficient as the metrics need to be recalculated but will do
		 * for now
		 */
		this.tableMetrics = null;
	}

}