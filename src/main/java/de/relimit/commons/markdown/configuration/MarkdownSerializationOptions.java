package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRuleCharacter;
import de.relimit.commons.markdown.span.textual.Fences;
import de.relimit.commons.markdown.span.textual.Textual;

public interface MarkdownSerializationOptions {

	TextSerializer<Object> getSerializer();

	TextEscaper getEscaper();

	String getLineSeparator();

	String getTableCellLineSeparator();

	CodeBlockLanguage getDefaultCodeBlockLangauge();

	Fences getCodeFences();

	HorizontalRuleCharacter getDefaultHorizontalRuleCharacter();

	default String stringify(Textual element) throws MarkdownSerializationException {
		String text = getSerializer().serialize(element, element.getStringifyable());
		text = getEscaper().escape(element, text);
		return text;
	}

}
