package de.relimit.commons.markdown.blockelement.list;

public class OrderedList extends AbstractList<OrderedListItem> {

	public OrderedList(int indentationLevel) {
		super(indentationLevel);
	}

	@Override
	protected OrderedListItem gateKeep(OrderedListItem element) {
		element.setOrdinal(elements.size());
		return element;
	}

}
