package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.span.textual.Text;
import de.relimit.commons.markdown.span.textual.Textual;

@FunctionalInterface
public interface TextEscaper {

	/**
	 * Does not escape any text put into the builder. In other words: Text put
	 * into markdown builder will end up in the resulting markdown as-is.
	 */
	public static final TextEscaper ALLOW_MARKDOWN = (e, text) -> text;

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
	public static final TextEscaper ESCAPE_MARKDOWN = (e, text) -> {
		final String escapeChars = e.getEscapeCharacters();
		for (int i = 0; i < escapeChars.length(); i++) {
			final char c = escapeChars.charAt(i);
			text = text.replace("" + c, "\\" + c);
		}
		return text;
	};

	/**
	 * This method is expected to escape the text input and return it so it is
	 * safe for use within markdown. The text this method receives is not
	 * supposed to be markdown but any text that is supposed to appear exactly
	 * as it is. Meaning that every character (be it an asterisk or any other
	 * markdown special character) is supposed to show in the rendered markdown
	 * as itself.
	 * <p>
	 * The actual {@link Textual} (currently always a {@link Text}) the call
	 * originates from is included. This can be used to distinguish e.g. between
	 * {@link Code} and {@link PlainText}.
	 * <p>
	 * The method should never return <code>null</code> but an empty String.
	 * 
	 * @param e
	 * @param text
	 * @return
	 */
	String escape(Textual e, String text);

}
