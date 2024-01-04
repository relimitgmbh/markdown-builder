package de.relimit.commons.markdown;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.span.SpanElement;

// TODO
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

	@TestFactory
	Stream<DynamicTest> serializeMethods_allValidSamples_DoesNotThrow() {
		return methods().stream().map(m -> DynamicTest.dynamicTest(m.getName(),
				() -> assertDoesNotThrow(() -> serialize((MarkdownSerializable) m.invoke(samples)))));
	}
}
