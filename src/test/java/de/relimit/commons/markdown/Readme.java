package de.relimit.commons.markdown;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock.Language;
import de.relimit.commons.markdown.blockelement.paragraph.ParagraphTests;
import de.relimit.commons.markdown.document.DocumentBuilder;

public class Readme {

	public static final String README_FILE_NAME = "README.md";

	private static void buildDocument(DocumentBuilder b) throws MarkdownSerializationException {

		b.heading("Java Markdown Builder");
		b.paragraph(
				"A lightweight library for generating pretty printed markdown. No external dependencies except JUnit are used. This "
						+ README_FILE_NAME + " file was generated with Markdown Builder.");
		b.heading("Examples");
		b.heading(2, "Paragraphs and Line Breaks");
		b.codeBlock(new ParagraphTests().sample(), Language.MARKDOWN);

	}

	public static void main(String[] args) throws MarkdownSerializationException {

		final DocumentBuilder b = new DocumentBuilder();
		buildDocument(b);
		final String markdown = b.build().serialize();

		final File markdownFile = new File(README_FILE_NAME);
		try (final PrintWriter markdownWriter = new PrintWriter(markdownFile)) {
			markdownWriter.write(markdown);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
