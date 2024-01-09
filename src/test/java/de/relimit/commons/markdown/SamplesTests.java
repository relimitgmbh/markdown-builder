package de.relimit.commons.markdown;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

public class SamplesTests {

	private Samples samples = new Samples();

	private void serialize(MarkdownSerializable markdownSerializable) throws MarkdownSerializationException {
		markdownSerializable.serialize();
	}

	@Test
	void propertyKeysFound_CompareKeysToPropertyKeys_NoneMissing() {
		final Properties props = new Properties();
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final InputStream stream = loader.getResourceAsStream(Samples.PROPERTIES_FILE);
		assertDoesNotThrow(() -> props.load(stream));
		for (final Method method : Samples.sampleMethods()) {
			final Sample sample = method.getAnnotation(Sample.class);
			final String propertiesKey = Samples.PROPERTY_KEY_NAMESPACE + "." + sample.key() + "."
					+ Samples.PROPERTY_KEY_SUFFIX_HEADING;
			final String heading = props.getProperty(propertiesKey);
			assertNotNull(heading);
			assertFalse(heading.isBlank());
		}
	}

	@TestFactory
	Stream<DynamicTest> serializeMethods_allValidSamples_DoesNotThrow() {
		return Samples.sampleMethods().stream().map(m -> DynamicTest.dynamicTest(m.getName(),
				() -> assertDoesNotThrow(() -> serialize((MarkdownSerializable) m.invoke(samples)))));
	}
}
