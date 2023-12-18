package de.relimit.commons.markdown;

import java.util.List;

import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public interface LineBased extends MarkdownSerializable {

	List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException;

}
