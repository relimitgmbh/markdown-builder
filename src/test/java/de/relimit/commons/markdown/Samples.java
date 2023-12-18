package de.relimit.commons.markdown;

import static de.relimit.commons.markdown.util.MD.cell;
import static de.relimit.commons.markdown.util.MD.italic;
import static de.relimit.commons.markdown.util.MD.row;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.codeblock.Language;
import de.relimit.commons.markdown.blockelement.list.TaskListBuilder;
import de.relimit.commons.markdown.blockelement.list.UnorderedList;
import de.relimit.commons.markdown.blockelement.list.UnorderedListBuilder;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.table.Table;
import de.relimit.commons.markdown.blockelement.table.TableBuilder;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.configuration.OptionsBuilder;
import de.relimit.commons.markdown.converter.Escaper;
import de.relimit.commons.markdown.document.Document;
import de.relimit.commons.markdown.span.emphasis.Emphasis.Type;
import de.relimit.commons.markdown.util.MD;

/**
 * This class is used by {@link Readme} to produce the README.md Github
 * documentation file. Every method annotated with {@link Sample} will result in
 * an example chapter in the readme. Certain limitations apply because the
 * source code (.java file) is parsed so the source code can appear in the
 * documentation. And properly parsing java code for that purpose would have
 * been a joyless job.
 * <p>
 * <ul>
 * <li>Only methods annotated with {@link Sample} are processed. All others are
 * ignored.</li>
 * <li>Everything immediately before {@link Sample} is ignored, but everything
 * between {@link Sample} and the line containing the method name is included.
 * This means one can add comments to the method which will appear in the
 * documentation.</li>
 */
public class Samples {

	public static final String PROPERTIES_FILE = "samples.properties";

	public static final String PROPERTY_KEY_NAMESPACE = "examples.chapters";
	public static final String PROPERTY_KEY_SUFFIX_HEADING = "heading";
	public static final String PROPERTY_KEY_SUFFIX_INTRO = "intro";

	@Sample(order = 10, key = "paragraphs")
	public Document paragraphs() {
		return Document.start()
				.paragraph("This is a paragraph containing plain text. Line breaks \n"
						+ "will lead to new lines \nwithin the paragraph.") //
				.paragraph("This is the second paragraph. It is separated from the previous one " + "by a blank line.") //
				.build();
	}

	@Sample(order = 20, key = "emphasis")
	public Paragraph emphasis() {
		return MD.startParagraph() //
				.emphasis(Type.BOLD, "This is bold.").newLine() //
				.emphasis(Type.STRIKETHROUGH, "This is strikethrough.").newLine() //
				.emphasis(Type.ITALIC, "This is italic.").newLine() //
				.startEmphasis(Type.BOLD).plainText("Span elements can be nested. This is bold text ")
				.emphasis(Type.ITALIC, "followed by bold and italic text").plainText(" and finally bold text again.")
				.end() // end emphasis
				.build(); // end paragraph
	}

	@Sample(order = 30, key = "taskLists")
	public BlockElement taskLists() {
		return new TaskListBuilder<Void>() //
				.item("This task is completed.", true) //
				.startItem().startParagraph().plainText("This task is pending but it has nice ")
				.emphasis(Type.BOLD, "bold formatted text").plainText(" going for it.") //
				.end() // end paragraph
				.end() // end task item
				.build(); // end list
	}

	@Sample(order = 40, key = "lists")
	public UnorderedList lists() {
		return new UnorderedListBuilder<Void>() //
				.item("First item.") //
				.item("Second item.") //
				.startItem().paragraph("Third item.").paragraph("Another paragraph of the third item.").quote()
				.paragraph("This is a quoted paragraph of the third item.").unquote()
				.codeBlock("// This is a code block of the third item.", Language.JAVA) //
				.end() // end list item
				.build(); // end list
	}

	@Sample(order = 50, key = "escaping", escape = true)
	public Document escaping() {
		return Document.start() //
				.startParagraph() //
				.plainText("Markdown characters are automatically escaped by "
						+ "default. This means that characters like * or # "
						+ "are not rendered as emphasis. Paths like c:\\temp\\foo.bar " + "are safe. The ")
				.code(Escaper.class).plainText(" can be configured via ") //
				.code(MarkdownSerializationOptions.class).plainText(".") //
				.end() // end paragraph
				.build();
	}

	@Sample(order = 50, key = "customRenderer")
	public Table customRenderer() throws MarkdownSerializationException {
		final MarkdownSerializationOptions options = new OptionsBuilder() //
				.stringifier(Boolean.class, (e, o) -> o ? "&#128994;" : "&#128308;") //
				.build();
		return new TableBuilder<Void>() //
				.defaultOptions(options) //
				.append(row("Status", "Message")) // header
				.append(row(true, "This is fine.")) //
				.append(row(false, "Abandon ship!")) //
				.build();
	}

	@Sample(order = 200, key = "md")
	// import static de.relimit.commons.markdown.util.MD.cell;
	// import static de.relimit.commons.markdown.util.MD.italic;
	// import static de.relimit.commons.markdown.util.MD.row;
	public Table md() {
		return new TableBuilder<Void>() //
				.append(row("Heading 1", "Heading 2")) //
				.append(row("Cell 1.1", "Cell 1.2")) //
				.append(row(cell("Cell 2.1"), cell(italic("Cell 2.2")))) //
				.build();
	}

}
