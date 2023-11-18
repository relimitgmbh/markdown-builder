package de.relimit.commons.markdown.span.textual;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class Code extends Textual {

	public Code(Object stringyfiable) {
		super(stringyfiable);
	}

	@Override
	public String getPredecessor() {
		return "`";
	}

	@Override
	public String getSuccessor() {
		return getPredecessor();
	}

	@Override
	protected String serializeText(MarkdownSerializationOptions options, Object stringyfiable)
			throws MarkdownSerializationException {
		// TODO Escape for code?
		return options.getPlainTextSerializer().serialize(this, stringyfiable);
	}

}
