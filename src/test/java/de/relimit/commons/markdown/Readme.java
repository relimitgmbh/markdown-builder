package de.relimit.commons.markdown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.codeblock.Language;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.configuration.OptionsBuilder;
import de.relimit.commons.markdown.converter.Escaper;
import de.relimit.commons.markdown.document.Document;
import de.relimit.commons.markdown.document.DocumentBuilder;
import de.relimit.commons.markdown.span.SpanElement;

public class Readme {

	public static final String README_FILE_NAME = "README.md";

	public static final String AT = "@";

	public static final MarkdownSerializationOptions ALLOW_MARKDOWN = new OptionsBuilder()
			.defaultEscaper(Escaper.ALLOW_MARKDOWN).build();

	public static List<Method> sampleMethods() {
		return Arrays.stream(Samples.class.getDeclaredMethods())
				// Only methods that are marked as Sample methods
				.filter(m -> m.getAnnotation(Sample.class) != null)
				/**
				 * By definition "sample" methods have no parameters and return
				 * a MarkdownSerilizable. Skip all methods that do not meet
				 * those criteria.
				 */
				.filter(m -> {
					// Only no-arg methods
					if (m.getParameterCount() > 0) {
						return false;
					}
					// Only BlockElements or SpanElements
					if (BlockElement.class.isAssignableFrom(m.getReturnType())) {
						return true;
					}
					if (SpanElement.class.isAssignableFrom(m.getReturnType())) {
						return true;
					}
					return false;
				})
				// Sort by order defined by annotation
				.sorted(Comparator.comparingInt(m -> m.getAnnotation(Sample.class).order()))
				// Capture the sorted list of methods.
				.collect(Collectors.toList());
	}

	/**
	 * A poor man's source code parser that extracts all methods annotated
	 * with @Sample from the Samples.java source file and returns them as a Map.
	 * The key of the map is the key defined in the annotation.
	 * 
	 * @see Samples
	 * 
	 * @return
	 * @throws IOException
	 */
	private static Map<String, List<String>> parseSourceFile(Class<?> clazz) throws IOException {
		/*
		 * Attempts to get the source file of the Samples class. Should also
		 * work on Windows but untested. The package path is created from the
		 * full class name and therefore ends with the classes' name.
		 * getParent() removes this unwanted path segment.
		 */
		final Path packagePath = Path.of(clazz.getName().replace('.', File.separatorChar)).getParent();
		final Path path = Path.of("src", "test", "java").resolve(packagePath).resolve(clazz.getSimpleName() + ".java");

		final BufferedReader in = new BufferedReader(new FileReader(path.toFile()));
		final Map<String, List<String>> sources = new HashMap<>();
		List<String> methodSource = null;
		int indent = -1;
		String line = in.readLine();
		/*
		 * Look at each line of code but stop right before the closing brackets
		 * of the class.
		 */
		while (line != null && !line.startsWith("}")) {
			if (line.contains(AT + Sample.class.getSimpleName())) {
				if (indent == -1) {
					/*
					 * Remember the indentation of the first annotation found so
					 * we can remove it from every line thereafter.
					 */
					indent = line.indexOf(AT);
				}
				final StringBuilder sb = new StringBuilder();
				sb.append(line);
				while (!line.contains(")")) {
					line = in.readLine();
					sb.append(line);
				}
				final String s = sb.toString();
				final Properties properties = parseAnnotation(s);
				final String heading = properties.getProperty(Sample.KEY);
				if (sources.containsKey(heading)) {
					in.close();
					throw new IllegalStateException("Two or more methods annotated with @"
							+ Sample.class.getSimpleName() + " share the same key. The key must be unique.");
				}
				/*
				 * Start collecting the source code lines of the new method we
				 * just found.
				 */
				methodSource = new ArrayList<>();
				sources.put(heading, methodSource);
			} else if (line.contains(AT)) {
				/*
				 * Skip further annotations. Currently not relevant but just in
				 * case.
				 */
			} else {
				/* Ignore blank lines and all lines before the first method. */
				if (!line.isBlank() && methodSource != null) {
					methodSource.add(line.substring(indent));
				}
			}
			line = in.readLine();
		}
		in.close();
		return sources;
	}

	/**
	 * Parses a string in the form 'abc(key="value",key="value")'.
	 * 
	 * @param s
	 * @return
	 */
	private static Properties parseAnnotation(String s) {
		s = s.substring(s.indexOf("(") + 1);
		s = s.substring(0, s.indexOf(")"));
		s = s.trim();

		final Pattern p = Pattern.compile("(\\w+)\\s*=\\s*(\\\"[^\\\"]*\\\"|'[^']*')");
		final Matcher m = p.matcher(s);
		final Properties properties = new Properties();
		while (m.find()) {
			final String key = m.group(1);
			String value = m.group(2);
			value = value.substring(1, value.length() - 1);
			properties.put(key, value);
		}

		return properties;
	}

