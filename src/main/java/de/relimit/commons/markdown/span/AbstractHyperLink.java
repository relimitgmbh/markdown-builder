package de.relimit.commons.markdown.span;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.span.textual.Textual;

public abstract class AbstractHyperLink extends MarkdownElement implements Textual {

	private String url;

	private Object stringifyable;

	public AbstractHyperLink(String url, Object stringifyable) {
		this.url = url;
		this.stringifyable = stringifyable;
	}

	public AbstractHyperLink(String url) {
		this(url, url);
	}

	@Override
	public Object getStringifyable() {
		return stringifyable;
	}

	@Override
	public String getEscapeCharacters() {
		return PlainText.ESCAPE_CHARS;
	}

	protected String getUrl() {
		return url;
	}

	protected String serializeLink(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final StringBuilder sb = new StringBuilder();
		final String text = options.stringify(this);
		sb.append("[").append(text).append("]");
		// For now always assume the URL is well-formed.
		sb.append("(").append(url).append(")");
		return sb.toString();
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		lines.add(serializeLink(options));
		return lines;
	}

}
