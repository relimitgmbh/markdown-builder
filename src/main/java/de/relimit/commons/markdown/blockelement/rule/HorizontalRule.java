package de.relimit.commons.markdown.blockelement.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.StringUtil;

public class HorizontalRule extends MarkdownElement implements BlockElement {

	/**
	 * An enum providing the most common (maybe all?)
	 * {@link HorizontalRuleCharacter}s for fencing a horizontal rule. The
	 * interface exists just in case someone needs to add additional options for
	 * specialized use cases.
	 */
	public static enum RuleCharacter implements HorizontalRuleCharacter {

		HYPHEN('-'),
		ASTERISK('*'),
		UNDERSCORE('_');

		private char character;

		private RuleCharacter(char character) {
			this.character = character;
		}

		@Override
		public char getCharacter() {
			return character;
		}

	}

	public static final int MINIMUM_LENGTH = 3;

	private int length;

	private HorizontalRuleCharacter characterSupplier = RuleCharacter.HYPHEN;

	public HorizontalRule() {
		this.length = MINIMUM_LENGTH;
	}

	public HorizontalRule(int length) {
		this.length = Math.max(MINIMUM_LENGTH, length);
	}

	public HorizontalRule(HorizontalRuleCharacter characterSupplier) {
		this(MINIMUM_LENGTH);
		this.characterSupplier = Objects.requireNonNull(characterSupplier);
	}

	public HorizontalRule(int length, HorizontalRuleCharacter characterSupplier) {
		this(length);
		this.characterSupplier = Objects.requireNonNull(characterSupplier);
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		lines.add(StringUtil.fill(length, characterSupplier.getCharacter()));
		return lines;
	}

}
