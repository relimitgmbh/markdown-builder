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

public class Markdown {

	public static DocumentBuilder start() {
		return new DocumentBuilder();
	}

	public static SpanElementNodeBuilder<?, Paragraph> startParagraph() {
		return new SpanElementNodeBuilder(new Paragraph());
	}

	public static Heading heading(int level, Object stringyfiable) {
		return new Heading(level, new PlainText(stringyfiable));
	}

	public static Heading heading1(Object stringyfiable) {
		return heading(1, stringyfiable);
	}

	public static Heading heading2(Object stringyfiable) {
		return heading(2, stringyfiable);
	}

	public static Heading heading3(Object stringyfiable) {
		return heading(3, stringyfiable);
	}

	public static Emphasis emphasis(EmphasisType type, Object stringyfiable) {
		return new Emphasis(type, new PlainText(stringyfiable));
	}

	public static Emphasis bold(Object stringyfiable) {
		return emphasis(Type.BOLD, stringyfiable);
	}

	public static Emphasis inserted(Object stringyfiable) {
		return emphasis(Type.INSERTED, stringyfiable);
	}

	public static Emphasis italic(Object stringyfiable) {
		return emphasis(Type.ITALIC, stringyfiable);
	}

	public static Emphasis marked(Object stringyfiable) {
		return emphasis(Type.MARKED, stringyfiable);
	}

	public static Emphasis strikethrough(Object stringyfiable) {
		return emphasis(Type.STRIKETHROUGH, stringyfiable);
	}

	public static Emphasis superscript(Object stringyfiable) {
		return emphasis(Type.SUPERSCRIPT, stringyfiable);
	}

	public static Emphasis subscript(Object stringyfiable) {
		return emphasis(Type.SUBSCRIPT, stringyfiable);
	}

	public static Code code(Object stringyfiable) {
		return new Code(stringyfiable);
	}

	public static HyperLink link(String url, Object stringyfiable) {
		return new HyperLink(url, stringyfiable);
	}

	public static HyperLink link(String url) {
		return new HyperLink(url);
	}

	private Markdown() {
		// utility class
	}

}
