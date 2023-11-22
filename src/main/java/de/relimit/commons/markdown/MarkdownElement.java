package de.relimit.commons.markdown;

import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptionsImpl;

/**
 * Base class that every markdown element extends.
 */
public abstract class MarkdownElement implements MarkdownSerializable {

	private String serialized;

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
	@Override
	public String getSerialized(MarkdownSerializationOptions options, String fallback) {
		try {
			return getSerialized(options);
		} catch (final MarkdownSerializationException e) {
			return fallback;
		}
	}

	/**
	 * Calls {@link MarkdownElement#serialize()} or directly returns its last
	 * result from {@link MarkdownElement#serialized}.
	 *
	 * @return Markdown as String
	 * @throws MarkdownSerializationException
	 *             If unable to generate a markdown String
	 */
	@Override
	public String getSerialized(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		if (serialized == null) {
			serialized = serialize(options);
		}
		return serialized;
	}

	/**
	 * Sets {@link MarkdownElement#serialized} to null. The next call to
	 * {@link MarkdownElement#getSerialized(MarkdownSerializationOptions)} fill
	 * invoke a fresh serialization.
	 */
	public void invalidateSerialized() {
		this.serialized = null;
	}

	@Override
	public String toString() {
		return getSerialized(MarkdownSerializationOptionsImpl.DEFAULT_OPTIONS, this.getClass().getSimpleName());
	}

}
