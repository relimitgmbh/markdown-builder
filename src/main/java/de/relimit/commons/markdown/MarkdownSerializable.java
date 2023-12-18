package de.relimit.commons.markdown;

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
		return serialize(MarkdownSerializationOptions.DEFAULT_OPTIONS);
	}

	/**
	 * Attempts to generate a String representing this markdown element.
	 *
	 * @return Markdown as String
	 * @throws MarkdownSerializationException
	 *             If unable to generate a markdown String
	 */
	String serialize(MarkdownSerializationOptions options) throws MarkdownSerializationException;

	/**
	 * Returns the result of
	 * {@link MarkdownElement#getSerialized(MarkdownSerializationOptions)} or
	 * the specified fallback if a {@link MarkdownSerializationException}
	 * occurred.
	 * 
	 * @param fallback
	 *            String to return if serialization fails
	 *
	 * @return Markdown as String or specified fallback
	 */
	String getSerialized(MarkdownSerializationOptions options, String fallback);

	/**
	 * Calls {@link MarkdownElement#serialize()} or directly returns its last
	 * result from {@link MarkdownElement#serialized}.
	 *
	 * @return Markdown as String
	 * @throws MarkdownSerializationException
	 *             If unable to generate a markdown String
	 */
	String getSerialized(MarkdownSerializationOptions options) throws MarkdownSerializationException;

}
