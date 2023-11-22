package de.relimit.commons.markdown.span.textual;

import java.util.Optional;

public class Fences {

	private static final Fences NONE = new Fences(null, null);

	private String leading;

	private String trailing;

	private Fences(String leading, String trailing) {
		this.leading = leading;
		this.trailing = trailing;
	}

	public Optional<String> getLeading() {
		return Optional.ofNullable(leading);
	}

	public Optional<String> getTrailing() {
		return Optional.ofNullable(trailing);
	}

	public String fence(String string) {
		final int b = (leading != null ? 2 : 0) + (trailing != null ? 1 : 0);
		switch (b) {
		case 0:
			return string;
		case 1:
			return string + trailing;
		case 2:
			return leading + string;
		case 3:
			return leading + string + trailing;
		default:
			throw new AssertionError(b + " should not be found at this point.");
		}
	}

	public static final Fences of(String leftFence, String rightFence) {
		return new Fences(leftFence, rightFence);
	}

	public static final Fences ofLeft(String leftFence) {
		return new Fences(leftFence, null);
	}

	public static final Fences ofRight(String rightFence) {
		return new Fences(null, rightFence);
	}

	public static final Fences none() {
		return NONE;
	}

}
