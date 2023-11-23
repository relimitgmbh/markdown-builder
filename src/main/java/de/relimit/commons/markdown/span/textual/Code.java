package de.relimit.commons.markdown.span.textual;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.configuration.OptionsBuilder;

public class Code extends Text {

	public static final Fences SAFE_FENCES = Fences.between("``` ", " ```");

	public static final Fences COMPACT_FENCES = Fences.between("`", "`");

	/**
	 * A single backtick (without separating spaces) at the beginning and the
	 * end of in-line code would suffice in many cases. However this library is
	 * playing it safe by default. Using three backticks as fences is safer
	 * because it allows for single backticks to be present anywhere in the
	 * fenced code without breaking the markdown. Escaping backticks with
	 * backslashes does not work. The extra space between the leading and
	 * trailing backticks allows for the code to begin or end with a backtick.
	 * Or - as a very special case scenario - consist of only one single
	 * backtick character.
	 * <p>
	 * To change this to compact mode, use
	 * {@link OptionsBuilder#codeFences(Fences)} and set
	 * {@value #COMPACT_FENCES}.
	 */
	public static final Fences DEFAULT_FENCES = SAFE_FENCES;

	public Code(Object stringifyable) {
		super(stringifyable);
	}

	@Override
	protected Fences getFences(MarkdownSerializationOptions options) {
		return options.getCodeFences();
	}

	/**
	 * @see #getPredecessor() on why there is no attempt at escaping code.
	 */
	@Override
	public String getEscapeCharacters() {
		return "";
	}

}
