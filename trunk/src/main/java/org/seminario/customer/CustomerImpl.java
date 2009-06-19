package org.seminario.customer;

import org.neo4j.api.core.Node;
import org.seminario.order.Order;
import org.seminario.order.OrderImpl;
import org.seminario.relationships.RelationshipTypes;

public class CustomerImpl implements Customer {

	private final Node underlyingNode;
	private static final String KEY_FIRST_NAME = "firstName";

	public CustomerImpl(Node underlyingNode) {
		this.underlyingNode = underlyingNode;
	}

	public void setFirstName(String firstName) {
		underlyingNode.setProperty(KEY_FIRST_NAME, firstName);
	}

	public String getFirstName() {
		return (String) underlyingNode.getProperty(KEY_FIRST_NAME);
	}

	public Node getUnderlyingNode() {
		return underlyingNode;
	}
	@Override
	public void addOrder(Order order) {
		Node orderNode = ((OrderImpl) order).getUnderlyingNode();
		getUnderlyingNode().createRelationshipTo(orderNode,
				RelationshipTypes.CUSTOMER_TO_ORDER);
	}
}
