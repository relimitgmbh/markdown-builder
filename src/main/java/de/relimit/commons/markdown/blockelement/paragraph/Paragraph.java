package de.relimit.commons.markdown.blockelement.paragraph;

import java.util.List;
import java.util.Optional;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;

public class Paragraph extends SpanElementNode implements BlockElement {

	public Paragraph() {
	}

	public Paragraph(SpanElement... elements) {
		super(elements);
	}

	public Paragraph(String text) {
		super(text);
	}

	@Override
	protected Optional<String> getPredecessor(List<String> lines) {
		return Optional.empty();
	}

	@Override
	protected Optional<String> getSuccessor(List<String> lines) {
		return Optional.empty();
	}

}
