package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class OrderedListItem extends ListItem {

	private int ordinal;

	public OrderedListItem(Paragraph title) {
		super(title);
	}

	@Override
	protected String getMarker(MarkdownSerializationOptions options) {
		return ordinal + ".";
	}

	void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

}
