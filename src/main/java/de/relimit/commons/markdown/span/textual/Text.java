package de.relimit.commons.markdown.span.textual;

import java.util.List;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.configuration.TextSerializer;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.util.StringUtil;

/**
 * Something that contains only text. The class takes an {@link Object} as
 * opposed to a {@link String} to not limit possible uses. An unlimited amount
 * of {@link TextSerializer}s can be set via
 * {@link MarkdownSerializationOptions}. By default, simply {@link #toString()}
 * is invoked on the object.
 * <p>
 * The String returned may contain UNIX or Windows line breaks. These will be
 * reflected in the generated markdown as new lines but will not lead to new
 * paragraphs.
 * 
 * @author Jens Westarp
 *
 */
public abstract class Text extends MarkdownElement implements SpanElement, Textual {

	private Object stringyfiable;

	public Text(Object stringyfiable) {
		if (stringyfiable instanceof MarkdownSerializable) {
			throw new IllegalArgumentException("Text nodes do not accept markdown elements.");
		}
		this.stringyfiable = stringyfiable;
	}

	abstract protected String getPredecessor();

	abstract protected String getSuccessor();

	@Override
	public Object getStringyfiable() {
		return stringyfiable;
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final String text = getPredecessor() + options.stringify(this) + getSuccessor();
		// Covers UNIX and Windows
		return StringUtil.splitLines(text);
	}

}
