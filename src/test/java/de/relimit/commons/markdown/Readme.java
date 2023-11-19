package de.relimit.commons.markdown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock.Language;
import de.relimit.commons.markdown.document.DocumentBuilder;

public class Readme {

	public static final String README_FILE_NAME = "README.md";

	private static Map<String, List<String>> getSources() throws IOException {
		final String samplesClassName = Samples.class.getName().replace('.', '/');
		final BufferedReader in = new BufferedReader(new FileReader("src/test/java/" + samplesClassName + ".java"));
		final Map<String, List<String>> methodSourceCode = new HashMap<>();
		String line = in.readLine();
		String sampleName;
		List<String> sampleMethodCode = null;
		int indent = -1;
		while (line != null && !line.startsWith("}")) {
			if (line.contains("@" + Sample.class.getSimpleName())) {
				if (indent == -1) {
					indent = line.indexOf("@");
				}
				final int startOfName = line.indexOf('\"') + 1;
				final int endOfName = line.lastIndexOf('\"');
				sampleName = line.substring(startOfName, endOfName);
				sampleMethodCode = new ArrayList<>();
				methodSourceCode.put(sampleName, sampleMethodCode);
			} else if (line.contains("@")) {
				// Skip annotations
			} else {
				if (!line.isBlank() && sampleMethodCode != null) {
					sampleMethodCode.add(line.substring(indent));
				}
			}
			line = in.readLine();
		}
		in.close();
		return methodSourceCode;
	}

	private static void buildDocument(DocumentBuilder b)
			throws MarkdownSerializationException, ReflectiveOperationException, IOException {

		b.heading("Java Markdown Builder");
		b.paragraph(
				"A lightweight library for generating pretty printed markdown. No external dependencies except JUnit are used. This "
						+ README_FILE_NAME + " file was generated with Markdown Builder.");
		b.heading("Examples");

		final Samples samples = new Samples();
		final Map<String, List<String>> samplesSources = getSources();
		for (final Method method : samples.getClass().getDeclaredMethods()) {
			final Sample sample = method.getAnnotation(Sample.class);
			if (sample == null) {
				continue;
			}
			if (method.getParameterCount() != 0 || !method.getReturnType().equals(DocumentBuilder.class)) {
				continue;
			}
			final String heading = sample.heading();
			b.heading(2, heading);
			final DocumentBuilder builder = (DocumentBuilder) method.invoke(samples);
			final String methodCode = samplesSources.get(heading).stream()
					.collect(Collectors.joining(System.lineSeparator()));
			b.heading(3, "Java Code");
			b.codeBlock(methodCode, Language.JAVA);
			final String markdown = builder.build().serialize();
			b.heading(3, "Markdown");
			b.codeBlock(markdown, Language.MARKDOWN);
		}

	}

	public static void main(String[] args) throws IOException {

		final DocumentBuilder b = new DocumentBuilder();
		final String markdown;
		try {
			buildDocument(b);
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
