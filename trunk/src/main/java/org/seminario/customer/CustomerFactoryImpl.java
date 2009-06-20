package org.seminario.customer;

import java.util.Iterator;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.neo4j.api.core.ReturnableEvaluator;
import org.neo4j.api.core.StopEvaluator;
import org.neo4j.api.core.Traverser;
import org.seminario.relationships.RelationshipTypes;

public class CustomerFactoryImpl implements CustomerFactory {

	private final NeoService neo;
	private final Node customerFactoryNode;
	private final String KEY_NAME = "name";
	
	public CustomerFactoryImpl(NeoService neo) {
		this.neo = neo;

		Relationship rel = neo.getReferenceNode().getSingleRelationship(
				RelationshipTypes.CUSTOMERS, Direction.OUTGOING);
		if (rel == null) {
			customerFactoryNode = neo.createNode();
			customerFactoryNode.setProperty(KEY_NAME, "Customers");
			neo.getReferenceNode().createRelationshipTo(customerFactoryNode,
					RelationshipTypes.CUSTOMERS);
		} else {
			customerFactoryNode = rel.getEndNode();
		}
	}

	@Override
	public Customer createCustomer() {
		Node node = neo.createNode();
		customerFactoryNode.createRelationshipTo(node,
				RelationshipTypes.CUSTOMERS);

		return new CustomerImpl(node);
	}

	@Override
	public Iterator<Customer> getAll() {
		return new Iterator<Customer>() {
			private final Iterator<Node> iterator = customerFactoryNode
					.traverse(Traverser.Order.BREADTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							RelationshipTypes.CUSTOMERS, Direction.OUTGOING)
					.iterator();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Customer next() {
				Node nextNode = iterator.next();
				return new CustomerImpl(nextNode);
			}

			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
}
