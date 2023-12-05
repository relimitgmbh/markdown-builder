package de.relimit.commons.markdown.util;

/**
 * Factory method for building basic span elements. Methods can be included as static imports for even less verbose code.
 */
import de.relimit.commons.markdown.blockelement.heading.Heading;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.table.TableCell;
import de.relimit.commons.markdown.blockelement.table.TableRow;
import de.relimit.commons.markdown.blockelement.table.TableRowBuilder;
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

	public static TableCell cell(Object stringyfiable) {
		return new TableCell(stringyfiable);
	}

	public static TableRow row(Object... stringyfiables) {
		final TableRowBuilder<Void> b = new TableRowBuilder<>();
		for (final Object stringyfiable : stringyfiables) {
			b.startCell().plainText(stringyfiable).end();
		}
		return b.build();
	}

}
