package de.relimit.commons.markdown.span.textual;

import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class PlainText extends Text {

	public static final String ESCAPE_CHARS = "\\`*_{}[]()#+-.!";

	public PlainText(Object stringifyable) {
		super(stringifyable);
	}

	@Override
	protected Fences getFences(MarkdownSerializationOptions options) {
		return Fences.none();
	}

	@Override
	public String getEscapeCharacters() {
		return ESCAPE_CHARS;
	}

}
