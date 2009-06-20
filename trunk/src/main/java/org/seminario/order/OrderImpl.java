package org.seminario.order;

import java.util.Collection;
import java.util.LinkedList;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.ReturnableEvaluator;
import org.neo4j.api.core.StopEvaluator;
import org.neo4j.api.core.Traverser;
import org.seminario.customer.Customer;
import org.seminario.customer.CustomerImpl;
import org.seminario.item.Item;
import org.seminario.item.ItemImpl;
import org.seminario.relationships.RelationshipTypes;
import org.seminario.salesman.Salesman;
import org.seminario.salesman.SalesmanImpl;

public class OrderImpl implements Order {

	private final Node underlyingNode;
	private static final String ORDER_NUMBER = "orderNumber";
	private static final String ICON = "order";

	public OrderImpl(Node underlyingNode) {
		this.underlyingNode = underlyingNode;
		this.underlyingNode.setProperty(ICON, ICON);
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

	@Override
	public void addItem(Item item) {
		Node itemNode = ((ItemImpl) item).getUnderlyingNode();
		getUnderlyingNode().createRelationshipTo(itemNode,
				RelationshipTypes.ORDER_TO_ITEM);

	}

	@Override
	public Collection<Item> getItems() {
		Collection<Node> itemNodes = getItemNodes();
		return wrapCollection(itemNodes);
	}

	private Collection<Node> getItemNodes() {
		Traverser traverse = underlyingNode.traverse(
				Traverser.Order.BREADTH_FIRST, StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				RelationshipTypes.ORDER_TO_ITEM, Direction.OUTGOING);

		return traverse.getAllNodes();
	}

	private Collection<Item> wrapCollection(Collection<Node> itemNodes) {
		Collection<Item> itemCollection = new LinkedList<Item>();

		for (Node node : itemNodes){
			itemCollection.add( new ItemImpl(node));
		}

		return itemCollection;
	}

	@Override
	public Salesman getSalesman() {
		Node salesmanNode = underlyingNode.getSingleRelationship(
				RelationshipTypes.ORDER_TO_SALESMAN, Direction.OUTGOING)
				.getEndNode();

		return new SalesmanImpl(salesmanNode);
	}

	@Override
	public void setSalesman(Salesman salesman) {
		Node salesmanNode = ((SalesmanImpl) salesman).getUnderlyingNode();
		getUnderlyingNode().createRelationshipTo(salesmanNode,
				RelationshipTypes.ORDER_TO_SALESMAN);

	}

}
