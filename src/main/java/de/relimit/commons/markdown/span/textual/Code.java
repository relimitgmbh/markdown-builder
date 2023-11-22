package de.relimit.commons.markdown.span.textual;

public class Code extends Text {

	/**
	 * Using three backticks as fences is safer because it allows for single
	 * backticks to be present anywhere in the fenced code without breaking the
	 * markdown. Escaping backticks with backslashes does not work. The extra
	 * space between the leading and trailing backticks allows for the code to
	 * begin or end with a backtick. Or - as a very special case scenario -
	 * consist of only one single backtick character.
	 */
	public static final Fences DEFAULT_FENCES = Fences.of("``` ", " ```");

	public Code(Object stringifyable) {
		super(stringifyable);
	}

	/**
	 * @see #getPredecessor() on why there is no attempt at escaping code.
	 */
	@Override
	public String getEscapeCharacters() {
		return "";
	}

}
