package de.relimit.commons.markdown;

import de.relimit.commons.markdown.document.DocumentBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;

public class Samples {

	@Sample(heading = "Paragraphs and Line Breaks",
			introduction = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.")
	public DocumentBuilder paragraphs() throws MarkdownSerializationException {
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

	@Sample(heading = "Task Lists",
			introduction = "Note: This is a non-standard element and might not be supported by all markdown renderers.")
	public DocumentBuilder taskLists() throws MarkdownSerializationException {
		return new DocumentBuilder().startTaskList() //
				.task(false, "This task is pending.") //
				.task(true, "This task is completed.") //
				.startTask(false).plainText("This task is pending but it has nice ")
				.emphasis(Type.BOLD, "bold formatted text").plainText(" going for it.").end().end();
	}

}
