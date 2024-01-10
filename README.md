Java Markdown Builder
=====================

A lightweight library for generating well-formed markdown. No external dependencies except JUnit 5 (Jupiter) are used. [MIT license](/LICENSE.txt).  
  
The [README.md](/README.md) you are currently reading is generated with Markdown Builder.

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
			.plainText("Span elements can be nested. ").startEmphasis(Type.ITALIC) //
			.plainText("This is italic text ") //
			.emphasis(Type.BOLD, "followed by italic and bold text") //
			.plainText(" and finally italic text again.") //
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
public Document heading() {
	return Document.start() //
			.heading(1, "This is a level 1 heading") //
			.paragraph("This is a paragraph below the first heading")//
			.startHeading(2).plainText("This is a level 2 heading").end() //
			.startHeading(3).plainText("This is a level 3 heading").end() //
			.paragraph("This is a paragraph below the third heading") //
			.build();
}
```

### Markdown

```markdown
This is a level 1 heading
=========================

This is a paragraph below the first heading

This is a level 2 heading
-------------------------

### This is a level 3 heading

This is a paragraph below the third heading
```

### Rendered

This is a level 1 heading
=========================

This is a paragraph below the first heading

This is a level 2 heading
-------------------------

### This is a level 3 heading

This is a paragraph below the third heading

Horizontal Rules
----------------

### Java Code

```java
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
```

### Markdown

```markdown
This is a paragraph followed by a horizontal rule.

---

Different rule characters (to account for differences in markdown dialects) are possible as well. You can set your own standard via ``` MarkdownSerializationOptions ```.

***

___
```

### Rendered

This is a paragraph followed by a horizontal rule.

---

Different rule characters (to account for differences in markdown dialects) are possible as well. You can set your own standard via ``` MarkdownSerializationOptions ```.

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
			.quote() // all we add from here is within quotes
			.paragraph("This is a quoted paragraph.") //
			.paragraph("It is followed by another quoted paragraph.") //
			.unquote() // close the quotes
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

### Java Code

```java
public UnorderedList codeblocks() {
	return new UnorderedListBuilder<Void>()//
			.item("The following list item contains a block of Java code") //
			.startItem() // start item
			.paragraph("Item containing code block:") //
			.codeBlock("// Java Code\n" //
					+ "public static void main(String[] args) {\n" //
					+ "	System.out.println(\"Hello World!\"\n" //
					+ "}")
			.end() // end item
			.item("An item following the code block") //
			.build();
}
```

### Markdown

```markdown
*   The following list item contains a block of Java code
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

*   The following list item contains a block of Java code
*   Item containing code block:
    
    ```
    // Java Code
    public static void main(String[] args) {
    	System.out.println("Hello World!"
    }
    ```
*   An item following the code block

Code
----

### Java Code

```java
public Document code() {
	return Document.start() //
			.startParagraph() //
			.plainText("This is a simple text containing a Java code snippet.\n" //
					+ "The snippet is ")
			.code("System.out.println(\"Hello World!\")") //
			.end() //
			.build();
}
```

### Markdown

```markdown
This is a simple text containing a Java code snippet.  
The snippet is ``` System.out.println("Hello World!") ```
```

### Rendered

This is a simple text containing a Java code snippet.  
The snippet is ``` System.out.println("Hello World!") ```

Unordered Lists
---------------

The first block element appended to a list item must always be a paragraph. Meaning text only. But further block elements can be added and they can be of any type. They will align nicely with the list items indent.

### Java Code

```java
public UnorderedList unorderedLists() {
	return new UnorderedListBuilder<Void>() //
			.item("One item of the unordered list.") //
			.item("Another item.") //
			.startItem().startParagraph().plainText("Third item ") //
			.emphasis(Type.BOLD, "with bold Text").end() //
			.quote().paragraph("A quoted paragraph in the third item.") //
			.unquote().end() // end list item
			.build(); // end list
}
```

### Markdown

```markdown
*   One item of the unordered list.
*   Another item.
*   Third item **with bold Text**
    
    > A quoted paragraph in the third item.
```

### Rendered

*   One item of the unordered list.
*   Another item.
*   Third item **with bold Text**
    
    > A quoted paragraph in the third item.

Ordered Lists
-------------

### Java Code

```java
public OrderedList orderedLists() {
	return new OrderedListBuilder<Void>() //
			.item("Item 0") //
			.startItem().startParagraph().plainText("Item 1 ") //
			.emphasis(Type.STRIKETHROUGH, "containing text with strikethrough") //
			.end() // end paragraph
			.end() // end item
			.item("Item 2") //
			.build();
}
```

### Markdown

```markdown
0.  Item 0
1.  Item 1 ~~containing text with strikethrough~~
2.  Item 2
```

### Rendered

0.  Item 0
1.  Item 1 ~~containing text with strikethrough~~
2.  Item 2

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
					+ "are not rendered as emphasis. Paths like c:\\temp\\foo.bar " //
					+ "are safe. The ")
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

Links
-----

### Java Code

```java
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
```

### Markdown

```markdown
This is a paragraph containing inline type hyperlinks.  
The first link is [https://www.google.com](https://www.google.com).  
Links can also contain a title attribute which is shown instead of  
the link: [Go to GitHub](https://github.com)
```

### Rendered

This is a paragraph containing inline type hyperlinks.  
The first link is [https://www.google.com](https://www.google.com).  
Links can also contain a title attribute which is shown instead of  
the link: [Go to GitHub](https://github.com)

Images
------

Note: This is a non-standard element.

### Java Code

```java
public Document images() {
	return Document.start() //
			.append(new Image("src/main/resources/markdown-builder.svg", "Markdown Builder Logo")) //
			.build();
}
```

### Markdown

```markdown
![Markdown Builder Logo](src/main/resources/markdown-builder.svg)
```

### Rendered

![Markdown Builder Logo](src/main/resources/markdown-builder.svg)

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
public static List<Method> sampleMethods() {
	return Arrays.stream(Samples.class.getDeclaredMethods())
			// Only methods that are marked as Sample methods
			.filter(m -> m.getAnnotation(Sample.class) != null)
			/**
			 * By definition "sample" methods have no parameters and return
			 * a MarkdownSerilizable. Skip all methods that do not meet
			 * those criteria.
			 */
			.filter(m -> {
				// Only no-arg methods
				if (m.getParameterCount() > 0) {
					return false;
				}
				// Only BlockElements or SpanElements
				if (BlockElement.class.isAssignableFrom(m.getReturnType())) {
					return true;
				}
				if (SpanElement.class.isAssignableFrom(m.getReturnType())) {
					return true;
				}
				return false;
			})
			// Sort by order defined by annotation
			.sorted(Comparator.comparingInt(m -> m.getAnnotation(Sample.class).order()))
			// Capture the sorted list of methods.
			.collect(Collectors.toList());
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