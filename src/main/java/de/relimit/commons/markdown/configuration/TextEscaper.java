package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.span.textual.Text;
import de.relimit.commons.markdown.span.textual.Textual;

@FunctionalInterface
public interface TextEscaper {

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
