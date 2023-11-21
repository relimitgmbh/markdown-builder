package de.relimit.commons.markdown.span;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.util.StringUtil;

public abstract class SpanElementNode extends Node<SpanElement> {

	public static final String END_OF_LINE = StringUtil.fill(2, ' ');

	public SpanElementNode() {
	}

	/**
	 * For convenience.
	 * 
	 * @param element
	 */
	public SpanElementNode(SpanElement... elements) {
		append(elements);
	}

	public SpanElementNode(SpanElement element) {
		append(element);
	}

	public SpanElementNode(Object stringyfiable) {
		this(new PlainText(stringyfiable));
	}

	public SpanElementNode(String text) {
		this((Object) text);
	}

	abstract protected Optional<String> getPredecessor(List<String> lines);

	abstract protected Optional<String> getSuccessor(List<String> lines);

	protected String getEndOfLineCharacters() {
		return END_OF_LINE;
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		StringBuilder currentLine = new StringBuilder();
		for (final SpanElement element : elements) {
			boolean firstLine = true;
			for (final String line : element.serializeLines(options)) {
				if (firstLine) {
					// Stay on the same line
					currentLine.append(line);
				} else {
					/* Complete the line, dump it and start a new one */
					lines.add(currentLine.toString() + getEndOfLineCharacters());
					currentLine = new StringBuilder();
					currentLine.append(line);
				}
				firstLine = false;
			}
		}
		final String tail = currentLine.toString();
		if (!tail.isEmpty()) {
			lines.add(tail);
		}
		if (lines.isEmpty()) {
			return lines;
		}

		getPredecessor(lines).ifPresent(s -> {
			lines.set(0, s + lines.get(0));
		});

		getSuccessor(lines).ifPresent(s -> {
			lines.set(lines.size() - 1, lines.get(0) + s);
		});

		return lines;
	}

}
