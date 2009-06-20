package org.seminario.salesman;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.seminario.relationships.RelationshipTypes;

public class SalesmanFactoryImpl implements SalesmanFactory {

	private final NeoService neo;
	private final Node salesmanFactoryNode;
	private final String KEY_NAME = "name";

	public SalesmanFactoryImpl(NeoService neo) {
		this.neo = neo;

		Relationship rel = neo.getReferenceNode().getSingleRelationship(
				RelationshipTypes.SALESMAN, Direction.OUTGOING);
		if (rel == null) {
			salesmanFactoryNode = neo.createNode();
			salesmanFactoryNode.setProperty(KEY_NAME, "Salesmans");
			neo.getReferenceNode().createRelationshipTo(salesmanFactoryNode,
					RelationshipTypes.SALESMAN);
		} else {
			salesmanFactoryNode = rel.getEndNode();
		}
	}

	@Override
	public Salesman createSalesman() {
		Node node = neo.createNode();
		salesmanFactoryNode.createRelationshipTo(node,
				RelationshipTypes.SALESMAN);

		return new SalesmanImpl(node);
	}
}
