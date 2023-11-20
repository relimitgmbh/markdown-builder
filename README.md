Java Markdown Builder
=====================

A lightweight library for generating pretty printed markdown. No external dependencies except JUnit are used. This README.md file was generated with Markdown Builder.

Examples
========

Emphasis
--------

### Java Code

```java
public DocumentBuilder emphasis() throws MarkdownSerializationException {
	return new DocumentBuilder().startParagraph() //
			.plainText("This is normal.").newLine() //
			.emphasis(Type.BOLD, "This is bold.").newLine() //
			.emphasis(Type.STRIKETHROUGH, "This is strikethrough.").newLine() //
			.emphasis(Type.ITALIC, "This is italic.").newLine() //
			.startEmphasis(Type.BOLD).plainText("Span elements can be nested. This is bold text ")
			.emphasis(Type.ITALIC, "followed by bold and italic text").plainText(" and finally bold text again.")
			.end() // end emphasis
			.end(); // end paragraph
}
```

### Markdown

```markdown
This is normal.  
**This is bold.**  
~~This is strikethrough.~~  
_This is italic._  
**Span elements can be nested. This is bold text _followed by bold and italic text_ and finally bold text again.**
```

### Rendered

This is normal.  
**This is bold.**  
~~This is strikethrough.~~  
_This is italic._  
**Span elements can be nested. This is bold text _followed by bold and italic text_ and finally bold text again.**

Paragraphs and Line Breaks
--------------------------

Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.

### Java Code

```java
public DocumentBuilder paragraphs() throws MarkdownSerializationException {
	return new DocumentBuilder()
			.paragraph("This is a paragraph containing plain text. Line breaks \n"
					+ "will lead to new lines \nwithin the paragraph.")
			.paragraph("This is the second paragraph. It is separated from the previous one by a blank line.");
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

Task Lists
----------

Note: This is a non-standard element and might not be supported by all markdown renderers.

### Java Code

```java
public DocumentBuilder taskLists() throws MarkdownSerializationException {
	return new DocumentBuilder().startTaskList() //
			.task(false, "This task is pending.") //
			.task(true, "This task is completed.") //
			.startTask(false).plainText("This task is pending but it has nice ")
			.emphasis(Type.BOLD, "bold formatted text").plainText(" going for it.").end().end();
}
```

### Markdown

```markdown
- [ ] This task is pending.
- [x] This task is completed.
- [ ] This task is pending but it has nice **bold formatted text** going for it.
```

### Rendered

- [ ] This task is pending.
- [x] This task is completed.
- [ ] This task is pending but it has nice **bold formatted text** going for it.