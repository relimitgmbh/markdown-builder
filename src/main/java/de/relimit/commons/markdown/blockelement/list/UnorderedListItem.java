package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;

public class UnorderedListItem extends ListItem {

	public static final String UNORDERED_LIST_ITEM_MARKER = "*";

	public UnorderedListItem(int indentationLevel, Paragraph listItemHeader) {
		super(indentationLevel, listItemHeader);
	}

	@Override
	protected String getListMarker() {
		return UNORDERED_LIST_ITEM_MARKER;
	}

}
