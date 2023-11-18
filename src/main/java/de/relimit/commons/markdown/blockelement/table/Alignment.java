package de.relimit.commons.markdown.blockelement.table;

public enum Alignment {

	LEFT,
	CENTER,
	RIGHT,
	/* In practice this usually defaults to left alignment. */
	NEUTRAL;

	public static final IllegalArgumentException illegalAlignment(Alignment alignment) {
		return new IllegalArgumentException(
				"Unknown " + Alignment.class.getSimpleName() + ": " + alignment.name() + ".");
	}

}
