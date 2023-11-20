package de.relimit.commons.markdown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock.Language;
import de.relimit.commons.markdown.document.Document;
import de.relimit.commons.markdown.document.DocumentBuilder;

public class Readme {

	public static final String README_FILE_NAME = "README.md";

	public static final String AT = "@";

	/**
	 * A poor man's source code parser that extracts all methods annotated
	 * with @Sample from the Samples.java source file and returns them as a Map.
	 * The key of the map is the heading defined in the annotation.
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
				final String heading = properties.getProperty(Sample.HEADING);
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

		final DocumentBuilder b = new DocumentBuilder();

		b.heading("Java Markdown Builder");
		b.paragraph(
				"A lightweight library for generating pretty printed markdown. No external dependencies except JUnit are used. This "
						+ README_FILE_NAME + " file was generated with Markdown Builder.");
		b.heading("Examples");

		final Samples samples = new Samples();
		final Map<String, List<String>> samplesSources = parseSourceFile(Samples.class);
		for (final Method method : samples.getClass().getDeclaredMethods()) {
			final Sample sample = method.getAnnotation(Sample.class);
			// Skip all methods that aren't marked as sample methods
			if (sample == null) {
				continue;
			}
			/*
			 * By definition "sample" methods have no paramers and return a
			 * DocumentBuilder. Skip all methods that do not meet those
			 * criteria.
			 */
			if (method.getParameterCount() != 0 || !method.getReturnType().equals(DocumentBuilder.class)) {
				continue;
			}
			/*
			 * The heading of the sample is defined by the annotation. It
			 * doubles up as a key. We use it to find the source code in the
			 * source code map.
			 */
			final String heading = sample.heading();
			b.heading(2, heading);

			// Add an optional introductory paragraph
			final String introduction = sample.introduction();
			if (!introduction.isBlank()) {
				b.paragraph(introduction);
			}

			/*
			 * Get the method's source code from the map created by the source
			 * code parser.
			 */
			final String methodCode = samplesSources.get(heading).stream()
					.collect(Collectors.joining(System.lineSeparator()));
			b.heading(3, "Java Code");
			b.codeBlock(methodCode, Language.JAVA);

			// Invoke the method to get the markdown
			final DocumentBuilder builder = (DocumentBuilder) method.invoke(samples);
			final Document document = builder.build();
			final String markdown = document.serialize();
			b.heading(3, "Markdown");
			b.codeBlock(markdown, Language.MARKDOWN);

			b.heading(3, "Rendered");
			b.append(document);
		}

		return b;

	}

	public static void main(String[] args) throws IOException {

		final String markdown;
		try {
			final DocumentBuilder b = buildDocument();
			markdown = b.build().serialize();
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
