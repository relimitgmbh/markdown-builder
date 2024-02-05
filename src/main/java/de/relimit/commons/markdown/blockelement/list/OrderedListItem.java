package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class OrderedListItem extends ListItem {

	private int ordinal;

	public OrderedListItem(int indentationLevel) {
		super(indentationLevel);
	}

	@Override
	protected String getMarker(MarkdownSerializationOptions options) {
		return ordinal + ".";
	}

	void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

}
