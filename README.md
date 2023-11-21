Java Markdown Builder
=====================

A lightweight library for generating pretty printed markdown\. No external dependencies except JUnit are used\. This README\.md file was generated with Markdown Builder\.

Examples
========

Paragraphs and Line Breaks
--------------------------

Lorem ipsum dolor sit amet consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua\. At vero eos et accusam et justo duo dolores et ea rebum\. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet\.

### Java Code

```java
public DocumentBuilder paragraphs() throws MarkdownSerializationException {
	return new DocumentBuilder()
			.paragraph("This is a paragraph containing plain text. Line breaks \n"
					+ "will lead to new lines \nwithin the paragraph.")
			.paragraph("This is the second paragraph. It is separated from the previous one " + "by a blank line.");
}
```

### Markdown

```markdown
This is a paragraph containing plain text\. Line breaks   
will lead to new lines   
within the paragraph\.

This is the second paragraph\. It is separated from the previous one by a blank line\.
```

### Rendered

This is a paragraph containing plain text\. Line breaks   
will lead to new lines   
within the paragraph\.

This is the second paragraph\. It is separated from the previous one by a blank line\.

Emphasis
--------

### Java Code

```java
public DocumentBuilder emphasis() throws MarkdownSerializationException {
	return new DocumentBuilder().startParagraph() //
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
**This is bold\.**  
~~This is strikethrough\.~~  
_This is italic\._  
**Span elements can be nested\. This is bold text _followed by bold and italic text_ and finally bold text again\.**
```

### Rendered

**This is bold\.**  
~~This is strikethrough\.~~  
_This is italic\._  
**Span elements can be nested\. This is bold text _followed by bold and italic text_ and finally bold text again\.**

Task Lists
----------

Note: This is a non\-standard element and might not be supported by all markdown renderers\.

### Java Code

```java
public DocumentBuilder taskLists() throws MarkdownSerializationException {
	return new DocumentBuilder().startTaskList() //
			.item("This task is completed.", true) //
			.startItem().startParagraph().plainText("This task is pending but it has nice ")
			.emphasis(Type.BOLD, "bold formatted text").plainText(" going for it.").end() // end paragraph
			.end() // end task item
			.end(); // end task list
}
```

### Markdown

```markdown
- [x] This task is completed\.
- [ ] This task is pending but it has nice **bold formatted text** going for it\.
```

### Rendered

- [x] This task is completed\.
- [ ] This task is pending but it has nice **bold formatted text** going for it\.

Lists
-----

The first block element appended to a list item must always be a paragraph\. Meaning text only\. But further block elements can be added and they can be of any type\. They will align nicely with the list items indent\.

### Java Code

```java
public DocumentBuilder lists() throws MarkdownSerializationException {
	return new DocumentBuilder().startUnorderedList() //
			.item("First item.") //
			.item("Second item.") //
			.startItem().paragraph("Third item.").paragraph("Another paragraph of the third item.").quote()
			.paragraph("This is a quoted paragraph of the third item.").unquote()
			.codeBlock("// This is a code block of the third item.", Language.JAVA).end().end();
}
```

### Markdown

```markdown
*   First item\.
*   Second item\.
*   Third item\.
    
    Another paragraph of the third item\.
    
    > This is a quoted paragraph of the third item\.
    
    ```java
    // This is a code block of the third item.
    ```
```

### Rendered

*   First item\.
*   Second item\.
*   Third item\.
    
    Another paragraph of the third item\.
    
    > This is a quoted paragraph of the third item\.
    
    ```java
    // This is a code block of the third item.
    ```

Escaping
--------

### Java Code

```java
public DocumentBuilder escaping() throws MarkdownSerializationException {
	return new DocumentBuilder().startParagraph().plainText(
			"Markdown characters are automatically escaped by default. This means that characters like * or # are "
					+ "not rendered as emphasis. Paths like c:\\temp\\foo.bar are safe as well. The ")
			.simpleClassName(TextEscaper.class).plainText(" can be " + "configured via ")
			.simpleClassName(MarkdownSerializationOptions.class).plainText(".").code("cont`taminated` ` `code`")
			.end();
}
```

### Markdown

```markdown
Markdown characters are automatically escaped by default\. This means that characters like \* or \# are not rendered as emphasis\. Paths like c:\\temp\\foo\.bar are safe as well\. The ``` TextEscaper ``` can be configured via ``` MarkdownSerializationOptions ```\.``` cont`taminated` ` `code` ```
```

### Rendered

Markdown characters are automatically escaped by default\. This means that characters like \* or \# are not rendered as emphasis\. Paths like c:\\temp\\foo\.bar are safe as well\. The ``` TextEscaper ``` can be configured via ``` MarkdownSerializationOptions ```\.``` cont`taminated` ` `code` ```