package de.relimit.commons.markdown;

import java.util.List;

import de.relimit.commons.markdown.blockelement.table.TableCell;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public interface MarkdownSerializable {

	/**
	 * Attempts to generate a String representing this markdown element.
	 *
	 * @return Markdown as String
	 * @throws MarkdownSerializationException
	 *             If unable to generate a markdown String
	 */
	default String serialize() throws MarkdownSerializationException {
		return serialize(new MarkdownSerializationOptions());
	}

	/**
	 * Attempts to generate a String representing this markdown element.
	 *
	 * @return Markdown as String
	 * @throws MarkdownSerializationException
	 *             If unable to generate a markdown String
	 */
	default String serialize(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		return MarkdownSerializable.serialize(this, options, options.getLineSeparator());
	}

	List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException;

	String getSerialized(MarkdownSerializationOptions options) throws MarkdownSerializationException;

	String getSerialized(MarkdownSerializationOptions options, String fallback);

	/**
	 * To to be able to alter the line break for {@link TableCell}s but still
	 * use the same code for all {@link MarkdownSerializable}s.
	 * 
	 * @param serializable
	 * @param options
	 * @param lineBreak
	 * @return
	 * @throws MarkdownSerializationException
	 */
	static String serialize(MarkdownSerializable serializable, MarkdownSerializationOptions options, String lineBreak)
			throws MarkdownSerializationException {
		final StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (final String line : serializable.serializeLines(options)) {
			if (!isFirst) {
				sb.append(lineBreak);
			}
			sb.append(line);
			isFirst = false;
		}
		return sb.toString();
	}

}
