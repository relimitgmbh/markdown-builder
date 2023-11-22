package de.relimit.commons.markdown.util;

public class Arg {

	private Arg() {
		// utility class
	}

	public static <T> T notNull(T arg, String argName) {
		if (arg == null) {
			throw new IllegalArgumentException(argName + " cannot be null.");
		}
		return arg;
	}

	public static String notNullOrEmpty(String arg, String argName) {
		notNull(arg, argName);
		if (arg.isEmpty()) {
			throw new IllegalArgumentException(argName + " cannot be an empty String.");
		}
		return arg;
	}

	public static String notNullOrBlank(String arg, String argName) {
		notNull(arg, argName);
		if (arg.isBlank()) {
			throw new IllegalArgumentException(argName + " cannot be an empty String.");
		}
		return arg;
	}

}
