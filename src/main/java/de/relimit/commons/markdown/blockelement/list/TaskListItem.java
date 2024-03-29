package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.configuration.MarkdownSerializationOptions;

public class TaskListItem extends ListItem {

	private boolean completed;

	public TaskListItem(Paragraph title, boolean completed) {
		super(title);
		this.completed = completed;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	protected String getMarker(MarkdownSerializationOptions options) {
		return "- [" + (completed ? "x" : " ") + "]";
	}

}
