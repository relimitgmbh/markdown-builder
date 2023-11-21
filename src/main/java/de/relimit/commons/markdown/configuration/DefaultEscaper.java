package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.span.textual.Textual;

public class DefaultEscaper implements TextEscaper {

	public static final TextEscaper DEFAULT_ESCAPER = new DefaultEscaper();

	private DefaultEscaper() {
		// singleton
	}

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
