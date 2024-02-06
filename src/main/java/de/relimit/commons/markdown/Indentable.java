package de.relimit.commons.markdown;

/**
 * Something that potentially adds an extra indentation to the indentation
 * already added by the parent element. It is important to note that this is 0
 * or 1 most of the time. If the indentation is 0, then the element does not add
 * any extra indentation. This does not necessarily mean that it is not
 * indented. A parent element may already add indentation. If the indentation is
 * 1 then this element adds 1 level of indentation. This means that all its
 * child elements are indented one unit more than its parent element.
 */
public interface Indentable {

	public int getIndentationLevel();

	public void setIndentationLevel(int indentationLevel);

	default void incrementIndentationLevel() {
		setIndentationLevel(getIndentationLevel() + 1);
	}

}
