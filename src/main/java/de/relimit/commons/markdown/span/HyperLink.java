package de.relimit.commons.markdown.span;

import java.net.URL;

public class HyperLink extends AbstractHyperLink implements SpanElement {

	public HyperLink(String url, Object stringifyable) {
		super(url, stringifyable);
	}

	public HyperLink(URL url, Object stringifyable) {
		this(url.toString(), stringifyable);
	}

	public HyperLink(URL url) {
		/*
		 * The second argument is the display text. Add the URL natively (do not
		 * invoke toString here) so a specialized stringifyer will be used
		 * during serialization if set via options. If none is set, then the
		 * default stringifyer will invoke toString.
		 */
		this(url, url);
	}

	public HyperLink(String url) {
		this(url, url);
	}

}
