package de.relimit.commons.markdown;

@SuppressWarnings("serial")
public class MarkdownSerializationException extends Exception {

	public MarkdownSerializationException() {
	}

	public MarkdownSerializationException(String s) {
		super(s);
	}

	public MarkdownSerializationException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public MarkdownSerializationException(Throwable throwable) {
		super(throwable);
	}

}
