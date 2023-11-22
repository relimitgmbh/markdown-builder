package de.relimit.commons.markdown.util;

import java.util.Arrays;
import java.util.List;

import de.relimit.commons.markdown.blockelement.table.Alignment;

public class Strings {

	private Strings() {
		// utility class
	}

	public static String fillUpAligned(String value, String fill, int length, Alignment alignment) {
		switch (alignment) {
		case RIGHT:
			return fillUpRightAligned(value, fill, length);
		case CENTER:
			return fillUpCenterAligned(value, fill, length);
		case LEFT:
		case NEUTRAL:
			return fillUpLeftAligned(value, fill, length);
		default:
			throw Alignment.illegalAlignment(alignment);
		}
	}

	public static String fillUpLeftAligned(String value, String fill, int length) {
		if (value.length() >= length) {
			return value;
		}
		final StringBuilder valueBuilder = new StringBuilder(length);
		valueBuilder.append(value);
		while (valueBuilder.length() < length) {
			valueBuilder.append(fill);
		}
		return valueBuilder.toString();
	}

	public static String fillUpRightAligned(String value, String fill, int length) {
		final int valueLength = value.length();
		if (valueLength >= length) {
			return value;
		}
		final StringBuilder valueBuilder = new StringBuilder(length);
		final int fillLength = length - valueLength;
		while (valueBuilder.length() < fillLength) {
			valueBuilder.append(fill);
		}
		valueBuilder.append(value);
		return valueBuilder.toString();
	}

	public static String fillUpCenterAligned(String value, String fill, int length) {
		final int valueLength = value.length();
		if (valueLength >= length) {
			return value;
		}
		final int fillLeftCount = (length - valueLength) / 2 / fill.length();
		final StringBuilder valueBuilder = new StringBuilder(length);
		for (int i = 0; i < fillLeftCount; i++) {
			valueBuilder.append(fill);
		}
		valueBuilder.append(value);
		while (valueBuilder.length() < length) {
			valueBuilder.append(fill);
		}
		return valueBuilder.toString();
	}

	/**
	 * Handles UNIX and Windows
	 * 
	 * @param string
	 * @return
	 */
	public static List<String> splitLines(String string) {
		/*
		 * Since Java 8 "\R" matches line breaks including unicode characters.
		 * The -1 disables the limit which leads to trailing and leading line
		 * breaks to be recognized as separate lines.
		 */
		return Arrays.asList(string.split("\\R", -1));
	}

	public static String fill(int length, char filler) {
		return fill(length, String.valueOf(filler));
	}

	public static String fill(int length, String filler) {
		if (length > 0) {
			final StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < length; i++) {
				buffer.append(filler);
			}
			return buffer.toString();
		} else {
			return "";
		}
	}

	public static String spaces(int length) {
		return fill(length, " ");
	}

}