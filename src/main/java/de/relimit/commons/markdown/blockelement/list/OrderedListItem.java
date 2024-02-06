package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class OrderedListItem extends ListItem {

	private int ordinal;

	public OrderedListItem() {
	}

	@Override
	protected String getMarker(MarkdownSerializationOptions options) {
		return ordinal + ".";
	}

	void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

}
