package de.relimit.commons.markdown.blockelement.heading;

import java.util.List;
import java.util.Optional;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.util.StringUtil;

public class Heading extends SpanElementNode implements BlockElement {

	public static final int MIN_LEVEL = 1;
	public static final int MAX_LEVEL = 6;

	// Underline characters for Setext style headings
	public static final char SETEXT_1ST_LEVEL = '=';
	public static final char SETEXT_2ND_LEVEL = '-';

	// Prefix character for Atx style headings
	public static final char ATX_PREFIX = '#';

	private int level;

	boolean underlineStyle = true;

	public Heading() {
		this.level = MIN_LEVEL;
	}

	public Heading(int level) {
		this.level = level;
	}

	public Heading(Object stringifyable) {
		this(MIN_LEVEL, new PlainText(stringifyable));
	}

	public Heading(int level, SpanElement... elements) {
		super(elements);
		setLevel(level);
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = super.serializeLines(options);
		if (underlineStyle && level < 3) {
			final char underlineChar = (level == 1) ? SETEXT_1ST_LEVEL : SETEXT_2ND_LEVEL;
			/*
			 * It is possible for a heading to contain line breaks. Set the
			 * length of the separator to the longest text line. Note that this
			 * is purely cosmetic as a single character would suffice as far as
			 * the markdown spec is concerned.
			 */
			final int maxLineLength = lines.stream().map(String::length).max(Integer::compareTo)
					.orElseThrow(() -> new MarkdownSerializationException(
							"Text of " + Heading.class.getSimpleName() + " cannot be empty."));
			lines.add(StringUtil.fill(maxLineLength, underlineChar));
		}
		return lines;
	}

	@Override
	protected Optional<String> getPredecessor(List<String> lines) {
		if (underlineStyle && level < 3) {
			// Underline added by serializeLines
			return Optional.empty();
		}
		return Optional.of(StringUtil.fill(level, ATX_PREFIX) + " ");
	}

	@Override
	protected Optional<String> getSuccessor(List<String> lines) {
		return Optional.empty();
	}

	private int trimLevel(int level) {
		// TODO code smell
		return Math.min(MAX_LEVEL, Math.max(MIN_LEVEL, level));
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = trimLevel(level);
		invalidateSerialized();
	}

	public boolean isUnderlineStyle() {
		return underlineStyle;
	}

	public void setUnderlineStyle(boolean underlineStyle) {
		this.underlineStyle = underlineStyle;
		invalidateSerialized();
	}

}
