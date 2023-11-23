package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.span.textual.Textual;

public class DefaultEscaper implements TextEscaper {

	public static final TextEscaper DEFAULT_ESCAPER = new DefaultEscaper();

	private DefaultEscaper() {
		// singleton
	}

	/**
	 * Escaping in markdown is done by putting a backslash as an escape
	 * character in front of the character that needs escaping. Consequently a
	 * backslash turns into two backslashes.
	 * <p>
	 * The number of character to escape may vary from element to element. So
	 * the method asks the element for a String consisting of all characters
	 * deemed dangerous by the element.
	 * <p>
	 * Not all special characters need to be escaped all the time. For example
	 * an asterisk surrounded by spaces is perfectly fine but directly adjacent
	 * to another character is might prematurely start or end a surrounding
	 * emphasis. Similarly, a full stop adjacent to a letter ends a sentence and
	 * is fine. A dot behind a number at the beginning of a line might start a
	 * numbered list. Trying to detect these special cases is a joyless effort.
	 * Therefore this default escaper takes the easy way out and just escapes
	 * everything at the cost of the output sometimes looking not as neat as it
	 * could be.
	 */
	@Override
	public String escape(Textual e, String text) {
		final String escapeChars = e.getEscapeCharacters();
		for (int i = 0; i < escapeChars.length(); i++) {
			final char c = escapeChars.charAt(i);
			text = text.replace("" + c, "\\" + c);
		}
		return text;
	}

}
