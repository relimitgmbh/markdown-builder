package de.relimit.commons.markdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import de.relimit.commons.markdown.blockelement.table.Table;
import de.relimit.commons.markdown.blockelement.table.TableRow;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;

/**
 * Something that represents a markdown node. A markdown node contains a list of
 * markdown elements. In other words nodes provide a means to create a
 * hierarchy. Sometimes this is a hierarchy in terms of the document structure.
 * E.g. can {@link SpanElement}s be nested using a {@link SpanElementNode}
 * (which is in turn a {@link SpanElement}). But sometimes it is an internal
 * structural tool. E.g. are {@link Table}s nodes that take {@link TableRow}s,
 * but this is not defined by markdown.
 *
 * @param <T>
 *            The type of element that can be
 *            {@link #append(MarkdownSerializable)}ed to this node.
 */
public abstract class Node<T extends MarkdownSerializable> extends MarkdownElement {

	protected List<T> elements = new ArrayList<>();

	protected T gateKeep(T element) {
		return element;
	}

	public final void append(Stream<T> elementStream) {
		elementStream.map(this::gateKeep).filter(Objects::nonNull).forEach(elements::add);
		invalidateSerialized();
	}

	public final void append(Collection<T> elements) {
		append(elements.stream());
	}

	public final void append(T... elements) {
		append(Arrays.stream(elements));
	}

	public final void appendAll(Node<T> node) {
		append(node.getElements().stream());
	}

	public List<T> getElements() {
		return elements;
	}

	public int size() {
		return elements.size();
	}

}
