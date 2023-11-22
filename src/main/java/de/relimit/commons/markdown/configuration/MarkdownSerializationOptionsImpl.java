package de.relimit.commons.markdown.configuration;

import de.relimit.commons.markdown.blockelement.codeblock.CodeBlock;
import de.relimit.commons.markdown.blockelement.codeblock.CodeBlockLanguage;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRule;
import de.relimit.commons.markdown.blockelement.rule.HorizontalRuleCharacter;
import de.relimit.commons.markdown.blockelement.table.TableCell;
import de.relimit.commons.markdown.span.textual.Code;
import de.relimit.commons.markdown.span.textual.Fences;
import de.relimit.commons.markdown.util.Args;

public class MarkdownSerializationOptionsImpl implements MarkdownSerializationOptions {

	public static final MarkdownSerializationOptions DEFAULT_OPTIONS = new MarkdownSerializationOptionsImpl();

	private TextSerializer<Object> serializer;

	private TextEscaper escaper;

	private String lineSeparator;

	private String tableCellLineSeparator;

	private CodeBlockLanguage defaultCodeBlockLangauge;

	private Fences codeFences;

	private HorizontalRuleCharacter defaultHorizontalRuleCharacter;

	public MarkdownSerializationOptionsImpl() {
		this.serializer = TextSerializer.DEFAULT_SERIALIZER;
		this.escaper = DefaultEscaper.DEFAULT_ESCAPER;
		this.lineSeparator = System.lineSeparator();
		this.tableCellLineSeparator = TableCell.DEFAULT_TABLE_CELL_LINE_SEPARATOR;
		this.defaultCodeBlockLangauge = CodeBlock.DEFAULT_LANGUAGE;
		this.codeFences = Code.DEFAULT_FENCES;
		this.defaultHorizontalRuleCharacter = HorizontalRule.DEFAULT_CHARACTER;
	}

	/**
	 * Copy constructor for shallow copy. The serializer in particular is not
	 * cloned. So be careful augmenting the serializer of the copy on the fly.
	 * It will change the original serializer.
	 * 
	 * @param template
	 */
	public MarkdownSerializationOptionsImpl(MarkdownSerializationOptions template) {
		setSerializer(template.getSerializer());
		setEscaper(template.getEscaper());
		setLineSeparator(template.getLineSeparator());
		setTableCellLineSeparator(template.getTableCellLineSeparator());
		setDefaultCodeBlockLangauge(template.getDefaultCodeBlockLangauge());
		setCodeFences(template.getCodeFences());
		setDefaultHorizontalRuleCharacter(template.getDefaultHorizontalRuleCharacter());
	}

	public MarkdownSerializationOptionsImpl copy() {
		return new MarkdownSerializationOptionsImpl(this);
	}

	@Override
	public TextSerializer<Object> getSerializer() {
		return serializer;
	}

	public void setSerializer(TextSerializer<Object> plainTextSerializer) {
		this.serializer = Args.notNull(plainTextSerializer, "plainTextSerializer");
	}

	@Override
	public TextEscaper getEscaper() {
		return escaper;
	}

	public void setEscaper(TextEscaper escaper) {
		this.escaper = Args.notNull(escaper, "escaper");
	}

	@Override
	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = Args.notNull(lineSeparator, "lineSeparator");
	}

	@Override
	public String getTableCellLineSeparator() {
		return tableCellLineSeparator;
	}

	public void setTableCellLineSeparator(String tableCellLineSeparator) {
		this.tableCellLineSeparator = Args.notNull(tableCellLineSeparator, "tableCellLineSeparator");
	}

	@Override
	public CodeBlockLanguage getDefaultCodeBlockLangauge() {
		return defaultCodeBlockLangauge;
	}

	public void setDefaultCodeBlockLangauge(CodeBlockLanguage defaultCodeBlockLangauge) {
		this.defaultCodeBlockLangauge = Args.notNull(defaultCodeBlockLangauge, "defaultCodeBlockLangauge");
	}

	@Override
	public Fences getCodeFences() {
		return codeFences;
	}

	public void setCodeFences(Fences codeFences) {
		this.codeFences = Args.notNull(codeFences, "codeFences");
	}

	@Override
	public HorizontalRuleCharacter getDefaultHorizontalRuleCharacter() {
		return defaultHorizontalRuleCharacter;
	}

	public void setDefaultHorizontalRuleCharacter(HorizontalRuleCharacter defaultHorizontalRuleCharacter) {
		this.defaultHorizontalRuleCharacter = defaultHorizontalRuleCharacter;
	}

}
