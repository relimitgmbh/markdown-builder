package de.relimit.commons.markdown.blockelement.tasklist;

import java.util.ArrayList;
import java.util.List;

import de.relimit.commons.markdown.MarkdownSerializationException;
import de.relimit.commons.markdown.Node;
import de.relimit.commons.markdown.blockelement.BlockElement;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class TaskList extends Node<TaskListElement> implements BlockElement {

	@Override
	public List<String> serializeLines(MarkdownSerializationOptions options) throws MarkdownSerializationException {
		final List<String> lines = new ArrayList<>();
		for (final TaskListElement tle : elements) {
			lines.add(tle.getSerialized(options));
		}
		return lines;
	}

}
