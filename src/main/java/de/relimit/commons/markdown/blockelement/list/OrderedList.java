package de.relimit.commons.markdown.blockelement.list;

public class OrderedList extends AbstractList<OrderedListItem> {

	public OrderedList(int indentationLevel) {
		super(indentationLevel);
	}

	@Override
	public void append(OrderedListItem element) {
		super.append(element);
		element.setOrdinal(elements.size());
	}

}
