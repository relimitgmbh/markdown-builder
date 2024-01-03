Java Markdown Builder
=====================

A lightweight library for generating pretty printed markdown. No external dependencies except JUnit are used. This README.md file was generated with Markdown Builder.

Examples
========

Paragraphs and Line Breaks
--------------------------

The two most important elements when working with Markdown Builder are block elements and span elements. Block elements are separated from each other by a blank line and render as a paragraph.

### Java Code

```java
public Document paragraphs() {
	return Document.start()
			.paragraph("This is a paragraph containing plain text. Line breaks \n"
					+ "will lead to new lines \nwithin the paragraph.") //
			.paragraph("This is the second paragraph. It is separated from " //
					+ "the previous one by a blank line.") //
			.build();
}
```

### Markdown

```markdown
This is a paragraph containing plain text. Line breaks   
will lead to new lines   
within the paragraph.

This is the second paragraph. It is separated from the previous one by a blank line.
```

### Rendered

This is a paragraph containing plain text. Line breaks   
will lead to new lines   
within the paragraph.

This is the second paragraph. It is separated from the previous one by a blank line.

Emphasis
--------

### Java Code

```java
public Paragraph emphasis() {
	return MD.startParagraph() //
			.emphasis(Type.BOLD, "This is bold.").newLine() //
			.emphasis(Type.STRIKETHROUGH, "This is strikethrough.").newLine() //
			.emphasis(Type.ITALIC, "This is italic.").newLine() //
			.plainText("Span elements can be nested. ").startEmphasis(Type.ITALIC).plainText("This is italic text ")
			.emphasis(Type.BOLD, "followed by italic and bold text").plainText(" and finally italic text again.")
			.end() // end emphasis
			.build(); // end paragraph
}
```

### Markdown

```markdown
**This is bold.**  
~~This is strikethrough.~~  
_This is italic._  
Span elements can be nested. _This is italic text **followed by italic and bold text** and finally italic text again._
```

### Rendered

**This is bold.**  
~~This is strikethrough.~~  
_This is italic._  
Span elements can be nested. _This is italic text **followed by italic and bold text** and finally italic text again._

Headers
-------

### Java Code

```java
public Document headers() {
	return Document.start() //
			.heading(1, "This is a level 1 header") //
			.paragraph("This is a paragraph of the first Header")//
			.startHeading(2).plainText("This is a level 2 header") //
			.end() //
			.startHeading(3).plainText("This is the third header") //
			.end() //
			.paragraph("This is a paragraph to the third header") //
			.build();
}
```

### Markdown

```markdown
This is a level 1 header
========================

This is a paragraph of the first Header

This is a level 2 header
------------------------

### This is the third header

This is a paragraph to the third header
```

### Rendered

This is a level 1 header
========================

This is a paragraph of the first Header

This is a level 2 header
------------------------

### This is the third header

This is a paragraph to the third header

Horizontal Rules
----------------

### Java Code

```java
public BlockElement horizontalRules() {
	return Document.start() //
			.paragraph("This is a paragraph followed by a horizontal rule") //
			.horizontalRule() //
			.paragraph("Asterisk or Underscore can be chosen as well:") //
			.horizontalRule(RuleCharacter.ASTERISK) //
			.horizontalRule(RuleCharacter.UNDERSCORE) //
			.build();
}
```

### Markdown

```markdown
This is a paragraph followed by a horizontal rule

---

Asterisk or Underscore can be chosen as well:

***

___
```

### Rendered

This is a paragraph followed by a horizontal rule

---

Asterisk or Underscore can be chosen as well:

***

___

Tables
------

### Java Code

```java
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
```

### Markdown

```markdown
| Heading column 1 | Heading column 2 | Heading column 3 |
| ----------------:|:----------------:|:---------------- |
|        alignment |        of        | columns          |
|            right |      center      | left             |
```

### Rendered

| Heading column 1 | Heading column 2 | Heading column 3 |
| ----------------:|:----------------:|:---------------- |
|        alignment |        of        | columns          |
|            right |      center      | left             |

Blockquotes
-----------

### Java Code

```java
public Document blockquotes() {
	return Document.start() //
			.paragraph("This is a normal paragraph followed by a blockquote.") //
			.quote().paragraph("This is a quoted paragraph.") //
			.paragraph("It is followed by another quoted paragraph.") //
			.unquote() //
			.build();
}
```

