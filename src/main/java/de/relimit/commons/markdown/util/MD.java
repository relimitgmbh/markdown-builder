package de.relimit.commons.markdown.util;

import de.relimit.commons.markdown.blockelement.heading.Heading;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.document.DocumentBuilder;
import de.relimit.commons.markdown.span.HyperLink;
import de.relimit.commons.markdown.span.SpanElementNodeBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;
import de.relimit.commons.markdown.span.emphasis.EmphasisType;
import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.PlainText;

public class MD {

	private MD() {
		// utility class
	}

	public static DocumentBuilder start() {
		return new DocumentBuilder();
	}

	public static SpanElementNodeBuilder<?, Paragraph> startParagraph() {
		return new SpanElementNodeBuilder<>(new Paragraph());
	}

	public static Heading heading(int level, Object stringifyable) {
		return new Heading(level, new PlainText(stringifyable));
	}

	public static Heading heading1(Object stringifyable) {
		return heading(1, stringifyable);
	}

	public static Heading heading2(Object stringifyable) {
		return heading(2, stringifyable);
	}

	public static Heading heading3(Object stringifyable) {
		return heading(3, stringifyable);
	}

	public static Emphasis emphasis(EmphasisType type, Object stringifyable) {
		return new Emphasis(type, new PlainText(stringifyable));
	}

	public static Emphasis bold(Object stringifyable) {
		return emphasis(Type.BOLD, stringifyable);
	}

	public static Emphasis inserted(Object stringifyable) {
		return emphasis(Type.INSERTED, stringifyable);
	}

	public static Emphasis italic(Object stringifyable) {
		return emphasis(Type.ITALIC, stringifyable);
	}

	public static Emphasis marked(Object stringifyable) {
		return emphasis(Type.MARKED, stringifyable);
	}

	public static Emphasis strikethrough(Object stringifyable) {
		return emphasis(Type.STRIKETHROUGH, stringifyable);
	}

	public static Emphasis superscript(Object stringifyable) {
		return emphasis(Type.SUPERSCRIPT, stringifyable);
	}

	public static Emphasis subscript(Object stringifyable) {
		return emphasis(Type.SUBSCRIPT, stringifyable);
	}

	public static Code code(Object stringifyable) {
		return new Code(stringifyable);
	}

	public static HyperLink link(String url, Object stringifyable) {
		return new HyperLink(url, stringifyable);
	}

	public static HyperLink link(String url) {
		return new HyperLink(url);
	}

}
