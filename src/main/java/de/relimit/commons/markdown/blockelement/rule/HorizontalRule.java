package de.relimit.commons.markdown.blockelement.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.relimit.commons.markdown.MarkdownElement;
import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;
import de.relimit.commons.markdown.util.Args;
import de.relimit.commons.markdown.util.Strings;

public class HorizontalRule extends MarkdownElement implements BlockElement {

	public static final HorizontalRuleCharacter DEFAULT_CHARACTER = RuleCharacter.HYPHEN;

	/**
	 * An enum providing the most common (maybe all?)
	 * {@link HorizontalRuleCharacter}s for fencing a horizontal rule. The
	 * interface exists just in case someone needs to add additional options for
	 * specialized use cases.
	 */
	public enum RuleCharacter implements HorizontalRuleCharacter {

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

	private HorizontalRuleCharacter characterSupplier;

	public HorizontalRule() {
		this(MINIMUM_LENGTH);
	}

	public HorizontalRule(HorizontalRuleCharacter characterSupplier) {
		this(MINIMUM_LENGTH);
		/*
		 * While it can be null it does not make sense to use this constructor
		 * with null as an argument
		 */
		this.characterSupplier = Args.notNull(characterSupplier, "characterSupplier");
	}

	public HorizontalRule(int length) {
		this.length = Math.max(MINIMUM_LENGTH, length);
	}

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		lines.add(Strings.fill(length, Optional.ofNullable(characterSupplier)
				.orElse(options.getDefaultHorizontalRuleCharacter()).getCharacter()));
		return lines;
	}

}
