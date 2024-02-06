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
public abstract class ListItem extends MarkedBlockElementNode {

	protected ListItem() {
	}

	@Override
	protected BlockElement gateKeep(BlockElement element) {
		if (elements.isEmpty() && !(element instanceof Paragraph)) {
			throw new IllegalArgumentException("The first block element added to a " + getClass().getSimpleName()
					+ " must be a " + Paragraph.class.getSimpleName() + ".");
		}
		return element;
	}

}
