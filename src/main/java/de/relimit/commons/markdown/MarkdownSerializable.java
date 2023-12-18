package de.relimit.commons.markdown;

import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.converter.Stringifier;

public interface MarkdownSerializable {

	/**
	 * Sets the {@link MarkdownSerializationOptions} that are used if
	 * {@link #serialize()} is invoked. By default, the
	 * {@link MarkdownSerializationOptions#DEFAULT_OPTIONS} are used. Using this
	 * method, options specific to this document can be set. This allows for
	 * pre-configured markdown documents with serialization options specific to
	 * the document. For example the document might contain foreign types and
	 * only a custom {@link Stringifier} (set via the options) might know how to
	 * properly handle those.
	 * 
	 * @param options
	 * @return
	 */
	void setDefaultOptions(MarkdownSerializationOptions options);

	MarkdownSerializationOptions getDefaultoptions();

	/**
	 * Attempts to generate a String representing this markdown element.
	 *
	 * @return Markdown as String
	 * @throws MarkdownSerializationException
	 *             If unable to generate a markdown String
	 */
	default String serialize() throws MarkdownSerializationException {
		return serialize(getDefaultoptions());
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
