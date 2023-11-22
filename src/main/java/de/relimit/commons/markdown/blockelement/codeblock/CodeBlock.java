package de.relimit.commons.markdown.blockelement.codeblock;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.Textual;
import de.relimit.commons.markdown.util.Strings;

public class CodeBlock extends MarkdownElement implements BlockElement, Textual {

	public static final Language DEFAULT_LANGUAGE = Language.UNKNOWN;

	/**
	 * An enum providing some possible {@link CodeBlockLanguage}s for tagging a
	 * code block so the code is properly formatted. The interface exists so
	 * everyone can add additional languages.
	 */
	public static enum Language implements CodeBlockLanguage {

		UNKNOWN(""),
		JAVA("java"),
		MARKDOWN("markdown");

		private String name;

		private Language(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

	}

	private CodeBlockLanguage language;

	private Object stringifyable;

	public CodeBlock(Object strigyfiable) {
		this.stringifyable = strigyfiable;
	}

	public CodeBlock(Object strigyfiable, CodeBlockLanguage language) {
		this(strigyfiable);
		this.language = language;
	}

	@Override
	public Object getStringifyable() {
		return stringifyable;
	}

	/**
	 * @see Code#getPredecessor()
	 */
	@Override
	public String getEscapeCharacters() {
		return "";
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		// TODO escape?
		final String code = options.stringify(this);
		if (code.isBlank()) {
			return new ArrayList<>();
		}
		final List<String> lines = new ArrayList<>();
		lines.add("```" + getLanguage(options).getName());
		// We checked that the code is not empty. Should return at least one line.
		lines.addAll(Strings.splitLines(code));
		lines.add("```");
		return lines;
	}

	private CodeBlockLanguage getLanguage(MarkdownSerializationOptions options) {
		if (language == null) {
			return options.getDefaultCodeBlockLangauge();
		}
		return language;
	}

	public void setLanguage(CodeBlockLanguage language) {
		this.language = language;
		invalidateSerialized();
	}
}
