package de.relimit.commons.markdown.blockelement.heading;

import java.util.List;
import java.util.Optional;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.util.Strings;

public class Heading extends SpanElementNode implements BlockElement {

	public static final int MIN_LEVEL = 1;
	public static final int MAX_LEVEL = 6;

	// Underline characters for Setext style headings
	public static final char SETEXT_1ST_LEVEL = '=';
	public static final char SETEXT_2ND_LEVEL = '-';

	// Prefix character for Atx style headings
	public static final char ATX_PREFIX = '#';

	public static final HeadingStyle DEFAULT_HEADING_STYLE = HeadingStyle.SETEXT;

	private int level;

	private HeadingStyle headingStyle;

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

	private boolean useUnderline(MarkdownSerializationOptions options) {
		if (level > 2) {
			return false;
		}
		final HeadingStyle effectiveHeadingStyle = Optional.ofNullable(headingStyle)
				.orElse(options.getDefaultHeadingStyle());
		return HeadingStyle.SETEXT == effectiveHeadingStyle;
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = super.serializeLines(options);
		if (useUnderline(options)) {
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
			lines.add(Strings.fill(maxLineLength, underlineChar));
		}
		return lines;
	}

	@Override
	public Fences getFences(MarkdownSerializationOptions options) {
		if (useUnderline(options)) {
			// Underline added by serializeLines
			return Fences.none();
		}
		return Fences.rightOf(Strings.fill(level, ATX_PREFIX) + " ");
	}

	private int trimLevel(int level) {
		return Math.min(MAX_LEVEL, Math.max(MIN_LEVEL, level));
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = trimLevel(level);
		invalidateSerialized();
	}

	/**
	 * @deprecated Use {@link #getHeadingStyle()} and check if equals
	 *             {@link HeadingStyle#SETEXT} instead
	 * @return
	 */
	@Deprecated
	public boolean isUnderlineStyle() {
		return HeadingStyle.SETEXT == headingStyle;
	}

	/**
	 * @deprecated Use {@link #setHeadingStyle(HeadingStyle)} instead.
	 * @param underlineStyle
	 */
	@Deprecated
	public void setUnderlineStyle(boolean underlineStyle) {
		setHeadingStyle(underlineStyle ? HeadingStyle.SETEXT : HeadingStyle.ATX);
	}

	public void setHeadingStyle(HeadingStyle headingStyle) {
		this.headingStyle = headingStyle;
		invalidateSerialized();
	}

	public HeadingStyle getHeadingStyle() {
		return Optional.ofNullable(headingStyle).orElse(DEFAULT_HEADING_STYLE);
	}

}
