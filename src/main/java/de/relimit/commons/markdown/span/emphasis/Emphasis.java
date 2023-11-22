package de.relimit.commons.markdown.span.emphasis;

import java.util.List;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;
import de.relimit.commons.markdown.span.textual.Fences;
import de.relimit.commons.markdown.util.Args;

public class Emphasis extends SpanElementNode implements SpanElement {

	public static enum Type implements EmphasisType {

		BOLD("**"),
		INSERTED("++"),
		ITALIC("_"),
		MARKED("=="),
		STRIKETHROUGH("~~"),
		SUBSCRIPT("~"),
		SUPERSCRIPT("^");

		private String marker;

		private Type(String marker) {
			this.marker = marker;
		}

		@Override
		public String getMarker() {
			return marker;
		}

	}

	private Fences fences;

	public Emphasis(EmphasisType type, SpanElement... elements) {
		super(elements);
		Args.notNull(type, "Type");
		final String marker = Args.notNull(type.getMarker(), "Marker");
		this.fences = Fences.of(marker, marker);
	}

	@Override
	protected Fences getFences() {
		return fences;
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		// TODO Escape?
		return super.serializeLines(options);
	}

}