### Markdown

```markdown
This is a normal paragraph followed by a blockquote.

> This is a quoted paragraph.
> 
> It is followed by another quoted paragraph.
```

### Rendered

This is a normal paragraph followed by a blockquote.

> This is a quoted paragraph.
> 
> It is followed by another quoted paragraph.

Code Blocks
-----------

Code blocks are not easy to visualize in this README.md file, as each example is already printed in three different code blocks (see each example of `Java Code`, `Markdown` and `Rendered`)

### Java Code

```java
public UnorderedList codeblocks() {
	return new UnorderedListBuilder<Void>()//
			.item("The following list item will show a code block in Java Code") //
			.startItem().paragraph("Item containing code block:").codeBlock("// Java Code\n" //
					+ "public static void main(String[] args) {\n" //
					+ "System.out.println(\"Hello World!\"\n" //
					+ "}")
			.end() //
			.item("An item following the code block") //
			.build();
}
```

### Markdown

```markdown
*   The following list item will show a code block in Java Code
*   Item containing code block:
    
    ```
    // Java Code
    public static void main(String[] args) {
    System.out.println("Hello World!"
    }
    ```
*   An item following the code block
```

### Rendered

*   The following list item will show a code block in Java Code
*   Item containing code block:
    
    ```
    // Java Code
    public static void main(String[] args) {
    System.out.println("Hello World!"
    }
    ```
*   An item following the code block

Lists
-----

The first block element appended to a list item must always be a paragraph. Meaning text only. But further block elements can be added and they can be of any type. They will align nicely with the list items indent.

### Java Code

```java
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
```

### Markdown

```markdown
*   First item.
*   Second item.
*   Third item.
    
    Another paragraph of the third item.
    
    > This is a quoted paragraph of the third item.
    
    ```java
    // This is a code block of the third item.
    ```
```

### Rendered

*   First item.
*   Second item.
*   Third item.
    
    Another paragraph of the third item.
    
    > This is a quoted paragraph of the third item.
    
    ```java
    // This is a code block of the third item.
    ```

Task Lists
----------

Note: This is a non-standard element and might not be supported by all markdown renderers.

### Java Code

```java
public BlockElement taskLists() {
	return new TaskListBuilder<Void>() //
			.item("This task is completed.", true) //
			.startItem().startParagraph().plainText("This task is pending but it has nice ")
			.emphasis(Type.BOLD, "bold formatted text").plainText(" going for it.") //
			.end() // end paragraph
			.end() // end task item
			.build(); // end list
}
```

### Markdown

```markdown
- [x] This task is completed.
- [ ] This task is pending but it has nice **bold formatted text** going for it.
```

### Rendered

- [x] This task is completed.
- [ ] This task is pending but it has nice **bold formatted text** going for it.

Escaping
--------

### Java Code

```java
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
```

### Markdown

```markdown
Markdown characters are automatically escaped by default\. This means that characters like \* or \# are not rendered as emphasis\. Paths like c:\\temp\\foo\.bar are safe\. The ``` Escaper ``` can be configured via ``` MarkdownSerializationOptions ```\.
```

### Rendered

Markdown characters are automatically escaped by default. This means that characters like * or # are not rendered as emphasis. Paths like c:\temp\foo.bar are safe. The ``` Escaper ``` can be configured via ``` MarkdownSerializationOptions ```.

Custom Renderer
---------------

### Java Code

```java
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
```

### Markdown

```markdown
| Status    | Message       |
| --------- | ------------- |
| &#128994; | This is fine. |
| &#128308; | Abandon ship! |
```

### Rendered

| Status    | Message       |
| --------- | ------------- |
| &#128994; | This is fine. |
| &#128308; | Abandon ship! |

Static Utility Class `MD`
-------------------------

The utility class `MD` features many static methods for convenient element building. Add static imports as shown in the comments in the sample method for even less verbose markdowning.

### Java Code

```java
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
```

### Markdown

```markdown
| Heading 1 | Heading 2  |
| --------- | ---------- |
| Cell 1.1  | Cell 1.2   |
| Cell 2.1  | _Cell 2.2_ |
```

### Rendered

| Heading 1 | Heading 2  |
| --------- | ---------- |
| Cell 1.1  | Cell 1.2   |
| Cell 2.1  | _Cell 2.2_ |