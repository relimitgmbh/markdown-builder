package de.relimit.commons.markdown.blockelement;

import de.relimit.commons.markdown.MarkdownElementAppender;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.heading.Heading;
import de.relimit.commons.markdown.blockelement.image.Image;
import de.relimit.commons.markdown.blockelement.list.OrderedList;
import de.relimit.commons.markdown.blockelement.list.OrderedListBuilder;
import de.relimit.commons.markdown.blockelement.list.UnorderedList;
import de.relimit.commons.markdown.blockelement.list.UnorderedListBuilder;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.quotes.Blockquotes;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRule;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRuleCharacter;
import de.relimit.commons.markdown.blockelement.table.Table;
import de.relimit.commons.markdown.blockelement.table.TableBuilder;
import de.relimit.commons.markdown.builder.NodeBuilder;
import de.relimit.commons.markdown.span.SpanElementNodeBuilder;

public abstract class BlockElementNodeBuilder<B extends BlockElementNodeBuilder<B, P, E>, P, E extends BlockElementNode>
		extends NodeBuilder<B, P, E, BlockElement> {

	// Default false
	private boolean quoted;

	protected BlockElementNodeBuilder(E element) {
		super(element);
	}

	protected BlockElementNodeBuilder(E element, MarkdownElementAppender<P, E> parentAppender) {
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

	// Code block

	public B codeBlock(Object stringyfiable) {
		return append(new CodeBlock(stringyfiable));
	}

	public B codeBlock(Object stringyfiable, CodeBlockLanguage language) {
		return append(new CodeBlock(stringyfiable, language));
	}

	// Heading

	public B heading(Object stringyfiable) {
		return startHeading().plainText(stringyfiable).end();
	}

	public B heading(int level, Object stringyfiable) {
		return startHeading(level).plainText(stringyfiable).end();
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
		final Image image = new Image(url);
		return append(image);
	}

	public B image(String url, Object stringyfiable) {
		final Image image = new Image(url, stringyfiable);
		return append(image);
	}

	// Paragraph

	public B paragraph(Object stringyfiable) {
		return startParagraph().plainText(stringyfiable).end();
	}

	public SpanElementNodeBuilder<B, Paragraph> startParagraph() {
		return new SpanElementNodeBuilder<>(new Paragraph(), this::append);
	}

	// Table

	public TableBuilder<B> startTable() {
		final Table table = new Table();
		return new TableBuilder<>(table, this::append);
	}

}
