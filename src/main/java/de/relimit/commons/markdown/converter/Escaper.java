package de.relimit.commons.markdown.converter;

import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.span.textual.Textual;

@FunctionalInterface
public interface Escaper {

	/**
	 * Does not escape any text put into the builder. In other words: Text put
	 * into markdown builder will end up in the resulting markdown as-is.
	 */
	public static final Escaper ALLOW_MARKDOWN = (element, stringifyable, stringified) -> stringified;

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
	public static final Escaper ESCAPE_MARKDOWN = (element, stringifyable, stringified) -> {
		final String escapeChars = element.getEscapeCharacters();
		for (int i = 0; i < escapeChars.length(); i++) {
			final char c = escapeChars.charAt(i);
			stringified = stringified.replace("" + c, "\\" + c);
		}
		return stringified;
	};

	/**
	 * This method is expected to escape the text input and return it so it is
	 * safe for use within markdown. The text this method receives is not
	 * supposed to be markdown but any text that is supposed to appear exactly
	 * as it is. Meaning that every character (be it an asterisk or any other
	 * markdown special character) is supposed to show in the rendered markdown
	 * as itself.
	 * <p>
	 * The {@link Textual} the call originates from is included. It can be used
	 * to distinguish between e.g. a {@link CodeBlock} and a {@link PlainText}
	 * element. {@link Textual#getEscapeCharacters()} supplies a list of
	 * characters the {@link Textual} considers to be candidates for escaping.
	 * <p>
	 * The stringifyable Object is also supplied to be symmetrical with
	 * {@link Stringifier}. Custom escapers may decide to escape (or not
	 * escape) text based on the type of stringifyable Object the text
	 * originates from. The {@link ConfigurableEscaper} is built to help do just
	 * that.
	 * <p>
	 * The method should never return <code>null</code> but an empty String.
	 * 
	 * @param e
	 * @param stringifyable
	 * @param stringified
	 * @return
	 */
	String escape(Textual e, Object stringifyable, String stringified);

}
