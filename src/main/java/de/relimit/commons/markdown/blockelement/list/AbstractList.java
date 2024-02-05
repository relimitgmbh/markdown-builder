package de.relimit.commons.markdown.blockelement.list;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.Indentable;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.MarkedBlockElementNode;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public abstract class AbstractList<T extends MarkedBlockElementNode> extends Node<T>
		implements BlockElement, Indentable {

	private int indentationLevel;

	public AbstractList(int indentationLevel) {
	}

	@Override
	public int getIndentationLevel() {
		return indentationLevel;
	}

	@Override
	public void setIndentationLevel(int indentationLevel) {
		this.indentationLevel = indentationLevel;
		for (final T listItem : elements) {
			listItem.setIndentationLevel(1);
		}
		invalidateSerialized();
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		// For speed
		if (elements.size() == 1) {
			return elements.get(0).serializeLines(options);
		}
		final List<String> lines = new ArrayList<>();
		for (final T element : elements) {
			lines.addAll(element.serializeLines(options));
		}
		return lines;
	}

}
