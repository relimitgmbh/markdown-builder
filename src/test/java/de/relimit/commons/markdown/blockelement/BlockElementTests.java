package de.relimit.commons.markdown.blockelement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.admonition.Admonition;
import de.relimit.commons.markdown.blockelement.admonition.Display;
import de.relimit.commons.markdown.blockelement.admonition.Expansion;
import de.relimit.commons.markdown.blockelement.heading.Heading;
import de.relimit.commons.markdown.blockelement.heading.HeadingStyle;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.document.Document;
import de.relimit.commons.markdown.span.textual.PlainText;

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

	private static final String[] ADMONITION = {
			"???+ note inline end \"Foo\"",
			System.lineSeparator() + "    ",
			System.lineSeparator() + "    Foo",
			System.lineSeparator() + "    ",
			System.lineSeparator() + "    Bar"
	};

	@Test
	void instantiateAdmonition_generateMarkdown_expectWellFormed() throws MarkdownSerializationException {
		final Admonition admonition = Document.start().startAdmonition().expansion(Expansion.EXPANDED)
				.display(Display.INLINE_RIGHT).startTitle().append(new PlainText("Foo")).end()
				.append(new Paragraph("Foo")).append(new Paragraph("Bar")).build();
		assertEquals(Arrays.stream(ADMONITION).collect(Collectors.joining()), admonition.serialize());
	}

}
