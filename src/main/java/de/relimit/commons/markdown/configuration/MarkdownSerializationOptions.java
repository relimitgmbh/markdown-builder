package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.Fences;
import de.relimit.commons.markdown.MarkdownSerializable;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRule;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRuleCharacter;
import de.relimit.commons.markdown.blockelement.table.Alignment;
import de.relimit.commons.markdown.blockelement.table.Table;
import de.relimit.commons.markdown.blockelement.table.TableCell;
import de.relimit.commons.markdown.converter.Escaper;
import de.relimit.commons.markdown.converter.Stringifier;
import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.Textual;
import de.relimit.commons.markdown.util.Args;

public class MarkdownSerializationOptions {

	public static final MarkdownSerializationOptions DEFAULT_OPTIONS = new MarkdownSerializationOptions();

	private Stringifier<Object> serializer;

	private Escaper escaper;

	private String lineSeparator;

	private String tableCellLineSeparator;

	private CodeBlockLanguage defaultCodeBlockLangauge;

	private Fences codeFences;

	private HorizontalRuleCharacter defaultHorizontalRuleCharacter;

	private Alignment defaultTableCellAlignment;

	public MarkdownSerializationOptions() {
		this.serializer = Stringifier.DEFAULT_SERIALIZER;
		this.escaper = Escaper.ESCAPE_MARKDOWN;
		this.lineSeparator = System.lineSeparator();
		this.tableCellLineSeparator = TableCell.DEFAULT_TABLE_CELL_LINE_SEPARATOR;
		this.defaultCodeBlockLangauge = CodeBlock.DEFAULT_LANGUAGE;
		this.codeFences = Code.DEFAULT_FENCES;
		this.defaultHorizontalRuleCharacter = HorizontalRule.DEFAULT_CHARACTER;
		this.defaultTableCellAlignment = Table.DEFAULT_ALIGNMENT;
	}

	/**
	 * Copy constructor for shallow copy. The serializer in particular is not
	 * cloned. So be careful augmenting the serializer of the copy on the fly.
	 * It will change the original serializer.
	 * 
	 * @param template
	 */
	public MarkdownSerializationOptions(MarkdownSerializationOptions template) {
		setSerializer(template.getSerializer());
		setEscaper(template.getEscaper());
		setLineSeparator(template.getLineSeparator());
		setTableCellLineSeparator(template.getTableCellLineSeparator());
		setDefaultCodeBlockLangauge(template.getDefaultCodeBlockLangauge());
		setCodeFences(template.getCodeFences());
		setDefaultHorizontalRuleCharacter(template.getDefaultHorizontalRuleCharacter());
		setDefaultTableCellAlignment(template.getDefaultTableCellAlignment());
	}

	public MarkdownSerializationOptions copy() {
		return new MarkdownSerializationOptions(this);
	}

	public Stringifier<Object> getSerializer() {
		return serializer;
	}

	public void setSerializer(Stringifier<Object> plainTextSerializer) {
		this.serializer = Args.notNull(plainTextSerializer, "plainTextSerializer");
	}

	public Escaper getEscaper() {
		return escaper;
	}

	public void setEscaper(Escaper escaper) {
		this.escaper = Args.notNull(escaper, "escaper");
	}

	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = Args.notNull(lineSeparator, "lineSeparator");
	}

	public String getTableCellLineSeparator() {
		return tableCellLineSeparator;
	}

	public void setTableCellLineSeparator(String tableCellLineSeparator) {
		this.tableCellLineSeparator = Args.notNull(tableCellLineSeparator, "tableCellLineSeparator");
	}

	public CodeBlockLanguage getDefaultCodeBlockLangauge() {
		return defaultCodeBlockLangauge;
	}

	public void setDefaultCodeBlockLangauge(CodeBlockLanguage defaultCodeBlockLangauge) {
		this.defaultCodeBlockLangauge = Args.notNull(defaultCodeBlockLangauge, "defaultCodeBlockLangauge");
	}

	public Fences getCodeFences() {
		return codeFences;
	}

	public void setCodeFences(Fences codeFences) {
		this.codeFences = Args.notNull(codeFences, "codeFences");
	}

	public HorizontalRuleCharacter getDefaultHorizontalRuleCharacter() {
		return defaultHorizontalRuleCharacter;
	}

	public void setDefaultHorizontalRuleCharacter(HorizontalRuleCharacter defaultHorizontalRuleCharacter) {
		this.defaultHorizontalRuleCharacter = Args.notNull(defaultHorizontalRuleCharacter,
				"defaultHorizontalRuleCharacter");
	}

	public Alignment getDefaultTableCellAlignment() {
		return defaultTableCellAlignment;
	}

	public void setDefaultTableCellAlignment(Alignment defaultTableCellAlignment) {
		this.defaultTableCellAlignment = Args.notNull(defaultTableCellAlignment, "defaultTableCellAlignment");
	}

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
	public String stringify(Textual element) throws MarkdownSerializationException {
		final Object stringifyable = element.getStringifyable();
		String stringified = getSerializer().stringify(element, stringifyable);
		stringified = getEscaper().escape(element, stringifyable, stringified);
		return stringified;
	}

}
