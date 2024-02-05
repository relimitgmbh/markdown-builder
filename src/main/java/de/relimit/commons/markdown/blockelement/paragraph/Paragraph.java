package de.relimit.commons.markdown.blockelement.paragraph;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;

public class Paragraph extends SpanElementNode implements BlockElement {

	public Paragraph() {
	}

	public Paragraph(Object stringifyable) {
		super(stringifyable);
	}

	public Paragraph(SpanElement... elements) {
		super(elements);
	}

	public Paragraph(SpanElement element) {
		super(element);
	}

	public Paragraph(String text) {
		super(text);
	}

	@Override
	public Fences getFences(MarkdownSerializationOptions options) {
		return Fences.none();
	}

}
