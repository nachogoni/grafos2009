package org.seminario.item;

import org.neo4j.api.core.Node;

public class ItemImpl implements Item {

	private final Node underlyingNode;
	private static final String PRICE = "price";
	private static final String NAME = "name";

	public ItemImpl(Node underlyingNode) {
		this.underlyingNode = underlyingNode;
	}

	public Node getUnderlyingNode() {
		return underlyingNode;
	}

	@Override
	public Long getPrice() {
		return (Long) underlyingNode.getProperty(PRICE);
	}

	@Override
	public void setPrice(Long price) {
		underlyingNode.setProperty(PRICE, price);
	}

	@Override
	public String getName() {
		return (String) underlyingNode.getProperty(NAME);
	}

	@Override
	public void setName(String name) {
		underlyingNode.setProperty(NAME, name);
	}
}
