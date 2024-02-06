package de.relimit.commons.markdown.blockelement.list;

public class OrderedListItem extends ListItem {

	private int ordinal;

	public OrderedListItem(int indentationLevel) {
		super(indentationLevel);
	}

	@Override
	String getListMarker() {
		return ordinal + ".";
	}

	void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

}
