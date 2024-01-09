package de.relimit.commons.markdown;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.span.SpanElement;

public class SamplesTests {

	private Samples samples = new Samples();

	private void serialize(MarkdownSerializable markdownSerializable) throws MarkdownSerializationException {
		markdownSerializable.serialize();
	}

	private List<Method> methods() {
		return Arrays.stream(samples.getClass().getDeclaredMethods()).filter(m -> m.getAnnotation(Sample.class) != null)
				.filter(m -> {
					// Only no-arg methods
					if (m.getParameterCount() > 0) {
						return false;
					}
					// Only BlockElements or SpanElements
					if (m.getReturnType().isAssignableFrom(BlockElement.class)) {
						return true;
					}
					if (m.getReturnType().isAssignableFrom(SpanElement.class)) {
						return true;
					}
					return true;
				}).collect(Collectors.toList());
	}

	@Test
	void propertyKeysFound_CompareKeysToPropertyKeys_NoneMissing() {
		final Properties props = new Properties();
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final InputStream stream = loader.getResourceAsStream(Samples.PROPERTIES_FILE);
		assertDoesNotThrow(() -> props.load(stream));
		for (final Method method : methods()) {
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
		assertEquals(methods().size(), Arrays.stream(samples.getClass().getDeclaredMethods())
				.filter(m -> m.getParameterCount() == 0).collect(Collectors.toList()).size());
	}

	@TestFactory
	Stream<DynamicTest> serializeMethods_allValidSamples_DoesNotThrow() {
		return methods().stream().map(m -> DynamicTest.dynamicTest(m.getName(),
				() -> assertDoesNotThrow(() -> serialize((MarkdownSerializable) m.invoke(samples)))));
	}
}
