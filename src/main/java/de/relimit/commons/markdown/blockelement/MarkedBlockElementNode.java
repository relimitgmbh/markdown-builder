package de.relimit.commons.markdown.blockelement;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.list.AbstractList;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.table.Table;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.Args;
import de.relimit.commons.markdown.util.Strings;

/**
 * Please note that the first element added to this
 * {@link MarkedBlockElementNode} needs to be a {@link Paragraph}. A marked
 * block element node cannot start with other {@link BlockElement}s like
 * {@link Table}s, {@link Image}s or even other {@link AbstractList}s.
 */
public abstract class MarkedBlockElementNode<TITLE extends BlockElement> extends BlockElementNode {

	protected MarkedBlockElementNode(TITLE title) {
		super(1);
		setTitle(title);
	}

	protected abstract Class<TITLE> getTitleClass();

	protected abstract String getMarker(MarkdownSerializationOptions options);

	@Override
	protected BlockElement gateKeep(BlockElement element) {
		final Class<TITLE> titleClass = getTitleClass();
		if (elements.isEmpty() && !titleClass.isAssignableFrom(element.getClass())) {
			throw new IllegalArgumentException("The first block element added to a " + getClass().getSimpleName()
					+ " must be a " + titleClass.getSimpleName() + ".");
		}
		return element;
	}

	public void setTitle(TITLE title) {
		Args.notNull(title, "title");
		if (elements.isEmpty()) {
			elements.add(title);
		} else {
			elements.set(0, title);
		}
	}

	@Override
	protected List<String> serializeFirstElement(BlockElement element, MarkdownSerializationOptions options)
			throws MarkdownSerializationException {
		/*
		 * Special handling of the list item header paragraph. Its lines are
		 * indented just like the rest of the block elements following it. But
		 * the first line is not. Instead it starts with the bullet / number.
		 */
		boolean isFirst = true;
		final String marker = getMarker(options);
		final List<String> headerLines = element.serializeLines(options);
		final List<String> lines = new ArrayList<>();
		for (final String headerLine : headerLines) {
			if (isFirst) {
				/*
				 * For aesthetic reasons the list item marker (1. / *) should be
				 * at least four characters long to align with indentation of
				 * (potential) following paragraphs. It can be longer than that
				 * if necessary and it must always end with a space.
				 */
				final String prefix =
						// Less than four? Fill up with spaces to get four
						(marker.length() < 4) ? marker + Strings.spaces(4 - marker.length()) :
						// Four or more: Add a single space
								marker + " ";
				lines.add(prefix + headerLine);
			} else {
				lines.add(getIndent() + headerLine);
			}
			isFirst = false;
		}
		return lines;
	}

}
