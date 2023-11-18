package de.relimit.commons.markdown;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock.Language;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.table.Alignment;
import de.relimit.commons.markdown.blockelement.table.TableRow;
import de.relimit.commons.markdown.blockelement.table.TableRowBuilder;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.configuration.OptionsBuilder;
import de.relimit.commons.markdown.document.DocumentBuilder;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;

public class Markdown {

	private Markdown() {
		// utility class
	}

	public static void main(String[] args) throws MarkdownSerializationException, FileNotFoundException {
		try {
			final DocumentBuilder documentBuilder = new DocumentBuilder().heading("This is a heading").startParagraph()
					.startEmphasis(Type.BOLD).plainText("This is bold text. ")
					.emphasis(Type.ITALIC, "This is italic text nested in bold text.")
					.plainText(" This is another bold text.").end().end()
					//
					.startParagraph().emphasis(Type.STRIKETHROUGH, "This is strike through text").end()
					.paragraph("This is the first line of the paragraph.\nThis is the second line of the paragraph.")
					.quote().paragraph("This is another paragraph.\nIt has\nthree lines.").unquote()
					.codeBlock("// This is java code\nSystem.out.println(\"Hello markdown!\");", Language.JAVA)
					.paragraph("The following is a horizontal rule.").horizontalRule()
					// Unordered list
					.startUnorderedList().startItem(new Paragraph("Item 1")).startUnorderedList()
					.startItem(new Paragraph("Subitem 1\nAfter line break.")).paragraph("Subitem 1 Paragraph 2")
					.paragraph("Subitem 1 Paragraph 3").end().end().end().end()
					// Ordered list
					.startOrderedList().startItem(new Paragraph("Item 1")).startOrderedList()
					.startItem(new Paragraph("Subitem 1\nAfter line break.")).paragraph("Subitem 1 Paragraph 2")
					.paragraph("Subitem 1 Paragraph 3")
					//
					.end()
					//
					.end()
					//
					.end()
					//
					.end()
					// Table
					.startTable().defaultAlignment(Alignment.NEUTRAL).align(1, Alignment.RIGHT)
					// Heade row
					.startRow().startCell().plainText("Header 1").end().startCell().plainText("Header 2").end().end()
					// Row 2
					.startRow().startCell().plainText("Cell 1").end().startCell().plainText("Cell 2").end().startCell()
					.plainText("Cell 3 (ignored)").end().end()
					// Row 3
					.startRow().startCell().plainText("Left of code").code("Cell 1").plainText("Right of code").end()
					.end()
					//
					.end();

			final MarkdownSerializationOptions options = new OptionsBuilder()
					//					.serializer(String.class, (e, o) -> "String in upper case: " + o.toUpperCase())
					//
					//					.serializer(Instant.class, (e, o) -> "Instant in nanos: " + o.getNano() + "")
					//
					//					.serializer(Severity.class, (e, o) -> {
					//						switch (o) {
					//						case ALERT:
					//							return "Severity: \u26a0\ufe0f";
					//						default:
					//							return o.name();
					//						}
					//					})
					//
					//.lineSeparator("\ud83d\uded1\n")
					.build();

			final String markdown = documentBuilder.build().serialize(options);

			try (PrintWriter out = new PrintWriter("/Users/relimit/Downloads/markdown.md")) {
				out.write(markdown);
			}
			System.out.println(markdown);

			final TableRow row = new TableRowBuilder<>(new TableRow()).startCell().plainText("Cell 1").end().startCell()
					.plainText("Cell 2").end().build();
			System.out.println(row.serialize());

		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

}
