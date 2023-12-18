package de.relimit.commons.markdown.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

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

	protected NE gateKeep(NE element) {
		return element;
	}

	public final B append(Stream<NE> elements) {
		elements = elements.map(this::gateKeep).filter(Objects::nonNull);
		getElement().append(elements);
		return getBuilder();
	}

	@Override
	public final B append(NE element) {
		return append(Stream.of(element));
	}

	public final B append(NE... elements) {
		return append(Arrays.stream(elements));
	}

	public final B append(Collection<NE> elements) {
		return append(elements.stream());
	}

	public final B appendAll(BE node) {
		return append(node.getElements().stream());
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
		return append(Arrays.stream(elements).map(e -> converter.apply(e)));
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
		return append(elements.stream().map(e -> converter.apply(e)));
	}

}
