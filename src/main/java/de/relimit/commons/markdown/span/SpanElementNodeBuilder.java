package de.relimit.commons.markdown.span;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;
import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.PlainText;

public class SpanElementNodeBuilder<P, E extends SpanElementNode>
		extends NodeBuilder<SpanElementNodeBuilder<P, E>, P, E, SpanElement> {

	public SpanElementNodeBuilder(E element, MarkdownElementAppender<P, E> parent) {
		super(element, parent);
	}

	public SpanElementNodeBuilder(E element) {
		super(element);
	}

	@Override
	protected SpanElementNodeBuilder<P, E> getBuilder() {
		return this;
	}

	// Emphasis

	public SpanElementNodeBuilder<SpanElementNodeBuilder<P, E>, Emphasis> startEmphasis(Type type) {
		return new SpanElementNodeBuilder<>(new Emphasis(type), this::append);
	}

	public SpanElementNodeBuilder<P, E> emphasis(Type type, Object stringifyable) {
		return startEmphasis(type).plainText(stringifyable).end();
	}

	// Hyperlink

	public SpanElementNodeBuilder<P, E> hyperlink(String url) {
		return append(new HyperLink(url));
	}

	public SpanElementNodeBuilder<P, E> hyperlink(String url, Object stringifyable) {
		return append(new HyperLink(url, stringifyable));
	}

	// Code

	public SpanElementNodeBuilder<P, E> code(Object stringifyable) {
		return append(new Code(stringifyable));
	}

	public SpanElementNodeBuilder<P, E> className(Class<?> clazz) {
		return code(clazz.getName());
	}

	public SpanElementNodeBuilder<P, E> simpleClassName(Class<?> clazz) {
		return code(clazz.getSimpleName());
	}

	// Plain text

	public SpanElementNodeBuilder<P, E> plainText(Object stringifyable) {
		return append(new PlainText(stringifyable));
	}

	public SpanElementNodeBuilder<P, E> newLine() {
		return append(new PlainText(System.lineSeparator()));
	}

}
