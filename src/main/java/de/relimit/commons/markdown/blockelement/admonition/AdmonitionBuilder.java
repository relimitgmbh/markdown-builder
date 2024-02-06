package de.relimit.commons.markdown.blockelement.admonition;

import java.util.Objects;

import de.relimit.commons.markdown.blockelement.BlockElementNodeBuilder;
import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;
import de.relimit.commons.markdown.span.SpanElementNodeBuilder;

/**
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 */
public class AdmonitionBuilder<P> extends BlockElementNodeBuilder<P, AdmonitionBuilder<P>, Admonition> {

	public AdmonitionBuilder() {
		this(Title.NO_TITLE);
	}

	public AdmonitionBuilder(Title title, MarkdownSerializableAppender<P, Admonition> parentAppender) {
		super(new Admonition(), parentAppender);
		getElement().append(title);
	}

	public AdmonitionBuilder(Title title) {
		super(new Admonition());
		getElement().append(title);
	}

	public AdmonitionBuilder<P> title(Title title) {
		Objects.requireNonNull(title);
		/*
		 * This builder always adds a title as the first element. We therefore
		 * know it is already there.
		 */
		getElement().getElements().set(0, title);
		return this;
	}

	public AdmonitionBuilder<P> defaultTitle() {
		return title(Title.DEFAULT_TITLE);
	}

	public AdmonitionBuilder<P> noTitle() {
		return title(Title.NO_TITLE);
	}

	public SpanElementNodeBuilder<AdmonitionBuilder<P>, Title> startTitle() {
		return new SpanElementNodeBuilder<>(new Title(), this::title);
	}

	public AdmonitionBuilder<P> type(Type type) {
		getElement().setType(type);
		return this;
	}

	public AdmonitionBuilder<P> display(Display display) {
		getElement().setDisplay(display);
		return this;
	}

	public AdmonitionBuilder<P> expansion(Expansion expansion) {
		getElement().setExpansion(expansion);
		return this;
	}

	@Override
	protected AdmonitionBuilder<P> getBuilder() {
		return this;
	}

}
