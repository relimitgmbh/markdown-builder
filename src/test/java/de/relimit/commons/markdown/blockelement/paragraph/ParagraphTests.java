package de.relimit.commons.markdown.blockelement.paragraph;

import org.junit.Test;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.document.DocumentBuilder;

public class ParagraphTests {

	@Test
	public void sampleTest() throws Exception {
		sample();
	}

	public DocumentBuilder sample() throws MarkdownSerializationException {
		final DocumentBuilder db = new DocumentBuilder().paragraph(
				"This is a paragraph containing plain text. Line breaks \nwill lead to new lines \nwithin the paragraph.")
				.paragraph("This is the second paragraph. It is separated from the previous one by a blank line.");
		System.out.println(db);
		return db;
	}

}
