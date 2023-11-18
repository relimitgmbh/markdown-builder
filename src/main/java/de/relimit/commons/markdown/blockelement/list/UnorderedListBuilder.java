package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.MarkdownElementAppender;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;

public class UnorderedListBuilder<P> extends ListBuilder<P, UnorderedList, UnorderedListItem> {

	public UnorderedListBuilder(UnorderedList element, MarkdownElementAppender<P, UnorderedList> parentAppender) {
		super(element, parentAppender);
	}

	public UnorderedListBuilder(UnorderedList element) {
		super(element);
	}

	@Override
	UnorderedListItem createListItem(int indentationLevel, Paragraph listItemParagraph) {
		return new UnorderedListItem(indentationLevel, listItemParagraph);
	}

}
