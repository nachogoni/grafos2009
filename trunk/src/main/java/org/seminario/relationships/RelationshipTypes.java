package org.seminario.relationships;

import org.neo4j.api.core.RelationshipType;

public enum RelationshipTypes implements RelationshipType {
	CUSTOMER_TO_ORDER, ORDERS, CUSTOMERS, ITEMS, ORDER_TO_ITEM, SALESMAN, ORDER_TO_SALESMAN;
}