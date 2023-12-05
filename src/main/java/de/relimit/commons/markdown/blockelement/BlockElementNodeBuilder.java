package de.relimit.commons.markdown.blockelement;

import de.relimit.commons.markdown.Node;
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
import de.relimit.commons.markdown.builder.MarkdownElementAppender;
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

	protected BlockElementNodeBuilder(BE element, MarkdownElementAppender<P, BE> parentAppender) {
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
	public B append(BlockElement element) {
		if (quoted) {
			return super.append(new Blockquotes(element));
		} else {
			return super.append(element);
		}
	}

	public B appendAll(Node<BlockElement> node) {
		node.getElements().forEach(this::append);
		return getBuilder();
	}

	// Lists

	public OrderedListBuilder<B> startOrderedList() {
		final int indentationLevel = getElement().getIndentationLevel();
		final OrderedList list = new OrderedList(indentationLevel);
		return new OrderedListBuilder<>(list, this::append);
	}

	public UnorderedListBuilder<B> startUnorderedList() {
		final int indentationLevel = getElement().getIndentationLevel();
		final UnorderedList list = new UnorderedList(indentationLevel);
		return new UnorderedListBuilder<>(list, this::append);
	}

	public TaskListBuilder<B> startTaskList() {
		final int indentationLevel = getElement().getIndentationLevel();
		final TaskList list = new TaskList(indentationLevel);
		return new TaskListBuilder<>(list, this::append);
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

}
