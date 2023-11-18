package de.relimit.commons.markdown.document;

import de.relimit.commons.markdown.blockelement.BlockElementNodeBuilder;

public class DocumentBuilder extends BlockElementNodeBuilder<DocumentBuilder, DocumentBuilder, Document> {

	public DocumentBuilder() {
		super(new Document());
	}

	@Override
	protected DocumentBuilder getBuilder() {
		return this;
	}

}
