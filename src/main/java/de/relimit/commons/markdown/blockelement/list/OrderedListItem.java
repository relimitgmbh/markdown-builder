package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;

public class OrderedListItem extends ListItem {

	private int ordinal;

	public OrderedListItem(int indentationLevel, Paragraph listItemHeader) {
		super(indentationLevel, listItemHeader);
	}

	@Override
	String getListMarker() {
		return ordinal + ".";
	}

	void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

}
