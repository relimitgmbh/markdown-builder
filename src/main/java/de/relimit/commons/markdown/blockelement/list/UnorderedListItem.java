package de.relimit.commons.markdown.blockelement.list;

public class UnorderedListItem extends ListItem {

	public static final String UNORDERED_LIST_ITEM_MARKER = "*";

	public UnorderedListItem(int indentationLevel) {
		super(indentationLevel);
	}

	@Override
	protected String getListMarker() {
		return UNORDERED_LIST_ITEM_MARKER;
	}

}
