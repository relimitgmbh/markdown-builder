package de.relimit.commons.markdown.blockelement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.relimit.commons.markdown.Indentable;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.blockelement.quotes.Blockquotes;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.Strings;

public abstract class BlockElementNode extends Node<BlockElement> implements Indentable {

	public static final String INDENT = Strings.fill(4, ' ');

	private int indentationLevel;

	protected BlockElementNode(int indentationLevel) {
		this.indentationLevel = indentationLevel;
	}

	@Override
	public int getIndentationLevel() {
		return indentationLevel;
	}

	@Override
	public void setIndentationLevel(int indentationLevel) {
		this.indentationLevel = indentationLevel;
		for (final BlockElement element : elements) {
			// crawl the tree
			if (element instanceof Indentable) {
				((Indentable) element).setIndentationLevel(1);
			}
		}
	}

	protected String getIndent() {
		return Strings.fill(getIndentationLevel(), INDENT);
	}

	private boolean isQuoted(BlockElement blockElement) {
		return blockElement instanceof Blockquotes;
	}

	protected List<String> serializeFirstElement(BlockElement element, MarkdownSerializationOptions options)
			throws MarkdownSerializationException {
		return serializeElement(element, options);
	}

	protected List<String> serializeElement(BlockElement element, MarkdownSerializationOptions options)
			throws MarkdownSerializationException {
		return element.serializeLines(options).stream().map(l -> getIndent() + l).collect(Collectors.toList());
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		BlockElement predecessor = null;
		for (final BlockElement element : elements) {
			/*
			 * Add an extra line between block elements.
			 */
			if (predecessor == null) {
				// First element
				lines.addAll(serializeFirstElement(element, options));
			} else {
				/*
				 * Special handling of quoted blocks. The blank lines between
				 * the block elements are inserted by this node. We therefore
				 * need to check if two consecutive blocks are quoted and quote
				 * the blank line in between to get seamless quoting.
				 */
				final boolean insertQuote = isQuoted(predecessor) && isQuoted(element);
				lines.add(getIndent() + (insertQuote ? Blockquotes.QUOTE_INDICATOR : ""));
				// Not the first element
				lines.addAll(serializeElement(element, options));
			}

			predecessor = element;
		}
		return lines;
	}

}
