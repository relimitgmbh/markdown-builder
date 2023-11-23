package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRuleCharacter;
import de.relimit.commons.markdown.span.textual.Textual;

public interface MarkdownSerializationOptions {

	TextSerializer<Object> getSerializer();

	TextEscaper getEscaper();

	String getLineSeparator();

	String getTableCellLineSeparator();

	CodeBlockLanguage getDefaultCodeBlockLangauge();

	Fences getCodeFences();

	HorizontalRuleCharacter getDefaultHorizontalRuleCharacter();

	/**
	 * Sugar nethod that performs serialization and escaping in one go. The
	 * options are passed through all calls to
	 * {@link MarkdownSerializable#serialize(MarkdownSerializationOptions)} so
	 * this method is conveniently available where it is needed.
	 * 
	 * @param element
	 * @return
	 * @throws MarkdownSerializationException
	 */
	default String stringify(Textual element) throws MarkdownSerializationException {
		String text = getSerializer().serialize(element, element.getStringifyable());
		text = getEscaper().escape(element, text);
		return text;
	}

}
