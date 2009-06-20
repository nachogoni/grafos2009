package org.seminario.item;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.seminario.relationships.RelationshipTypes;

public class ItemFactoryImpl implements ItemFactory {

	private final NeoService neo;
	private final Node itemFactoryNode;
	private final String KEY_NAME = "name";

	public ItemFactoryImpl(NeoService neo) {
		this.neo = neo;

		Relationship rel = neo.getReferenceNode().getSingleRelationship(
				RelationshipTypes.ITEMS, Direction.OUTGOING);
		
		if (rel == null) {
			itemFactoryNode = neo.createNode();
			itemFactoryNode.setProperty(KEY_NAME, "Items");
			neo.getReferenceNode().createRelationshipTo(itemFactoryNode,
					RelationshipTypes.ITEMS);
		} else {
			itemFactoryNode = rel.getEndNode();
		}
	}

	@Override
	public Item createItem() {
		Node node = neo.createNode();
		itemFactoryNode.createRelationshipTo(node,
				RelationshipTypes.ITEMS);

		return new ItemImpl(node);
	}
}
