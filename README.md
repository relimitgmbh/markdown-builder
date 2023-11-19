Java Markdown Builder
=====================

A lightweight library for generating pretty printed markdown. No external dependencies except JUnit are used. This README.md file was generated with Markdown Builder.

Examples
========

Paragraphs and Line Breaks
--------------------------

```java
public DocumentBuilder paragraph() throws MarkdownSerializationException {
	return new DocumentBuilder()
			.paragraph("This is a paragraph containing plain text. Line breaks \n"
					+ "will lead to new lines \nwithin the paragraph.")
			.paragraph("This is the second paragraph. It is separated from the previous one by a blank line.");
}
```

```markdown
This is a paragraph containing plain text. Line breaks   
will lead to new lines   
within the paragraph.

This is the second paragraph. It is separated from the previous one by a blank line.
```

Emphasis
--------

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

```markdown
This is normal.  
**This is bold.**  
~~This is strikethrough.~~  
*This is italic.*  
**Span elements can be nested. This is bold text *followed by bold and italic text* and finally bold text again.**
```