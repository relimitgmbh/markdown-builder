package de.relimit.commons.markdown;

import de.relimit.commons.markdown.blockelement.table.TableCell;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.Strings;

/**
 * Base implementation of {@link MarkdownSerializable} that provides Caching of
 * the serialized markdown.
 */
public abstract class MarkdownElement implements LineBased {

	private MarkdownSerializationOptions options;

	// Cache for speed
	private String serialized;

	@Override
	public MarkdownSerializationOptions getDefaultoptions() {
		if (options != null) {
			return options;
		}
		return MarkdownSerializationOptions.DEFAULT_OPTIONS;
	}

	@Override
	public void setDefaultOptions(MarkdownSerializationOptions options) {
		this.options = options;
	}

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

	/**
	 * Attempts to generate a String representing this markdown element.
	 *
	 * @return Markdown as String
	 * @throws MarkdownSerializationException
	 *             If unable to generate a markdown String
	 */
	@Override
	public String serialize(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		return serialize(this, options, options.getLineSeparator());
	}

	/**
	 * To to be able to alter the line break for {@link TableCell}s but still
	 * use the same code for all {@link MarkdownSerializable}s.
	 * 
	 * @param serializable
	 * @param options
	 * @param lineBreak
	 *            The line break used to separate lines from each other.
	 *            Ultimately it originates from the options object which is
	 *            available as well. It is a separate property because the
	 *            calling code decides which separator is used depending on the
	 *            situation.
	 * @return
	 * @throws MarkdownSerializationException
	 */
	protected static String serialize(MarkdownElement serializable, MarkdownSerializationOptions options,
			String lineBreak) throws MarkdownSerializationException {
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

	@Override
	public String toString() {
		return Strings.stringify(this);
	}

}
