package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.MarkedBlockElementNode;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.table.Table;

/**
 * Please note that the first element added to this {@link ListItem} needs to be
 * a {@link Paragraph}. A list item cannot start with other
 * {@link BlockElement}s like {@link Table}s, {@link Image}s or even other
 * {@link AbstractList}s.
 */
public abstract class ListItem extends MarkedBlockElementNode<Paragraph> {

	protected ListItem(Paragraph title) {
		super(title);
	}

	@Override
	protected Class<Paragraph> getTitleClass() {
		return Paragraph.class;
	}

}
