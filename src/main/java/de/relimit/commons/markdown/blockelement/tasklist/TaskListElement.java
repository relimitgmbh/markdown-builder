package de.relimit.commons.markdown.blockelement.tasklist;

import java.util.List;
import java.util.Optional;

import de.relimit.commons.markdown.span.SpanElementNode;

public class TaskListElement extends SpanElementNode {

	private boolean completed;

	public TaskListElement(boolean completed) {
		this.completed = completed;
	}

	@Override
	protected Optional<String> getPredecessor(List<String> lines) {
		return Optional.of("- [" + (completed ? "x" : " ") + "] ");
	}

	@Override
	protected Optional<String> getSuccessor(List<String> lines) {
		return Optional.empty();
	}

}
