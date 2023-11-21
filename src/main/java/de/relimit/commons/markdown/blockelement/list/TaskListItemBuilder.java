package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;

public class TaskListItemBuilder<P> extends ListItemBuilder<TaskListItemBuilder<P>, P, TaskListItem> {

	public TaskListItemBuilder(TaskListItem listItem, MarkdownElementAppender<P, TaskListItem> parentAppender) {
		super(listItem, parentAppender);
	}

	public TaskListItemBuilder(TaskListItem listItem) {
		super(listItem);
	}

	public TaskListItemBuilder<P> incomplete() {
		return completed(false);
	}

	public TaskListItemBuilder<P> complete() {
		return completed(true);
	}

	public TaskListItemBuilder<P> completed(boolean completed) {
		getElement().setCompleted(completed);
		return this;
	}

	@Override
	protected TaskListItemBuilder<P> getBuilder() {
		return this;
	}

}
