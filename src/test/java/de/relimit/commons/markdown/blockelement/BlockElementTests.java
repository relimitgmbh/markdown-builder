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
import de.relimit.commons.markdown.blockelement.rule.HorizontalRule;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRule.RuleCharacter;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.document.Document;
import de.relimit.commons.markdown.span.textual.PlainText;
import de.relimit.commons.markdown.util.MD;

class BlockElementTests {

	public static final String LF = System.lineSeparator();

	@Test
	void setHeadingStyleViaOptions_generateMarkdown_expectStyleApplied() throws MarkdownSerializationException {
		final MarkdownSerializationOptions options = new MarkdownSerializationOptions();

		// Set ATX style to be used (default is SETEXT)
		options.setDefaultHeadingStyle(HeadingStyle.ATX);

		final Heading testHeading = new Heading("Foo");
		// Expect default style (SETEXT) without overriding options
		assertEquals("Foo" + LF + "===", testHeading.serialize());
		// Expect options to override SETEXT style with ATX style
		assertEquals("# Foo", testHeading.serialize(options));
	}

	private static final String[] ADMONITION = {
			"???+ note inline end \"Foo\"" + LF, //
			"    " + LF, //
			"    Foo" + LF, //
			"    " + LF, //
			"    Bar"
	};

	@Test
	void instantiateAdmonition_generateMarkdown_expectWellFormed() throws MarkdownSerializationException {
		final Admonition admonition = Document.start().startAdmonition().expansion(Expansion.EXPANDED)
				.display(Display.INLINE_RIGHT).startTitle().append(new PlainText("Foo")).end()
				.append(new Paragraph("Foo")).append(new Paragraph("Bar")).build();
		assertEquals(Arrays.stream(ADMONITION).collect(Collectors.joining()), admonition.serialize());
	}

	@Test
	void createIndentedDocument_generateMarkdown_expectIndentationWellFormed() throws MarkdownSerializationException {
		final Document document = Document.start()
				// List 1
				.startUnorderedList().startItem(MD.startParagraph().plainText("List 1 Item 1").end())
				// Admonition
				.startAdmonition().expansion(Expansion.EXPANDED).display(Display.INLINE_RIGHT).startTitle()
				.append(new PlainText("Admonition")).end().append(new Paragraph("Admonition Paragraph 1"))
				.startParagraph().plainText("Admonition Paragraph 2").end()
				// List 2
				.startUnorderedList().startItem(MD.startParagraph().plainText("List 2 Item 1").end()).startParagraph()
				.plainText("List 2 Item 1 Paragraph 1").end().end()
				.startItem(MD.startParagraph().plainText("List 2 Item 2").end()).end().end().end().startParagraph()
				.plainText("sdfdf").end()
				// List 3
				.startUnorderedList().startItem(MD.startParagraph().plainText("List 3 Item 1").end()).startParagraph()
				.plainText("List 3 Item 1 Paragraph 1").end().append(new Paragraph("List 3 Item 1 Paragraph 2")).end()
				.end().end().end().build();
		document.setIndentationLevel(3);

		final String[] lines = document.serialize().split(LF);

		assertEquals("            *   List 1 Item 1", lines[0]);
		assertEquals("                ???+ note inline end \"Admonition\"", lines[2]);
		assertEquals("                    Admonition Paragraph 1", lines[4]);
		assertEquals("                    *   List 2 Item 1", lines[8]);
		assertEquals("                        List 2 Item 1 Paragraph 1", lines[10]);
		assertEquals("                sdfdf", lines[13]);
		assertEquals("                *   List 3 Item 1", lines[15]);
		assertEquals("                    List 3 Item 1 Paragraph 1", lines[17]);
	}

	@Test
	void setDefaultHorizontalRuleCharacterViaOptions_generateMarkdown_expectCharacterApplied()
			throws MarkdownSerializationException {
		final MarkdownSerializationOptions options = new MarkdownSerializationOptions();

		// Set arbitrary horizontal rule character supplier
		options.setDefaultHorizontalRuleCharacter(() -> '@');

		final HorizontalRule hr = new HorizontalRule();
		// Expect default character without overriding options
		assertEquals("---", hr.serialize());
		// Expect options to override character
		assertEquals("@@@", hr.serialize(options));
	}

	@Test
	void setCustomHorizontalRuleCharacter_generateMarkdown_expectCharacterInOptionsIgnored()
			throws MarkdownSerializationException {
		final MarkdownSerializationOptions options = new MarkdownSerializationOptions();

		// Set arbitrary horizontal rule character supplier
		options.setDefaultHorizontalRuleCharacter(() -> '@');

		final HorizontalRule hr = new HorizontalRule(RuleCharacter.ASTERISK);
		// Expect custom character
		assertEquals("***", hr.serialize());
		// Still expect custom character. Expect default in options to be ignored
		assertEquals("***", hr.serialize(options));
	}

}
