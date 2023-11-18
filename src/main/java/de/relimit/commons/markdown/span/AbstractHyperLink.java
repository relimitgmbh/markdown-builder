package de.relimit.commons.markdown.span;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class AbstractHyperLink extends MarkdownElement {

	private String url;

	private Object stringyfiable;

	public AbstractHyperLink(String url, Object stringyfiable) {
		this.url = url;
		this.stringyfiable = stringyfiable;
	}

	public AbstractHyperLink(String url) {
		this(url, url);
	}

	protected Object getStringyfiable() {
		return stringyfiable;
	}

	protected String getUrl() {
		return url;
	}

	protected String serializeLink(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final StringBuilder sb = new StringBuilder();
		// TODO Encoding?
		sb.append("[").append(options.getPlainTextSerializer().serialize(this, stringyfiable)).append("]");
		sb.append("(").append(url).append(")");
		return sb.toString();
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		lines.add(serializeLink(null));
		return lines;
	}

}
