package de.relimit.commons.markdown.span;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.textual.Fences;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.util.Strings;

public abstract class SpanElementNode extends Node<SpanElement> {

	public static final String END_OF_LINE = Strings.fill(2, ' ');

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

	public SpanElementNode(Object stringifyable) {
		this(new PlainText(stringifyable));
	}

	public SpanElementNode(String text) {
		this((Object) text);
	}

	abstract protected Fences getFences();

	protected String getEndOfLineCharacters() {
		return END_OF_LINE;
	}

	/**
	 * Concatenates a list of span elements to one big span element. Span
	 * elements can be nested and each one can contain line breaks meaning
	 * produce more than one line. This in turn means that the elements need to
	 * be stitched together in a way that the last line of one span element and
	 * the first line of the next span element meet on the same line.
	 */
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

		final Fences fences = getFences();
		fences.getLeading().ifPresent(s -> {
			// Add a fence to the beginning of the first line
			lines.set(0, s + lines.get(0));
		});
		fences.getTrailing().ifPresent(s -> {
			// Append a fence to the end of the last line
			final int lastIndex = lines.size() - 1;
			lines.set(lastIndex, lines.get(lastIndex) + s);
		});

		return lines;
	}

}
