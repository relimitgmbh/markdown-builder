package de.relimit.commons.markdown.blockelement.admonition;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.SpanElement;
import de.relimit.commons.markdown.span.SpanElementNode;

/**
 * The title can take three forms:
 * <ol>
 * <li>Default title according to the admonition's {@link Type}. Set
 * {@link Title#swallowIfEmpty} to <code>true</code> and do not add any
 * content.</li>
 * <li>No title at all. No custom title is set but it should also not be the
 * default title. Set {@link Title#swallowIfEmpty} to <code>false</code> and do
 * not add any content.</li>
 * <li>Custom title. {@link Title#swallowIfEmpty} does not matter (set to
 * <code>false</code> to be safe in case it is empty after all) and add your
 * custom title.</li>
 * </ol>
 */
public class Title extends SpanElementNode implements BlockElement {

	// empty and swallowed (nonexistent)
	public static final Title DEFAULT_TITLE = new Title(false);

	// empty and not swallowed (shows empty quotes "")
	public static final Title NO_TITLE = new Title(true);

	private boolean quote;

	private Title(boolean quote) {
		this.quote = quote;
	}

	public Title(Object stringifyable) {
		super(stringifyable);
		this.quote = true;
	}

	public Title(SpanElement... elements) {
		super(elements);
		this.quote = true;
	}

	public Title(SpanElement element) {
		super(element);
		this.quote = true;
	}

	public Title(String text) {
		super(text);
		this.quote = true;
	}

	@Override
	public Fences getFences(MarkdownSerializationOptions options) {
		if (quote) {
			return Fences.between("\"", "\"");
		}
		return Fences.none();
	}

	@Override
	protected boolean swallowIfEmpty() {
		return false;
	}

}
