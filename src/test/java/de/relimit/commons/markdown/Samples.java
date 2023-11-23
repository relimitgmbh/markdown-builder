package de.relimit.commons.markdown;

import de.relimit.commons.markdown.blockelement.codeblock.Language;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.configuration.TextEscaper;
import de.relimit.commons.markdown.document.Document;
import de.relimit.commons.markdown.document.DocumentBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;;

public class Samples {

	public static final String PROPERTIES_FILE = "samples.properties";

	public static final String PROPERTY_KEY_NAMESPACE = "examples.chapters";
	public static final String PROPERTY_KEY_SUFFIX_HEADING = "heading";
	public static final String PROPERTY_KEY_SUFFIX_INTRO = "intro";

	@Sample(order = 10, key = "paragraphs")
	public DocumentBuilder paragraphs() {
		return Document.start()
				.paragraph("This is a paragraph containing plain text. Line breaks \n"
						+ "will lead to new lines \nwithin the paragraph.")
				.paragraph("This is the second paragraph. It is separated from the previous one " + "by a blank line.");
	}

	@Sample(order = 20, key = "emphasis")
	public DocumentBuilder emphasis() {
		return Document.start().startParagraph() //
				.emphasis(Type.BOLD, "This is bold.").newLine() //
				.emphasis(Type.STRIKETHROUGH, "This is strikethrough.").newLine() //
				.emphasis(Type.ITALIC, "This is italic.").newLine() //
				.startEmphasis(Type.BOLD).plainText("Span elements can be nested. This is bold text ")
				.emphasis(Type.ITALIC, "followed by bold and italic text").plainText(" and finally bold text again.")
				.end() // end emphasis
				.end(); // end paragraph
	}

	@Sample(order = 30, key = "taskLists")
	public DocumentBuilder taskLists() {
		return Document.start().startTaskList() //
				.item("This task is completed.", true) //
				.startItem().startParagraph().plainText("This task is pending but it has nice ")
				.emphasis(Type.BOLD, "bold formatted text").plainText(" going for it.").end() // end paragraph
				.end() // end task item
				.end(); // end task list
	}

	@Sample(order = 40, key = "lists")
	public DocumentBuilder lists() {
		return Document.start().startUnorderedList() //
				.item("First item.") //
				.item("Second item.") //
				.startItem().paragraph("Third item.").paragraph("Another paragraph of the third item.").quote()
				.paragraph("This is a quoted paragraph of the third item.").unquote()
				.codeBlock("// This is a code block of the third item.", Language.JAVA).end().end();
	}

	@Sample(order = 50, key = "escaping")
	public DocumentBuilder escaping() {
		return Document.start().startParagraph().plainText(
				"Markdown characters are automatically escaped by default. This means that characters like * or # are "
						+ "not rendered as emphasis. Paths like c:\\temp\\foo.bar are safe. The ")
				.simpleClassName(TextEscaper.class).plainText(" can be configured via ")
				.simpleClassName(MarkdownSerializationOptions.class).plainText(".").end();
	}

}
