package de.relimit.commons.markdown.blockelement;

import static de.relimit.commons.markdown.util.MD.row;

import java.util.Collection;
import java.util.function.Function;

import de.relimit.commons.markdown.blockelement.admonition.AdmonitionBuilder;
import de.relimit.commons.markdown.blockelement.admonition.Title;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.heading.Heading;
import de.relimit.commons.markdown.blockelement.image.Image;
import de.relimit.commons.markdown.blockelement.list.OrderedList;
import de.relimit.commons.markdown.blockelement.list.OrderedListBuilder;
import de.relimit.commons.markdown.blockelement.list.TaskList;
import de.relimit.commons.markdown.blockelement.list.TaskListBuilder;
import de.relimit.commons.markdown.blockelement.list.UnorderedList;
import de.relimit.commons.markdown.blockelement.list.UnorderedListBuilder;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.quotes.Blockquotes;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRule;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRuleCharacter;
import de.relimit.commons.markdown.blockelement.table.TableBuilder;
import de.relimit.commons.markdown.blockelement.table.TableRow;
import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;
import de.relimit.commons.markdown.span.SpanElementNodeBuilder;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 * @param <B>
 *            The builder itself for method chaining
 * @param <BE>
 *            The element that is built by this builder
 */
public abstract class BlockElementNodeBuilder<P, B extends BlockElementNodeBuilder<P, B, BE>, BE extends BlockElementNode>
		extends NodeBuilder<P, B, BE, BlockElement> {

	// Default false
	private boolean quoted;

	protected BlockElementNodeBuilder(BE element) {
		super(element);
	}

	protected BlockElementNodeBuilder(BE element, MarkdownSerializableAppender<P, BE> parentAppender) {
		super(element, parentAppender);
	}

	/**
	 * Every {@link BlockElement} added after this call is wrapped in a
	 * {@link Blockquotes} element.
	 * 
	 * @return
	 */
	public B quote() {
		this.quoted = true;
		return getBuilder();
	}

	/**
	 * Every {@link BlockElement} added after this call is added without change
	 * (not wrapped in a {@link Blockquotes} element.
	 * 
	 * @return
	 */
	public B unquote() {
		this.quoted = false;
		return getBuilder();
	}

	@Override
	protected BlockElement gateKeep(BlockElement element) {
		if (quoted) {
			return new Blockquotes(element);
		} else {
			return element;
		}

	}

	// Lists

	public OrderedListBuilder<B> startOrderedList() {
		return new OrderedListBuilder<>(new OrderedList(), this::append);
	}

	public UnorderedListBuilder<B> startUnorderedList() {
		return new UnorderedListBuilder<>(new UnorderedList(), this::append);
	}

	public TaskListBuilder<B> startTaskList() {
		return new TaskListBuilder<>(new TaskList(), this::append);
	}

	// Code block

	public B codeBlock(Object stringifyable) {
		return append(new CodeBlock(stringifyable));
	}

	public B codeBlock(Object stringifyable, CodeBlockLanguage language) {
		return append(new CodeBlock(stringifyable, language));
	}

	// Heading

	public B heading(Object stringifyable) {
		return startHeading().plainText(stringifyable).end();
	}

	public B heading(int level, Object stringifyable) {
		return startHeading(level).plainText(stringifyable).end();
	}

	public SpanElementNodeBuilder<B, Heading> startHeading() {
		return new SpanElementNodeBuilder<>(new Heading(), this::append);
	}

	public SpanElementNodeBuilder<B, Heading> startHeading(int level) {
		return new SpanElementNodeBuilder<>(new Heading(level), this::append);
	}

	// Horizontal rule

	public B horizontalRule() {
		return append(new HorizontalRule());
	}

	public B horizontalRule(HorizontalRuleCharacter character) {
		return append(new HorizontalRule(character));
	}

	// Image

	public B image(String url) {
		return append(new Image(url));
	}

	public B image(String url, Object stringifyable) {
		return append(new Image(url, stringifyable));
	}

	// Paragraph

	public B paragraph(Object stringifyable) {
		return startParagraph().plainText(stringifyable).end();
	}

	public SpanElementNodeBuilder<B, Paragraph> startParagraph() {
		return new SpanElementNodeBuilder<>(new Paragraph(), this::append);
	}

	// Table

	public TableBuilder<B> startTable() {
		return new TableBuilder<>(this::append);
	}

	public <T> B table(Function<T, TableRow> converter, Collection<T> rows) {
		return table(converter, rows, (Object[]) null);
	}

	public <T, H> B table(Function<T, TableRow> converter, Collection<T> elements,
			Object... stringifyableColumnHeaders) {
		final TableBuilder<B> tbb = startTable();
		if (stringifyableColumnHeaders != null && stringifyableColumnHeaders.length > 0) {
			// Header column
			tbb.append(row(stringifyableColumnHeaders));
		} else {
			tbb.firstRowisHeader(false);
		}
		tbb.append(converter, elements);
		return tbb.end();
	}

	// Admonition

	public AdmonitionBuilder<B> startAdmonition(Title title) {
		return new AdmonitionBuilder<>(title, this::append);
	}

	public AdmonitionBuilder<B> startAdmonition() {
		return startAdmonition(Title.DEFAULT_TITLE);
	}

}
