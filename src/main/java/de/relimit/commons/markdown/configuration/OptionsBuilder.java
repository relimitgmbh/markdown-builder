package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRuleCharacter;
import de.relimit.commons.markdown.converter.ConfigurableEscaper;
import de.relimit.commons.markdown.converter.ConfigurableStringifier;
import de.relimit.commons.markdown.converter.Escaper;
import de.relimit.commons.markdown.converter.Stringifier;
import de.relimit.commons.markdown.span.textual.Textual;

public class OptionsBuilder {

	private MarkdownSerializationOptions options;

	private ConfigurableStringifier configurableStringifier;

	private ConfigurableEscaper configurableEscaper;

	public OptionsBuilder() {
		this.options = new MarkdownSerializationOptions();
	}

	public OptionsBuilder(MarkdownSerializationOptions options) {
		/*
		 * The serializer is not cloned because it could be any implementation.
		 * However it will be a completely new one once serializers are added by
		 * this builder.
		 */
		this.options = new MarkdownSerializationOptions(options);
	}

	public MarkdownSerializationOptions build() {
		if (configurableStringifier != null) {
			options.setSerializer(configurableStringifier);
		}
		if (configurableEscaper != null) {
			options.setEscaper(configurableEscaper);
		}
		return options;
	}

	private ConfigurableStringifier getConfigurablStringifier() {
		if (configurableStringifier == null) {
			configurableStringifier = new ConfigurableStringifier();
		}
		return configurableStringifier;
	}

	public <T, E extends T> OptionsBuilder stringifier(Class<T> clazz, Stringifier<E> serializer) {
		getConfigurablStringifier().register(clazz, serializer);
		return this;
	}

	public <T, E extends T> OptionsBuilder defaultStringifier(Stringifier<?> serializer) {
		getConfigurablStringifier().registerDefault(serializer);
		return this;
	}

	private ConfigurableEscaper getConfigurablEscaper() {
		if (configurableEscaper == null) {
			configurableEscaper = new ConfigurableEscaper();
		}
		return configurableEscaper;
	}

	public OptionsBuilder escaper(Class<? extends Textual> clazz, Escaper escaper) {
		getConfigurablEscaper().register(clazz, escaper);
		return this;
	}

	public OptionsBuilder defaultEscaper(Escaper escaper) {
		getConfigurablEscaper().registerDefault(escaper);
		return this;
	}

	public OptionsBuilder lineSeparator(String separator) {
		options.setLineSeparator(separator);
		return this;
	}

	public OptionsBuilder tableCellLineSeparator(String separator) {
		options.setTableCellLineSeparator(separator);
		return this;
	}

	public OptionsBuilder defaultCodeBlockLanguage(CodeBlockLanguage language) {
		options.setDefaultCodeBlockLangauge(language);
		return this;
	}

	public OptionsBuilder codeFences(Fences fences) {
		options.setCodeFences(fences);
		return this;
	}

	public OptionsBuilder defaultHorizontalRuleCharacter(HorizontalRuleCharacter character) {
		options.setDefaultHorizontalRuleCharacter(character);
		return this;
	}

}
