package de.relimit.commons.markdown;

import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

/**
 * Base implementation of {@link MarkdownSerializable} that provides Caching of
 * the serialized markdown.
 */
public abstract class MarkdownElement implements MarkdownSerializable {

	// Cache for speed
	private String serialized;

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
		return getSerialized(MarkdownSerializationOptions.DEFAULT_OPTIONS, this.getClass().getSimpleName());
	}

}
