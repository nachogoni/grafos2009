package org.seminario.order;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.seminario.relationships.RelationshipTypes;

public class OrderFactoryImpl implements OrderFactory {

	private final NeoService neo;
	private final Node orderFactoryNode;

	public OrderFactoryImpl(NeoService neo) {

		this.neo = neo;

		Relationship rel = neo.getReferenceNode().getSingleRelationship(
				RelationshipTypes.ORDERS, Direction.OUTGOING);

		if (rel == null) {
			orderFactoryNode = neo.createNode();
			neo.getReferenceNode().createRelationshipTo(orderFactoryNode,
					RelationshipTypes.ORDERS);
		} else {
			orderFactoryNode = rel.getEndNode();
		}
	}

	@Override
	public Order createOrder() {
		Node node = neo.createNode();
		orderFactoryNode.createRelationshipTo(node, RelationshipTypes.ORDERS);

		return new OrderImpl(node);
	}

}
