package de.relimit.commons.markdown.blockelement.admonition;

import java.util.Optional;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.MarkedBlockElementNode;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

/**
 * @see <a href=
 *      "https://squidfunk.github.io/mkdocs-material/reference/admonitions/">https://squidfunk.github.io/mkdocs-material/reference/admonitions/</a>
 */
public class Admonition extends MarkedBlockElementNode<Title> implements BlockElement {

	public static final Type DEFAULT_TYPE = DefaultType.NOTE;

	public static final Expansion DEFAULT_EXPANSION = Expansion.FIXED;

	public static final Display DEFAULT_DISPLAY = Display.BLOCK;

	private Type type;

	private Expansion expansion;

	private Display display;

	public Admonition(Title title) {
		super(title);
	}

	public Type getType() {
		return type != null ? type : DEFAULT_TYPE;
	}

	public void setType(Type type) {
		this.type = type;
		invalidateSerialized();
	}

	public Expansion getExpansion() {
		return expansion != null ? expansion : DEFAULT_EXPANSION;
	}

	public void setExpansion(Expansion expansion) {
		this.expansion = expansion;
		invalidateSerialized();
	}

	public Display getDisplay() {
		return display != null ? display : DEFAULT_DISPLAY;
	}

	public void setDisplay(Display display) {
		this.display = display;
		invalidateSerialized();
	}

	@Override
	protected Class<Title> getTitleClass() {
		return Title.class;
	}

	@Override
	protected String getMarker(MarkdownSerializationOptions options) {
		final StringBuilder markerBuilder = new StringBuilder();
		markerBuilder
				.append(Optional.ofNullable(expansion).orElse(options.getDefaultAdmonitionExpansion()).getMarker());
		markerBuilder.append(" ");
		markerBuilder.append(Optional.ofNullable(type).orElse(options.getDefaultAdmonitionType()).getQualifier());
		Optional.ofNullable(display).orElse(options.getDefaultAdmonitionDisplay()).getDirective().ifPresent(d -> {
			markerBuilder.append(" ");
			markerBuilder.append(d);
		});
		return markerBuilder.toString();
	}

}
