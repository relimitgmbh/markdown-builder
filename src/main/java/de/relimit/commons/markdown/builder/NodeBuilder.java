package de.relimit.commons.markdown.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.blockelement.BlockElementNode;
import de.relimit.commons.markdown.blockelement.table.Table;
import de.relimit.commons.markdown.span.SpanElementNode;

/**
 * A builder that builds something that has children. Note that the element this
 * builder builds does not necessarily have to be a markdown node. For example a
 * {@link Table} is not a markdown node in the sense that it can directly take
 * other markdown elements as children like a {@link BlockElementNode} or a
 * {@link SpanElementNode} can.
 * 
 * @param <P>
 *            The parent builder to continue building on the parent after
 *            {@link #end()}
 * @param <B>
 *            The builder itself for method chaining
 * @param <BE>
 *            The element that is built by this builder (a node)
 * @param <NE>
 *            The child element that is added to the node
 */
public abstract class NodeBuilder<P, B extends NodeBuilder<P, B, BE, NE>, BE extends Node<NE>, NE extends MarkdownSerializable>
		extends NestableBuilder<P, B, BE> implements MarkdownSerializableAppender<B, NE> {

	protected NodeBuilder(BE element, MarkdownSerializableAppender<P, BE> parent) {
		super(element, parent);
	}

	protected NodeBuilder(BE element) {
		super(element);
	}

	@Override
	public B append(NE element) {
		getElement().append(element);
		return getBuilder();
	}

	/*
	 * The append methods that take more than one element invoke the
	 * single-element append method inherited from MarkdownElementAppender in a
	 * loop. This may not be the cheapest or most elegant approach. But child
	 * classes may have the need to augment every element added. By always
	 * invoking the single-element append method we make sure the logic applies
	 * to all elements added. For the same reason the following methods are all
	 * final.
	 */

	public final B append(NE... elements) {
		Arrays.stream(elements).forEach(this::append);
		return getBuilder();
	}

	public final B append(Collection<NE> elements) {
		elements.forEach(this::append);
		return getBuilder();
	}

	/**
	 * Using a nested builder in a loop can be cumbersome. This method allows
	 * for conveniently adding more than one "foreign" objects by taking a
	 * converter {@link Function} as the first argument. Every element is passed
	 * through the converter and the result of the conversion is then appended.
	 * 
	 * @param <T>
	 *            The "foreign" element type to be converted to the native
	 *            element type this node expects
	 * @param converter
	 *            The converter function converting the foreign elements
	 * @param elements
	 *            The elements to be converted
	 * @return The builder for method chaining
	 */
	public final <T> B append(Function<T, NE> converter, T... elements) {
		Arrays.stream(elements).map(e -> converter.apply(e)).forEach(this::append);
		return getBuilder();
	}

	/**
	 * Using a nested builder in a loop can be cumbersome. This method allows
	 * for conveniently adding more than one "foreign" objects by taking a
	 * converter {@link Function} as the first argument. Every element is passed
	 * through the converter and the result of the conversion is then appended.
	 * 
	 * @param <T>
	 *            The "foreign" element type to be converted to the native
	 *            element type this node expects
	 * @param converter
	 *            The converter function converting the foreign elements
	 * @param elements
	 *            The elements to be converted
	 * @return The builder for method chaining
	 */
	public final <T> B append(Function<T, NE> converter, Collection<T> elements) {
		elements.stream().map(e -> converter.apply(e)).forEach(this::append);
		return getBuilder();
	}

}
