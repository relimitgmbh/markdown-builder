package de.relimit.commons.markdown.blockelement.admonition;

import java.util.Optional;

public enum Display {

	BLOCK(null),
	INLINE_LEFT("inline"),
	INLINE_RIGHT("inline end");

	private String directive;

	private Display(String directive) {
		this.directive = directive;
	}

	public Optional<String> getDirective() {
		return Optional.ofNullable(directive);
	}

}
