package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRuleCharacter;

public class OptionsBuilder {

	private MarkdownSerializationOptionsImpl options;

	private ConfigurableSerializer configurableSerializer;

	public OptionsBuilder() {
		this.options = new MarkdownSerializationOptionsImpl();
	}

	public OptionsBuilder(MarkdownSerializationOptions options) {
		/*
		 * The serializer is not cloned because it could be any implementation.
		 * However it will be a completely new one once serializers are added by
		 * this builder.
		 */
		this.options = new MarkdownSerializationOptionsImpl(options);
	}

	public MarkdownSerializationOptions build() {
		if (configurableSerializer != null) {
			options.setSerializer(configurableSerializer);
		}
		return options;
	}

	private ConfigurableSerializer getConfigurablSerializer() {
		if (configurableSerializer == null) {
			configurableSerializer = new ConfigurableSerializer();
		}
		return configurableSerializer;
	}

	public <T, E extends T> OptionsBuilder registerSerializer(Class<T> clazz, TextSerializer<E> serializer) {
		getConfigurablSerializer().registerSerializer(clazz, serializer);
		return this;
	}

	public <T, E extends T> OptionsBuilder registerDefaultSerializer(TextSerializer<?> serializer) {
		getConfigurablSerializer().registerDefaultSerializer(serializer);
		return this;
	}

	public OptionsBuilder escaper(TextEscaper escaper) {
		options.setEscaper(escaper);
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
