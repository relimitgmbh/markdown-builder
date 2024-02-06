package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class UnorderedListItem extends ListItem {

	public static final String UNORDERED_LIST_ITEM_MARKER = "*";

	@Override
	protected String getMarker(MarkdownSerializationOptions options) {
		return UNORDERED_LIST_ITEM_MARKER;
	}

}
