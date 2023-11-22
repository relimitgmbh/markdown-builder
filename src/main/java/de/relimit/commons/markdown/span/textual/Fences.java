package de.relimit.commons.markdown.span.textual;

public class Fences {

	private String leading;

	private String trailing;

	private Fences(String leading, String trailing) {
		this.leading = leading;
		this.trailing = trailing;
	}

	public String getLeading() {
		return leading;
	}

	public String getTrailing() {
		return trailing;
	}

	public String fence(String string) {
		/* Inelegant for speed. */
		if (leading != null && trailing != null) {
			return leading + string + trailing;
		}
		if (leading != null) {
			return leading + string;
		} else {
			return string + trailing;
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

}
