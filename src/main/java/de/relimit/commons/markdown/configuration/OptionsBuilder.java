package de.relimit.commons.markdown.configuration;

public class OptionsBuilder {

	private MarkdownSerializationOptions options;

	private ConfigurableSerializer configurableSerializer;

	public OptionsBuilder() {
		this.options = new MarkdownSerializationOptions();
	}

	public OptionsBuilder(MarkdownSerializationOptions options) {
		this();
		/*
		 * Serializer is not cloned because it can be any implementation!
		 * However it will be a completely new one once serializers are added by
		 * this builder.
		 */
		this.options = new MarkdownSerializationOptions(options);
	}

	public MarkdownSerializationOptions build() {
		if (configurableSerializer != null) {
			options.setSerializer(configurableSerializer);
		}
		return options;
	}

	public OptionsBuilder lineSeparator(String separator) {
		options.setLineSeparator(separator);
		return this;
	}

	public OptionsBuilder tableCellLineSeparator(String separator) {
		options.setTableCellLineSeparator(separator);
		return this;
	}

	private ConfigurableSerializer getConfigurablSerializer() {
		if (configurableSerializer == null) {
			configurableSerializer = new ConfigurableSerializer();
		}
		return configurableSerializer;
	}

	public <T, E extends T> OptionsBuilder serializer(Class<T> clazz, TextSerializer<E> serializer) {
		getConfigurablSerializer().registerSerializer(clazz, serializer);
		return this;
	}

	public <T, E extends T> OptionsBuilder defaultSerializer(TextSerializer<?> serializer) {
		getConfigurablSerializer().registerDefaultSerializer(serializer);
		return this;
	}

}
