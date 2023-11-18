package de.relimit.commons.markdown.blockelement.list;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.Indentable;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public abstract class AbstractList<T extends ListItem> extends Node<T> implements BlockElement, Indentable {

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
		invalidateSerialized();
	}

	@Override
	public void incrementIndentationLevel() {
		indentationLevel = indentationLevel + 1;
		for (final T listItem : elements) {
			listItem.incrementIndentationLevel();
		}
		invalidateSerialized();
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		for (final T element : elements) {
			lines.addAll(element.serializeLines(options));
		}
		return lines;
	}

}
