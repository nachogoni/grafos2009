package org.seminario.order;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.Node;
import org.seminario.customer.Customer;
import org.seminario.customer.CustomerImpl;
import org.seminario.relationships.RelationshipTypes;

public class OrderImpl implements Order {

	private final Node underlyingNode;
	private static final String ORDER_NUMBER = "orderNumber";

	public OrderImpl(Node underlyingNode) {
		this.underlyingNode = underlyingNode;
	}

	@Override
	public Customer getCustomer() {
		Node customerNode = underlyingNode.getSingleRelationship(
				RelationshipTypes.CUSTOMER_TO_ORDER, Direction.INCOMING)
				.getStartNode();

		return new CustomerImpl(customerNode);
	}

	@Override
	public long getOrderNumber() {
		return (Long) underlyingNode.getProperty(ORDER_NUMBER);
	}

	@Override
	public void setOrderNumber(long orderNumber) {
		underlyingNode.setProperty(ORDER_NUMBER, orderNumber);
	}
	
	public Node getUnderlyingNode() {
		return underlyingNode;
	}
}
