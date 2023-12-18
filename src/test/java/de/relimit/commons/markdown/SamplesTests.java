package de.relimit.commons.markdown;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

// TODO
public class SamplesTests {

	private Samples samples = new Samples();

	private void serialize(MarkdownSerializable markdownSerializable) throws MarkdownSerializationException {
		markdownSerializable.serialize();
	}

	@Test
	public void paragraphsTest() throws Exception {
		// TODO Use @TestFactory and reflection. See class Readme.
		assertDoesNotThrow(() -> serialize(samples.paragraphs()));
	}

}
