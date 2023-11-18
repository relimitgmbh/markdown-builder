package de.relimit.commons.markdown.blockelement.image;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.AbstractHyperLink;

public class Image extends AbstractHyperLink implements BlockElement {

	public Image(String url, Object stringyfiable) {
		super(url, stringyfiable);
	}

	public Image(String url) {
		this(url, url);
	}

	@Override
	protected String serializeLink(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		return "!" + super.serializeLink(options);
	}

}
