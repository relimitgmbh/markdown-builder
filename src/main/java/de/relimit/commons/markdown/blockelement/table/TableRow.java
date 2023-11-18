package de.relimit.commons.markdown.blockelement.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.StringUtil;

public class TableRow extends Node<TableCell> {

	public static final String SEPARATOR = "|";
	public static final String WHITESPACE = " ";

	private Table parent;

	public TableRow() {
	}

	public TableRow(List<TableCell> columns) {
		columns.forEach(this::append);
	}

	private Optional<TableRow> getHeaderRow() {
		return getParent().flatMap(Table::getHeaderRow);
	}

	private boolean roomForMore() {
		return getHeaderRow().map(row -> size() < row.size()).orElse(true);
	}

	private boolean overflow() {
		return getHeaderRow().map(row -> size() > row.size()).orElse(false);
	}

	private Optional<Integer> getColumnLimit() {
		return getHeaderRow().map(TableRow::size);
	}

	@Override
	public void append(TableCell element) {
		element.setParent(this);
		if (roomForMore()) {
			super.append(element);
		}
	}

	public Optional<Table> getParent() {
		return Optional.ofNullable(parent);
	}

	void setParent(Table parent) {
		this.parent = parent;
		if (overflow()) {
			final int columnLimit = getColumnLimit()
					.orElseThrow(() -> new AssertionError("Expected to find a header column at this point."));
			elements = elements.subList(0, columnLimit);
		}
		invalidateSerialized();
	}

	Alignment getAlignment(int columnIndex) {
		return getParent().map(parent -> parent.getAlignment(columnIndex)).orElse(Table.DEFAULT_ALIGNMENT);
	}

	/**
	 * Returns either the column widths of the {@link Table} (if there is any)
	 * or the widths of the row's cells. Note that the list of column widths
	 * from the table might contain more cells (columns) than this row actually
	 * has. In that case these non existing trailing cells need to be rendered
	 * as blank cells.
	 * 
	 * @param options
	 * @return
	 * @throws MarkdownSerializationException
	 */
	Map<Integer, Integer> getColumnWidths(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		if (parent != null) {
			return parent.getTableMetrics(options).getColumnWidths();
		}
		final Map<Integer, Integer> columnWidths = new HashMap<>();
		int index = 0;
		for (final TableCell cell : elements) {
			columnWidths.put(index++, cell.getSerialized(options).length());
		}
		return columnWidths;
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final Map<Integer, Integer> columnWidths = getColumnWidths(options);

		final StringBuilder sb = new StringBuilder();
		for (int columnIndex = 0; columnIndex < columnWidths.size(); columnIndex++) {
			sb.append(SEPARATOR);
			String value;
			if (columnIndex >= elements.size()) {
				// blank cell
				value = "";
			} else {
				final TableCell cell = elements.get(columnIndex);
				value = WHITESPACE + cell.getSerialized(options) + WHITESPACE;
			}
			final int targetWidth = columnWidths.get(columnIndex) + 2;
			final Alignment alignment = getAlignment(columnIndex);
			// Add 2 for WHITESPACE
			value = StringUtil.fillUpAligned(value, WHITESPACE, targetWidth, alignment);
			sb.append(value);
		}
		sb.append(SEPARATOR);

		final List<String> lines = new ArrayList<>();
		lines.add(sb.toString());
		return lines;
	}

	public TableCell getCell(int index) {
		return elements.get(index);
	}

	/**
	 * Returns a list of the widths of the cells in this row. See
	 * {@link TableCell#getCellWidth(MarkdownSerializationOptions)} on a note on
	 * performance.
	 * 
	 * @param options
	 * @return the widths of the cells in this row
	 * @throws MarkdownSerializationException
	 */
	List<Integer> getCellWidths(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<Integer> widths = new ArrayList<>();
		for (final TableCell cell : elements) {
			widths.add(cell.getCellWidth(options));
		}
		return widths;
	}

	@Override
	public void invalidateSerialized() {
		super.invalidateSerialized();
		for (final TableCell cell : elements) {
			cell.invalidateSerialized();
		}
	}

}
