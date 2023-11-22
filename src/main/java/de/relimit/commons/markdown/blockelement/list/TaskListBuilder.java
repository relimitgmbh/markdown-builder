package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;

public class TaskListBuilder<P>
		extends ListBuilder<TaskListBuilder<P>, P, TaskList, TaskListItemBuilder<TaskListBuilder<P>>, TaskListItem> {

	public TaskListBuilder(TaskList element, MarkdownElementAppender<P, TaskList> parentAppender) {
		super(element, parentAppender);
	}

	public TaskListBuilder(TaskList element) {
		super(element);
	}

	@Override
	TaskListItem createListItem(int indentationLevel) {
		return new TaskListItem(indentationLevel, false);
	}

	@Override
	TaskListItemBuilder<TaskListBuilder<P>> createListItemBuilder(TaskListItem listItem,
			MarkdownElementAppender<TaskListBuilder<P>, TaskListItem> appender) {
		return new TaskListItemBuilder<>(listItem, appender);
	}

	/**
	 * Convenience method to quickly create a task item from a text.
	 * 
	 * @param stringifyable
	 * @param completed
	 * @return
	 */
	public TaskListBuilder<P> item(Object stringifyable, boolean completed) {
		return startItem().paragraph(stringifyable).completed(completed).end();
	}

	@Override
	protected TaskListBuilder<P> getBuilder() {
		return this;
	}

}
