package de.relimit.commons.markdown.span;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.converter.Stringifier;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.util.Strings;

public abstract class SpanElementNode extends Node<SpanElement> {

	public static final String END_OF_LINE = Strings.fill(2, ' ');

	protected SpanElementNode() {
	}

	protected SpanElementNode(SpanElement... elements) {
		append(elements);
	}

	/**
	 * Method that provides some syntactic sugar for easy object building. It
	 * accepts a varargs array of {@link Object} and converts every element in
	 * the array to a {@link SpanElement}. Every {@link SpanElement} is accepted
	 * as-is without conversion. Other {@link MarkdownElement}s will cause an
	 * {@link IllegalArgumentException}. Any other {@link Object} will be
	 * wrapped in a {@link PlainText} and consequently be serialized according
	 * to the {@link Stringifier} set via {@link MarkdownSerializationOptions}.
	 * ({@link #toString()} by default)
	 * 
	 * @param elements
	 */
	protected SpanElementNode(Object... elements) {
		final Stream<SpanElement> spanElements = Arrays.stream(elements).map(e -> {
			if (e instanceof MarkdownSerializable) {
				if (e instanceof SpanElement) {
					return (SpanElement) e;
				}
				throw new IllegalArgumentException(
						"Cannot add " + MarkdownElement.class.getSimpleName() + " elements of type "
								+ e.getClass().getSimpleName() + ". Only " + MarkdownElement.class.getSimpleName()
								+ "s of type " + SpanElement.class.getSimpleName() + " are allowed.");
			}
			return (SpanElement) new PlainText(e);
		});
		append(spanElements);
	}

	protected abstract Fences getFences(MarkdownSerializationOptions options);

	protected boolean swallowIfEmpty() {
		return true;
	}

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
			if (swallowIfEmpty()) {
				return lines;
			} else {
				lines.add("");
			}
		}

		final Fences fences = getFences(options);
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
