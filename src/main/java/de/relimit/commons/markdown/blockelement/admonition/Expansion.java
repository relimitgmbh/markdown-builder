package de.relimit.commons.markdown.blockelement.admonition;

public enum Expansion {

	FIXED("!!!"),
	EXPANDABLE("???"),
	EXPANDED("???+");

	private String marker;

	private Expansion(String marker) {
		this.marker = marker;
	}

	public String getMarker() {
		return marker;
	}

}
