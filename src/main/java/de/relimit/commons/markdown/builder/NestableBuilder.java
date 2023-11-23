package de.relimit.commons.markdown.builder;

import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptionsImpl;

/**
 * <p>
 * This builder can functions both as a standalone builder and a nested builder.
 * To keep generics simple the builder does not know anything about the parent
 * builder except that it exists as a generic parameter.
 * <p>
 * If this is supposed to be a stand alone builder, the user is expected to call
 * the method {@link #build()} to retrieve the product. If it is a nested
 * builder, they are expected to call {@link #end()}. Since we cannot
 * (practically) hide either method depending on the scenario the methods are
 * designed to always do something meaningful.
 * <p>
 * In the stand alone scenario {@link #end()} behaves like {@link #build()}. In
 * the nested scenario, calling {@link #build()} does not make much sense but
 * just like in a stand alone mode the product is returned.
 * <p>
 * In order for {@link #end()} to work in a nested scenario it is required to
 * set a {@link MarkdownElementAppender}. This is the price we pay for the
 * simplified generics: The builder is agnostic in terms of the parent and
 * relies on the appender to append the product to the parent builder and then
 * return the parent to continue method chaining at the parent builder level.
 * <p>
 * If no appender is set the builder assumes that the parent and itself are
 * identical. In other words: It is a stand alone builder.
 *
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 * @param <B>
 *            The builder itself for method chaining
 * @param <BE>
 *            The element that is built by this builder
 */
public abstract class NestableBuilder<P, B extends NestableBuilder<P, B, BE>, BE extends MarkdownSerializable> {

	private MarkdownElementAppender<P, BE> parent;

	private BE element;

	/**
	 * Use this to run the builder in stand alone mode.
	 * 
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	public NestableBuilder(BE element) {
		/*
		 * Stand alone scenario: Assume this builder and the parent are
		 * identical. Set a parent appender that does nothing and just returns
		 * this builder.
		 */
		this.parent = e -> ((P) this);
		this.element = element;
	}

	/**
	 * Use this to run the builder in nested mode.
	 * 
	 * @param element
	 * @param parentAppender
	 */
	public NestableBuilder(BE element, MarkdownElementAppender<P, BE> parent) {
		this.parent = parent;
		this.element = element;
	}

	/**
	 * For method chaining.
	 * 
	 * @return
	 */
	abstract protected B getBuilder();

	protected BE getElement() {
		return element;
	}

	public BE build() {
		return element;
	}

	public P end() {
		final BE product = build();
		return parent.append(product);
	}

	@Override
	public String toString() {
		return element.getSerialized(MarkdownSerializationOptionsImpl.DEFAULT_OPTIONS, this.getClass().getSimpleName());
	}

}
