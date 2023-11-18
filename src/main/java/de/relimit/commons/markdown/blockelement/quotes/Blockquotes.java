package de.relimit.commons.markdown.blockelement.quotes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class Blockquotes extends MarkdownElement implements BlockElement {

	public static final String QUOTE_INDICATOR = ">";

	private BlockElement quotedElement;

	public Blockquotes(BlockElement quotedElement) {
		this.quotedElement = Objects.requireNonNull(quotedElement);
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		return quotedElement.serializeLines(options).stream().map(s -> QUOTE_INDICATOR + " " + s)
				/*
				 * Calling methods might need to manipulate the list. Make sure
				 * it is an ArrayList.
				 */
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
