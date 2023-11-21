package de.relimit.commons.markdown.blockelement.list;

public class TaskListItem extends ListItem {

	private boolean completed;

	public TaskListItem(int indentationLevel, boolean completed) {
		super(indentationLevel);
		this.completed = completed;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	protected String getListMarker() {
		return "- [" + (completed ? "x" : " ") + "]";
	}

}
