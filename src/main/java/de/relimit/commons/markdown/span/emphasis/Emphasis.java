package de.relimit.commons.markdown.span.emphasis;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;
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

	private static Fences fromType(EmphasisType type) {
		Args.notNull(type, "Type");
		final String marker = Args.notNull(type.getMarker(), "Marker");
		return Fences.between(marker, marker);
	}

	public Emphasis(EmphasisType type, SpanElement... elements) {
		super(elements);
		this.fences = fromType(type);
	}

	public Emphasis(EmphasisType type, Object... elements) {
		super(elements);
		this.fences = fromType(type);
	}

	@Override
	public Fences getFences(MarkdownSerializationOptions options) {
		return fences;
	}

}
