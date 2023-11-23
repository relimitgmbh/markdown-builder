package de.relimit.commons.markdown.span;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;
import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.PlainText;

/**
 * 
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 * @param <BE>
 *            The element that is built by this builder
 */
public class SpanElementNodeBuilder<P, BE extends SpanElementNode>
		extends NodeBuilder<P, SpanElementNodeBuilder<P, BE>, BE, SpanElement> {

	public SpanElementNodeBuilder(BE element, MarkdownElementAppender<P, BE> parent) {
		super(element, parent);
	}

	public SpanElementNodeBuilder(BE element) {
		super(element);
	}

	@Override
	protected SpanElementNodeBuilder<P, BE> getBuilder() {
		return this;
	}

	// Emphasis

	public SpanElementNodeBuilder<SpanElementNodeBuilder<P, BE>, Emphasis> startEmphasis(Type type) {
		return new SpanElementNodeBuilder<>(new Emphasis(type), this::append);
	}

	public SpanElementNodeBuilder<P, BE> emphasis(Type type, Object stringifyable) {
		return startEmphasis(type).plainText(stringifyable).end();
	}

	// Hyperlink

	public SpanElementNodeBuilder<P, BE> hyperlink(String url) {
		return append(new HyperLink(url));
	}

	public SpanElementNodeBuilder<P, BE> hyperlink(String url, Object stringifyable) {
		return append(new HyperLink(url, stringifyable));
	}

	// Code

	public SpanElementNodeBuilder<P, BE> code(Object stringifyable) {
		return append(new Code(stringifyable));
	}

	public SpanElementNodeBuilder<P, BE> className(Class<?> clazz) {
		return code(clazz.getName());
	}

	public SpanElementNodeBuilder<P, BE> simpleClassName(Class<?> clazz) {
		return code(clazz.getSimpleName());
	}

	// Plain text

	public SpanElementNodeBuilder<P, BE> plainText(Object stringifyable) {
		return append(new PlainText(stringifyable));
	}

	public SpanElementNodeBuilder<P, BE> newLine() {
		return append(new PlainText(System.lineSeparator()));
	}

}
