package de.relimit.commons.markdown.span.emphasis;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;

public class Emphasis extends SpanElementNode implements SpanElement {

	public static enum Type implements EmphasisType {

		BOLD("**"),
		INSERTED("++"),
		ITALIC("*"),
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

	private EmphasisType type;

	public Emphasis(EmphasisType type, SpanElement... elements) {
		super(elements);
		this.type = Objects.requireNonNull(type);
	}

	@Override
	protected Optional<String> getPredecessor(List<String> lines) {
		return Optional.of(type.getMarker());
	}

	@Override
	protected Optional<String> getSuccessor(List<String> lines) {
		return getPredecessor(lines);
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		// TODO Escape style indicators
		return super.serializeLines(options);
	}

}
