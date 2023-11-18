package de.relimit.commons.markdown.span.textual;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class PlainText extends Textual {

	public PlainText(Object stringyfiable) {
		super(stringyfiable);
	}

	@Override
	protected String getPredecessor() {
		return "";
	}

	@Override
	protected String getSuccessor() {
		return "";
	}

	@Override
	protected String serializeText(MarkdownSerializationOptions options, Object stringyfiable)
			throws MarkdownSerializationException {
		// TODO: Escape for markdown
		return options.getPlainTextSerializer().serialize(this, stringyfiable);
	}

}
