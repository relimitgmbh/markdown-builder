package de.relimit.commons.markdown.blockelement.list;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElementNode;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.StringUtil;

public abstract class ListItem extends BlockElementNode {

	private Paragraph listItemHeader;

	protected ListItem(int indentationLevel, Paragraph listItemHeader) {
		super(indentationLevel);
		this.listItemHeader = listItemHeader;
	}

	abstract String getListMarker();

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		/*
		 * Special handling of the list item header paragraph. Its lines are
		 * indented just like the rest of the block elements following it. But
		 * the first line is not. Instead it starts with the bullet / number.
		 */
		final List<String> lines = new ArrayList<>();
		final List<String> headerLines = listItemHeader.serializeLines(options);
		boolean isFirst = true;
		final String marker = getListMarker();
		/*
		 * For aesthetic reasons the list item marker (1. / *) should be at
		 * least four characters long to align with indentation of (potential)
		 * following paragraphs. It can be longer than that if necessary and it
		 * must always end with a space.
		 */
		final String prefix =
				// Less than four? Fill up with spaces to get four
				(marker.length() < 4) ? marker + StringUtil.spaces(4 - marker.length()) :
				// Four or more: Add a single space
						marker + " ";
		for (final String headerLine : headerLines) {
			if (isFirst) {
				lines.add(prefix + headerLine);
			} else {
				lines.add(getIndent() + headerLine);
			}
			isFirst = false;
		}
		final List<String> blockElementLines = super.serializeLines(options);
		if (!blockElementLines.isEmpty()) {
			lines.add("");
			lines.addAll(blockElementLines);
		}
		return lines;
	}

}
