package org.seminario.salesman;

import org.neo4j.api.core.Node;

public class SalesmanImpl implements Salesman {

	private final Node underlyingNode;
	private static final String KEY_FIRST_NAME = "firstName";

	public SalesmanImpl(Node underlyingNode) {
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
}
