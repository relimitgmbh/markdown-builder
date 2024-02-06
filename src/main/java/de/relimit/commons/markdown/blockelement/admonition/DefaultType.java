package de.relimit.commons.markdown.blockelement.admonition;

public enum DefaultType implements Type {

	NOTE,
	ABSTRACT,
	INFO,
	TIP,
	SUCCESS,
	QUESTION,
	WARNING,
	FAILURE,
	DANGER,
	BUG,
	EXAMPLE,
	QUOTE;

	@Override
	public String getQualifier() {
		return name().toLowerCase();
	}

}
