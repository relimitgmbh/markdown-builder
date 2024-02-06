package de.relimit.commons.markdown.blockelement.list;

import de.relimit.commons.markdown.blockelement.paragraph.Paragraph;
import de.relimit.commons.markdown.builder.MarkdownSerializableAppender;

public class TaskListBuilder<P>
		extends ListBuilder<P, TaskListBuilder<P>, TaskList, TaskListItemBuilder<TaskListBuilder<P>>, TaskListItem> {

	public TaskListBuilder(TaskList element, MarkdownSerializableAppender<P, TaskList> parentAppender) {
		super(element, parentAppender);
	}

	public TaskListBuilder() {
		super(new TaskList());
	}

	@Override
	TaskListItem createListItem(Paragraph title) {
		return new TaskListItem(title, false);
	}

	@Override
	TaskListItemBuilder<TaskListBuilder<P>> createListItemBuilder(TaskListItem listItem,
			MarkdownSerializableAppender<TaskListBuilder<P>, TaskListItem> appender) {
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
		return startItem(new Paragraph(stringifyable)).completed(completed).end();
	}

	@Override
	protected TaskListBuilder<P> getBuilder() {
		return this;
	}

}
