package de.relimit.commons.markdown.blockelement.admonition;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.MarkedBlockElementNode;
import de.relimit.commons.markdown.blockelement.list.UnorderedListItem;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.document.Document;
import de.relimit.commons.markdown.span.textual.PlainText;

/**
 * 
 * 
 * @see <a href=
 *      "https://squidfunk.github.io/mkdocs-material/reference/admonitions/">https://squidfunk.github.io/mkdocs-material/reference/admonitions/</a>
 */
public class Admonition extends MarkedBlockElementNode implements BlockElement {

	public static final Type DEFAULT_TYPE = DefaultType.NOTE;

	public static final Expansion DEFAULT_EXPANSION = Expansion.FIXED;

	public static final Display DEFAULT_DISPLAY = Display.BLOCK;

	private Type type;

	private Expansion expansion;

	private Display display;

	public Admonition() {
		super(0);
	}

	@Override
	protected BlockElement gateKeep(BlockElement element) {
		if (elements.isEmpty() && !(element instanceof Title)) {
			throw new IllegalArgumentException(
					"The first block element added to an admonition must be the " + Title.class.getSimpleName() + ".");
		}
		return element;
	}

	public Type getType() {
		return type != null ? type : DEFAULT_TYPE;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Expansion getExpansion() {
		return expansion != null ? expansion : DEFAULT_EXPANSION;
	}

	public void setExpansion(Expansion expansion) {
		this.expansion = expansion;
	}

	public Display getDisplay() {
		return display != null ? display : DEFAULT_DISPLAY;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	@Override
	protected String getMarker(MarkdownSerializationOptions options) {
		final StringBuilder markerBuilder = new StringBuilder();
		markerBuilder.append(getExpansion().getMarker());
		markerBuilder.append(" ");
		markerBuilder.append(getType().getQualifier());
		getDisplay().getDirective().ifPresent(d -> {
			markerBuilder.append(" ");
			markerBuilder.append(d);
		});
		return markerBuilder.toString();
	}

	public static void main(String[] args) throws MarkdownSerializationException {
		final Admonition admonition = new AdmonitionBuilder<>(new Admonition(), Title.NO_TITLE)
				.expansion(Expansion.EXPANDED).display(Display.INLINE_RIGHT).startTitle().append(new PlainText("Foo"))
				.end().startParagraph().plainText("Foo").end().append(new Paragraph("Foo")).append(new Paragraph("Bar"))
				.build();

		final Document document = Document.start().startUnorderedList().startItem().startParagraph().plainText("sdfdf")
				.end().startAdmonition().expansion(Expansion.EXPANDED).display(Display.INLINE_RIGHT).startTitle()
				.append(new PlainText("Admonition")).end().append(new Paragraph("Foo")).startParagraph()
				.plainText("Bar").end().startUnorderedList().startItem().startParagraph().plainText("List 2").end()
				.startParagraph().plainText("List 2 Paragraph").end().end().startItem().startParagraph()
				.plainText("Foo 2").end().end().end().end().startParagraph().plainText("sdfdf").end()
				.startUnorderedList().startItem().startParagraph().plainText("jsdgfjh").end().startParagraph()
				.plainText("jsdgfjh").end().append(new Paragraph("Foo")).end().end().end().end().build();
		document.setIndentationLevel(3);

		final Document document2 = Document.start().startAdmonition().expansion(Expansion.EXPANDED)
				.display(Display.INLINE_RIGHT).startTitle().append(new PlainText("Foo")).end()
				.append(new Paragraph("Foo")).append(new Paragraph("Bar")).end().build();

		final UnorderedListItem item = new UnorderedListItem(1);
		item.append(new Paragraph("ddfds"));
		item.append(new Paragraph("ddfds"));

		System.out.println(item.serialize());
	}

}
