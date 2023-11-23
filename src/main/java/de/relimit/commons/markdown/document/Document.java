package de.relimit.commons.markdown.document;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.BlockElementNode;

/**
 * The root node of a markdown tree. Since a document is also a
 * {@link BlockElement}, documents can be added to documents.
 */
public class Document extends BlockElementNode implements BlockElement {

	protected Document() {
		// Document node is at the top level
		super(0);
	}

	public static DocumentBuilder start() {
		return new DocumentBuilder();
	}

}