	private static DocumentBuilder buildDocument()
			throws MarkdownSerializationException, ReflectiveOperationException, IOException {

		// Load texts from properties file

		final Properties props = new Properties();
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final InputStream stream = loader.getResourceAsStream(Samples.PROPERTIES_FILE);
		props.load(stream);

		final DocumentBuilder b = Document.start();

		// Chapter: Introduction
		b.heading(props.getProperty("introduction.heading"));
		b.paragraph(MessageFormat.format(props.getProperty("introduction.text"), "MIT license", "LICENSE", "README.md",
				"README.md", Readme.class.getSimpleName() + ".java",
				"src/test/java/" + Readme.class.getCanonicalName().replace(".", "/") + ".java"));

		// Chapter: Examples
		b.heading(props.getProperty("examples.heading"));

		final Samples samples = new Samples();
		final Map<String, List<String>> sources = parseSourceFile(Samples.class);
		final List<Method> methods = Readme.sampleMethods();

		for (final Method method : methods) {
			final Sample sample = method.getAnnotation(Sample.class);
			/*
			 * The key of the sample is defined by the annotation. We use it to
			 * find the source code in the source code map. It doubles up as a
			 * namespace for the resource bundle keys.
			 */
			final String key = sample.key();
			final String propertiesKey = Samples.PROPERTY_KEY_NAMESPACE + "." + key;

			final String heading = props.getProperty(propertiesKey + "." + Samples.PROPERTY_KEY_SUFFIX_HEADING);
			if (heading == null || heading.isBlank()) {
				throw new IllegalStateException(
						"Resource key " + propertiesKey + "." + Samples.PROPERTY_KEY_SUFFIX_HEADING + " not found in "
								+ Samples.PROPERTIES_FILE + " or value is empty.");
			}
			b.heading(2, heading);

			// Add an optional introductory paragraph
			final String intro = props.getProperty(propertiesKey + "." + Samples.PROPERTY_KEY_SUFFIX_INTRO);
			if (intro != null && !intro.isBlank()) {
				b.paragraph(intro);
			}

			/*
			 * Get the method's source code from the map created by the source
			 * code parser.
			 */
			final String methodCode = sources.get(key).stream().collect(Collectors.joining(System.lineSeparator()));
			b.heading(3, props.getProperty("examples.heading.code"));
			b.codeBlock(methodCode, Language.JAVA);

			// Invoke the method
			final MarkdownSerializable mds = (MarkdownSerializable) method.invoke(samples);

			/*
			 * Add the serialized markdown as a markdown code block so the
			 * reader can see the output generated by the builder pre-rendering
			 */
			final MarkdownSerializationOptions options = new MarkdownSerializationOptions(mds.getDefaultoptions());
			if (sample.escape()) {
				options.setEscaper(Escaper.ESCAPE_MARKDOWN);
			} else {
				options.setEscaper(Escaper.ALLOW_MARKDOWN);
			}
			final String markdown = mds.serialize(options);
			b.heading(3, props.getProperty("examples.heading.markdown"));
			b.codeBlock(markdown, Language.MARKDOWN);

			/*
			 * Add the not yet serialized markdown document node as a block
			 * element to the main document. This means the sample markdown will
			 * be seamlessly embedded in the readme file and will be rendered by
			 * Gitea / Github along with the rest of the readme file. The reader
			 * can then see what it will actually look like formatted.
			 */
			b.heading(3, props.getProperty("examples.heading.rendered"));
			if (mds instanceof BlockElement) {
				b.append((BlockElement) mds);
			} else if (mds instanceof SpanElement) {
				b.paragraph(mds);
			} else {
				throw new AssertionError("Expected either a " + BlockElement.class.getSimpleName() + " or a "
						+ SpanElement.class.getSimpleName() + " but found a " + mds.getClass().getSimpleName() + ".");
			}
		}

		return b;

	}

	public static void main(String[] args) throws IOException {

		final String markdown;
		try {
			final DocumentBuilder b = buildDocument();
			markdown = b.build().serialize(ALLOW_MARKDOWN);
		} catch (MarkdownSerializationException | ReflectiveOperationException e) {
			e.printStackTrace();
			return;
		}

		final File markdownFile = new File(README_FILE_NAME);
		try (final PrintWriter markdownWriter = new PrintWriter(markdownFile)) {
			markdownWriter.write(markdown);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
