package de.relimit.commons.markdown;

import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock.Language;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.configuration.TextEscaper;
import de.relimit.commons.markdown.document.DocumentBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;;

public class Samples {

	@Sample(order = 10, heading = "Paragraphs and Line Breaks",
			introduction = "The two most important elements when working with Markdown Builder are block elements and "
					+ "span elements. Block elements are separated from each other by a blank line and render as a "
					+ "paragraph.")
	public DocumentBuilder paragraphs() throws MarkdownSerializationException {
		return new DocumentBuilder()
				.paragraph("This is a paragraph containing plain text. Line breaks \n"
						+ "will lead to new lines \nwithin the paragraph.")
				.paragraph("This is the second paragraph. It is separated from the previous one " + "by a blank line.");
	}

	@Sample(order = 20, heading = "Emphasis")
	public DocumentBuilder emphasis() throws MarkdownSerializationException {
		return new DocumentBuilder().startParagraph() //
				.emphasis(Type.BOLD, "This is bold.").newLine() //
				.emphasis(Type.STRIKETHROUGH, "This is strikethrough.").newLine() //
				.emphasis(Type.ITALIC, "This is italic.").newLine() //
				.startEmphasis(Type.BOLD).plainText("Span elements can be nested. This is bold text ")
				.emphasis(Type.ITALIC, "followed by bold and italic text").plainText(" and finally bold text again.")
				.end() // end emphasis
				.end(); // end paragraph
	}

	@Sample(order = 30, heading = "Task Lists",
			introduction = "Note: This is a non-standard element and might not be supported by all markdown renderers.")
	public DocumentBuilder taskLists() throws MarkdownSerializationException {
		return new DocumentBuilder().startTaskList() //
				.item("This task is completed.", true) //
				.startItem().startParagraph().plainText("This task is pending but it has nice ")
				.emphasis(Type.BOLD, "bold formatted text").plainText(" going for it.").end() // end paragraph
				.end() // end task item
				.end(); // end task list
	}

	@Sample(order = 40, heading = "Lists",
			introduction = "The first block element appended to a list item must always be a paragraph. Meaning text only. "
					+ "But further block elements can be added and they can be of any type. They will align "
					+ "nicely with the list items indent.")
	public DocumentBuilder lists() throws MarkdownSerializationException {
		return new DocumentBuilder().startUnorderedList() //
				.item("First item.") //
				.item("Second item.") //
				.startItem().paragraph("Third item.").paragraph("Another paragraph of the third item.").quote()
				.paragraph("This is a quoted paragraph of the third item.").unquote()
				.codeBlock("// This is a code block of the third item.", Language.JAVA).end().end();
	}

	@Sample(order = 50, heading = "Escaping")
	public DocumentBuilder escaping() throws MarkdownSerializationException {
		return new DocumentBuilder().startParagraph().plainText(
				"Markdown characters are automatically escaped by default. This means that characters like * or # are "
						+ "not rendered as emphasis. Paths like c:\\temp\\foo.bar are safe as well. The ")
				.simpleClassName(TextEscaper.class).plainText(" can be " + "configured via ")
				.simpleClassName(MarkdownSerializationOptions.class).plainText(".").end();
	}

}
