package de.relimit.commons.markdown.util;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.admonition.Admonition;
import de.relimit.commons.markdown.blockelement.admonition.AdmonitionBuilder;
import de.relimit.commons.markdown.blockelement.admonition.Title;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.heading.Heading;
import de.relimit.commons.markdown.blockelement.list.ListItem;
import de.relimit.commons.markdown.blockelement.list.OrderedListItemBuilder;
import de.relimit.commons.markdown.blockelement.list.TaskListItemBuilder;
import de.relimit.commons.markdown.blockelement.list.UnorderedListItemBuilder;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRule;
import de.relimit.commons.markdown.blockelement.table.TableCell;
import de.relimit.commons.markdown.blockelement.table.TableRow;
import de.relimit.commons.markdown.blockelement.table.TableRowBuilder;
import de.relimit.commons.markdown.span.HyperLink;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNodeBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;
import de.relimit.commons.markdown.span.emphasis.EmphasisType;
import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.PlainText;

/**
 * Static Factory method class for building span and block elements. Include the
 * methods as static imports in your project for even less verbose code.
 */
public class MD {

	private MD() {
		// utility class
	}

	// Paragraph and text

	public static Paragraph paragraph(SpanElement... element) {
		return startParagraph().append(element).build();
	}

	public static Paragraph paragraph(Object... stringifyable) {
		return startParagraph().plainText(stringifyable).build();
	}

	public static SpanElementNodeBuilder<Void, Paragraph> startParagraph() {
		return new SpanElementNodeBuilder<>(new Paragraph());
	}

	public static PlainText txt(Object stringifyable) {
		return new PlainText(stringifyable);
	}

	// heading

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

	// Emphasis

	public static Emphasis emphasis(EmphasisType type, SpanElement element) {
		return new Emphasis(type, element);
	}

	public static Emphasis emphasis(EmphasisType type, Object stringifyable) {
		return emphasis(type, txt(stringifyable));
	}

	public static Emphasis bold(SpanElement element) {
		return emphasis(Type.BOLD, element);
	}

	public static Emphasis bold(Object stringifyable) {
		return emphasis(Type.BOLD, stringifyable);
	}

	public static Emphasis inserted(SpanElement element) {
		return emphasis(Type.INSERTED, element);
	}

	public static Emphasis inserted(Object stringifyable) {
		return emphasis(Type.INSERTED, stringifyable);
	}

	public static Emphasis italic(SpanElement element) {
		return emphasis(Type.ITALIC, element);
	}

	public static Emphasis italic(Object stringifyable) {
		return emphasis(Type.ITALIC, stringifyable);
	}

	public static Emphasis marked(SpanElement element) {
		return emphasis(Type.MARKED, element);
	}

	public static Emphasis marked(Object stringifyable) {
		return emphasis(Type.MARKED, stringifyable);
	}

	public static Emphasis strikethrough(SpanElement element) {
		return emphasis(Type.STRIKETHROUGH, element);
	}

	public static Emphasis strikethrough(Object stringifyable) {
		return emphasis(Type.STRIKETHROUGH, stringifyable);
	}

	public static Emphasis superscript(SpanElement element) {
		return emphasis(Type.SUPERSCRIPT, element);
	}

	public static Emphasis superscript(Object stringifyable) {
		return emphasis(Type.SUPERSCRIPT, stringifyable);
	}

	public static Emphasis subscript(SpanElement element) {
		return emphasis(Type.SUBSCRIPT, element);
	}

	public static Emphasis subscript(Object stringifyable) {
		return emphasis(Type.SUBSCRIPT, stringifyable);
	}

	// Code

	public static Code code(Object stringifyable) {
		return new Code(stringifyable);
	}

	public static CodeBlock codeBlock(Object stringifyable) {
		return new CodeBlock(stringifyable);
	}

	public static CodeBlock codeBlock(Object stringifyable, CodeBlockLanguage language) {
		return new CodeBlock(stringifyable, language);
	}

	// Hyperlink

	public static HyperLink link(String url, SpanElement element) {
		return new HyperLink(url, element);
	}

	public static HyperLink link(String url, Object stringifyable) {
		return link(url, txt(stringifyable));
	}

	public static HyperLink link(String url) {
		return new HyperLink(url);
	}

	// Table

	public static TableCell cell(SpanElement stringifyable) {
		return new TableCell(stringifyable);
	}

	public static TableCell cell(Object stringifyable) {
		return cell(txt(stringifyable));
	}

	public static TableRow row(TableCell... cells) {
		return new TableRowBuilder<>().append(cells).build();
	}

	public static TableRow row(Object... stringifyables) {
		return new TableRowBuilder<>().append(MD::cell, stringifyables).build();
	}

	// List item

	// Unordered list item

	public static UnorderedListItemBuilder<Object> startUnorderedListItem(Paragraph title) {
		return new UnorderedListItemBuilder<>(title);
	}

	public static UnorderedListItemBuilder<Object> startUnorderedListItem(Object title) {
		return startUnorderedListItem(paragraph(title));
	}

	public static ListItem unorderedListItem(Paragraph title) {
		return startUnorderedListItem(title).build();
	}

	public static ListItem unorderedListItem(Object title) {
		return startUnorderedListItem(paragraph(title)).build();
	}

	// Ordered list item

	public static OrderedListItemBuilder<Object> startOrderedListItem(Paragraph title) {
		return new OrderedListItemBuilder<>(title);
	}

	public static OrderedListItemBuilder<Object> startOrderedListItem(Object title) {
		return startOrderedListItem(paragraph(title));
	}

	public static ListItem orderedListItem(Paragraph title) {
		return startOrderedListItem(title).build();
	}

	public static ListItem orderedListItem(Object title) {
		return startOrderedListItem(paragraph(title)).build();
	}

	// Task list item

	public static TaskListItemBuilder<Object> startTaskListItem(Paragraph title, boolean completed) {
		return new TaskListItemBuilder<>(title).completed(completed);
	}

	public static TaskListItemBuilder<Object> startTaskListItem(Object title, boolean completed) {
		return startTaskListItem(paragraph(title), completed);
	}

	public static ListItem taskListItem(Paragraph title, boolean completed) {
		return startTaskListItem(title, completed).build();
	}

	public static ListItem taskListItem(Object title, boolean completed) {
		return startTaskListItem(paragraph(title), completed).build();
	}

	// Admonition

	public static Title title(Object title) {
		return new Title(title);
	}

	public static AdmonitionBuilder<Object> startAdmonition(Title title) {
		return new AdmonitionBuilder<>(title);
	}

	public static Admonition admonition(Title title, BlockElement notice) {
		return startAdmonition(title).append(notice).build();
	}

	public static Admonition admonition(Object title, Object notice) {
		return admonition(title(title), paragraph(notice));
	}

	// Horizontal rule

	public static HorizontalRule hr() {
		return new HorizontalRule();
	}

}
