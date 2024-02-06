package de.relimit.commons.markdown;

import static de.relimit.commons.markdown.util.MD.cell;
import static de.relimit.commons.markdown.util.MD.italic;
import static de.relimit.commons.markdown.util.MD.row;

import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.blockelement.image.Image;
import de.relimit.commons.markdown.blockelement.list.OrderedList;
import de.relimit.commons.markdown.blockelement.list.OrderedListBuilder;
import de.relimit.commons.markdown.blockelement.list.TaskListBuilder;
import de.relimit.commons.markdown.blockelement.list.UnorderedList;
import de.relimit.commons.markdown.blockelement.list.UnorderedListBuilder;
import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRule.RuleCharacter;
import de.relimit.commons.markdown.blockelement.table.Alignment;
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
 * <li>Only methods annotated with {@link Sample} are processed. Do not add
 * unrelated methods as the source code parser is not very sophisticated and
 * might stumble.</li>
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
				.paragraph("This is the second paragraph. It is separated from " //
						+ "the previous one by a blank line.") //
				.build();
	}

	@Sample(order = 20, key = "emphasis")
	public Paragraph emphasis() {
		return MD.startParagraph() //
				.emphasis(Type.BOLD, "This is bold.").newLine() //
				.emphasis(Type.STRIKETHROUGH, "This is strikethrough.").newLine() //
				.emphasis(Type.ITALIC, "This is italic.").newLine() //
				.plainText("Span elements can be nested. ").startEmphasis(Type.ITALIC) //
				.plainText("This is italic text ") //
				.emphasis(Type.BOLD, "followed by italic and bold text") //
				.plainText(" and finally italic text again.") //
				.end() // end emphasis
				.build(); // end paragraph
	}

	@Sample(order = 30, key = "headings")
	public Document heading() {
		return Document.start() //
				.heading(1, "This is a level 1 heading") //
				.paragraph("This is a paragraph below the first heading")//
				.startHeading(2).plainText("This is a level 2 heading").end() //
				.startHeading(3).plainText("This is a level 3 heading").end() //
				.paragraph("This is a paragraph below the third heading") //
				.build();
	}

	@Sample(order = 40, key = "horizontalRules")
	public BlockElement horizontalRules() {
		return Document.start() //
				.paragraph("This is a paragraph followed by a horizontal rule.") //
				.horizontalRule() //
				.startParagraph().plainText("Different rule characters (to account " //
						+ "for differences in markdown dialects) are possible as " // 
						+ "well. You can set your own standard via ") //
				.code(MarkdownSerializationOptions.class).plainText(".") //
				.end() //
				.horizontalRule(RuleCharacter.ASTERISK) //
				.horizontalRule(RuleCharacter.UNDERSCORE) //
				.build();
	}

	@Sample(order = 50, key = "tables")
	public Table tables() {
		return new TableBuilder<Void>() //
				.align(Alignment.RIGHT, Alignment.CENTER, Alignment.LEFT) // align each column
				.startRow().startCell().plainText("Heading column 1").end() //
				.startCell().plainText("Heading column 2").end() //
				.startCell().plainText("Heading column 3").end().end() // 
				.append(row("alignment", "of", "columns")) // MD utility class methods
				.append(row("right", "center", "left")) //
				.build();
	}

	@Sample(order = 60, key = "blockquotes")
	public Document blockquotes() {
		return Document.start() //
				.paragraph("This is a normal paragraph followed by a blockquote.") //
				.quote() // all we add from here is within quotes
				.paragraph("This is a quoted paragraph.") //
				.paragraph("It is followed by another quoted paragraph.") //
				.unquote() // close the quotes
				.build();
	}

	@Sample(order = 70, key = "codeblocks")
	public UnorderedList codeblocks() {
		return new UnorderedListBuilder<Void>()//
				.item("The following list item contains a block of Java code") //
				.startItem("Item containing code block:") // start item
				.codeBlock("// Java Code\n" //
						+ "public static void main(String[] args) {\n" //
						+ "	System.out.println(\"Hello World!\"\n" //
						+ "}")
				.end() // end item
				.item("An item following the code block") //
				.build();
	}

	@Sample(order = 80, key = "code")
	public Document code() {
		return Document.start() //
				.startParagraph() //
				.plainText("This is a simple text containing a Java code snippet.\n" //
						+ "The snippet is ")
				.code("System.out.println(\"Hello World!\")") //
				.end() //
				.build();
	}

	@Sample(order = 90, key = "unorderedLists")
	public UnorderedList unorderedLists() {
		return new UnorderedListBuilder<Void>() //
				.item("One item of the unordered list.") //
				.item("Another item.") //
				.startItem(MD.startParagraph().plainText("Third item ") //
						.emphasis(Type.BOLD, "with bold Text").end()) // end title paragraph
				.quote().paragraph("A quoted paragraph in the third item.") //
				.unquote().end() // end list item
				.build(); // end list
	}

	@Sample(order = 100, key = "orderedLists")
	public OrderedList orderedLists() {
		return new OrderedListBuilder<Void>() //
				.item("Item 0") //
				.startItem(MD.startParagraph().plainText("Item 1 ") //
						.emphasis(Type.STRIKETHROUGH, "containing text with strikethrough") //
						.end()) // end title paragraph
				.end() // end item
				.item("Item 2") //
				.build();
	}

	@Sample(order = 110, key = "taskLists")
	public BlockElement taskLists() {
		return new TaskListBuilder<Void>() //
				.item("This task is completed.", true) //
				.startItem(MD.startParagraph().plainText("This task is pending but it has nice ")
						.emphasis(Type.BOLD, "bold formatted text").plainText(" going for it.") //
						.end()) // end title paragraph
				.end() // end task item
				.build(); // end list
	}

	@Sample(order = 120, key = "escaping", escape = true)
	public Document escaping() {
		return Document.start() //
				.startParagraph() //
				.plainText("Markdown characters are automatically escaped by "
						+ "default. This means that characters like * or # "
						+ "are not rendered as emphasis. Paths like c:\\temp\\foo.bar " //
						+ "are safe. The ")
				.code(Escaper.class).plainText(" can be configured via ") //
				.code(MarkdownSerializationOptions.class).plainText(".") //
				.end() // end paragraph
				.build();
	}

	@Sample(order = 130, key = "links")
	public Document links() {
		return Document.start() //
				.startParagraph() //
				.plainText("This is a paragraph containing inline type hyperlinks.\n" // 
						+ "The first link is ") //
				.hyperlink("https://www.google.com") //
				.plainText(".\nLinks can also contain a title attribute which is shown " //
						+ "instead of\nthe link: ") //
				.hyperlink("https://github.com", "Go to GitHub") //
				.end() //
				.build();
	}

	@Sample(order = 140, key = "images")
	public Document images() {
		return Document.start() //
				.append(new Image("src/main/resources/markdown-builder.svg", "Markdown Builder Logo")) //
				.build();
	}

	@Sample(order = 150, key = "customRenderer")
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
