package de.relimit.commons.markdown.blockelement.list;

public class OrderedList extends AbstractList<OrderedListItem> {

	@Override
	protected OrderedListItem gateKeep(OrderedListItem element) {
		element.setOrdinal(elements.size());
		return element;
	}

}
