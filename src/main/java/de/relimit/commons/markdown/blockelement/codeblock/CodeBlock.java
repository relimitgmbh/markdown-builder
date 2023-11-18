package de.relimit.commons.markdown.blockelement.codeblock;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.StringUtil;

public class CodeBlock extends MarkdownElement implements BlockElement {

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

	private CodeBlockLanguage language = Language.UNKNOWN;

	private Object strigyfiable;

	public CodeBlock(Object strigyfiable) {
		this.strigyfiable = strigyfiable;
	}

	public CodeBlock(Object strigyfiable, CodeBlockLanguage language) {
		this(strigyfiable);
		this.language = language;
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final String code = options.getPlainTextSerializer().serialize(this, strigyfiable);
		if (code.isBlank()) {
			return new ArrayList<>();
		}
		final List<String> lines = new ArrayList<>();
		lines.add("```" + language.getName());
		// We checked that the code is not empty. Should return at least one line.
		lines.addAll(StringUtil.splitLines(code));
		lines.add("```");
		return lines;
	}

	public CodeBlockLanguage getLanguage() {
		return language;
	}

	public void setLanguage(CodeBlockLanguage language) {
		this.language = language;
		invalidateSerialized();
	}
}
