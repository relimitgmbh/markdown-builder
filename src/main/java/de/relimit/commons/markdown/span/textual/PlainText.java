package de.relimit.commons.markdown.span.textual;

public class PlainText extends Text {

	public static final String ESCAPE_CHARS = "\\`*_{}[]()#+-.!";

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
	public String getEscapeCharacters() {
		return ESCAPE_CHARS;
	}

}
