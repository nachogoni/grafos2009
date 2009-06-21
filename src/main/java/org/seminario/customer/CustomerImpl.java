package org.seminario.customer;

import java.util.Iterator;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.ReturnableEvaluator;
import org.neo4j.api.core.StopEvaluator;
import org.neo4j.api.core.Traverser;
import org.seminario.order.Order;
import org.seminario.order.OrderImpl;
import org.seminario.relationships.RelationshipTypes;

public class CustomerImpl implements Customer {

	private final Node underlyingNode;
	private static final String KEY_FIRST_NAME = "firstName";

	public CustomerImpl(Node underlyingNode) {
		this.underlyingNode = underlyingNode;
	}

	@Override
	public void setFirstName(String firstName) {
		underlyingNode.setProperty(KEY_FIRST_NAME, firstName);
	}

	@Override
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

	@Override
	public Iterator<Order> getAllOrders() {
		return new Iterator<Order>() {
			private final Iterator<Node> iterator = getUnderlyingNode()
					.traverse(Traverser.Order.BREADTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							RelationshipTypes.CUSTOMER_TO_ORDER,
							Direction.OUTGOING).iterator();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Order next() {
				Node nextNode = iterator.next();
				return new OrderImpl(nextNode);
			}

			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
}
