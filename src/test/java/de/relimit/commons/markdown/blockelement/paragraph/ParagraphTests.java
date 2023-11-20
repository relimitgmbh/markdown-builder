package de.relimit.commons.markdown.blockelement.paragraph;

import org.junit.Test;

import de.relimit.commons.markdown.Samples;

public class ParagraphTests {

	private Samples samples = new Samples();

	@Test
	public void sampleTest() throws Exception {
		System.out.println(samples.paragraphs().build());
	}

}
