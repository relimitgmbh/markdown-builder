package de.relimit.commons.markdown.blockelement.admonition;

/**
 * As per documentation the type qualifier is customizable. This interface makes
 * it possible to add your own type. For convenience the predefined standard
 * types are available through {@link DefaultType}.
 * 
 * 
 * @see <a href=
 *      "https://squidfunk.github.io/mkdocs-material/reference/admonitions/#custom-admonitions">https://squidfunk.github.io/mkdocs-material/reference/admonitions/#custom-admonitions</a>
 */
public interface Type {

	/**
	 * @return The qualifier to be used for the admonition. Note that for
	 *         simplicity and speed the string returned is not checked and put
	 *         into the markdown as-is. Make sure it is valid.
	 */
	String getQualifier();

}
