package de.relimit.commons.markdown.span;

public class HyperLink extends AbstractHyperLink implements SpanElement {

	public HyperLink(String url, Object stringifyable) {
		super(url, stringifyable);
	}

	public HyperLink(String url) {
		this(url, url);
	}

}
