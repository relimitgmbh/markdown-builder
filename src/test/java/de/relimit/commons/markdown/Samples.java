package de.relimit.commons.markdown;

import de.relimit.commons.markdown.document.DocumentBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;

public class Samples {

	@Sample(heading = "Paragraphs and Line Breaks",
			introduction = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.")
	public DocumentBuilder paragraph() throws MarkdownSerializationException {
		return new DocumentBuilder()
				.paragraph("This is a paragraph containing plain text. Line breaks \n"
						+ "will lead to new lines \nwithin the paragraph.")
				.paragraph("This is the second paragraph. It is separated from the previous one by a blank line.");
	}

	@Sample(heading = "Emphasis")
	public DocumentBuilder emphasis() throws MarkdownSerializationException {
		return new DocumentBuilder().startParagraph() //
				.plainText("This is normal.").newLine() //
				.emphasis(Type.BOLD, "This is bold.").newLine() //
				.emphasis(Type.STRIKETHROUGH, "This is strikethrough.").newLine() //
				.emphasis(Type.ITALIC, "This is italic.").newLine() //
				.startEmphasis(Type.BOLD).plainText("Span elements can be nested. This is bold text ")
				.emphasis(Type.ITALIC, "followed by bold and italic text").plainText(" and finally bold text again.")
				.end() // end emphasis
				.end(); // end paragraph
	}

}
