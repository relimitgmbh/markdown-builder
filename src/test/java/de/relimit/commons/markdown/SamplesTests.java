package de.relimit.commons.markdown;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;
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
		for (final Method method : Readme.sampleMethods()) {
			final Sample sample = method.getAnnotation(Sample.class);
			final String propertiesKey = Samples.PROPERTY_KEY_NAMESPACE + "." + sample.key() + "."
					+ Samples.PROPERTY_KEY_SUFFIX_HEADING;
			final String heading = props.getProperty(propertiesKey);
			assertNotNull(heading);
			assertNotSame("", heading);
		}
	}

	@Test
	void allMethodsAreNoArg_compareMethods_Success() {
		assertEquals(Readme.sampleMethods().size(), Arrays.stream(samples.getClass().getDeclaredMethods())
				.filter(m -> m.getParameterCount() == 0).collect(Collectors.toList()).size());
	}

	@TestFactory
	Stream<DynamicTest> serializeMethods_allValidSamples_DoesNotThrow() {
		return Readme.sampleMethods().stream().map(m -> DynamicTest.dynamicTest(m.getName(),
				() -> assertDoesNotThrow(() -> serialize((MarkdownSerializable) m.invoke(samples)))));
	}
}
