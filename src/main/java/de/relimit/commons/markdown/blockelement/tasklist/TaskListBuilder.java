package de.relimit.commons.markdown.blockelement.tasklist;

import de.relimit.commons.markdown.builder.MarkdownElementAppender;
import de.relimit.commons.markdown.builder.NodeBuilder;
import de.relimit.commons.markdown.span.SpanElementNodeBuilder;

public class TaskListBuilder<P> extends NodeBuilder<TaskListBuilder<P>, P, TaskList, TaskListElement> {

	public TaskListBuilder(TaskList element, MarkdownElementAppender<P, TaskList> parent) {
		super(element, parent);
	}

	public TaskListBuilder(TaskList element) {
		super(element);
	}

	@Override
	protected TaskListBuilder<P> getBuilder() {
		return this;
	}

	public TaskListBuilder<P> task(boolean completed, Object stringyfiable) {
		return startTask(completed).plainText(stringyfiable).end();
	}

	public SpanElementNodeBuilder<TaskListBuilder<P>, TaskListElement> startTask(boolean completed) {
		return new SpanElementNodeBuilder<>(new TaskListElement(completed), this::append);
	}

}
