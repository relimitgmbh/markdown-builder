package de.relimit.commons.markdown.blockelement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.heading.Heading;
import de.relimit.commons.markdown.blockelement.heading.HeadingStyle;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

class BlockElementTests {

	@Test
	void setHeadingStyleViaOptions_generateMarkdown_expectStyleApplied() throws MarkdownSerializationException {
		final MarkdownSerializationOptions options = new MarkdownSerializationOptions();

		// Set ATX style to be used (default is SETEXT)
		options.setDefaultHeadingStyle(HeadingStyle.ATX);

		final Heading testHeading = new Heading("Foo");
		// Expect default style (SETEXT) without overriding options
		assertEquals("Foo" + System.lineSeparator() + "===", testHeading.serialize());
		// Expect options to override SETEXT style with ATX style
		assertEquals("# Foo", testHeading.serialize(options));
	}

}
