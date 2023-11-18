package de.relimit.commons.markdown;

import java.util.ArrayList;
import java.util.List;

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

	public void append(T element) {
		elements.add(element);
		invalidateSerialized();
	}

	public int size() {
		return elements.size();
	}

}
