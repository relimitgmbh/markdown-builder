package de.relimit.commons.markdown.blockelement.list;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.BlockElementNode;
import de.relimit.commons.markdown.blockelement.image.Image;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.table.Table;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.Strings;

public abstract class ListItem extends BlockElementNode {

	protected ListItem(int indentationLevel) {
		super(indentationLevel);
	}

	abstract String getListMarker();

	/**
	 * Appends a {@link BlockElement} to this list. Please note that the first
	 * element needs to be a {@link Paragraph}. A list cannot start with other
	 * {@link BlockElement}s like {@link Table}s, {@link Image}s or even other
	 * {@link AbstractList}s.
	 */
	@Override
	public void append(BlockElement element) {
		if (elements.isEmpty() && !(element instanceof Paragraph)) {
			throw new IllegalArgumentException(
					"The first element added to a list must be a " + Paragraph.class.getSimpleName() + ".");
		}
		super.append(element);
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
		final String marker = getListMarker();
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
