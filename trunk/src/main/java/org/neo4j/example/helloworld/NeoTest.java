package org.neo4j.example.helloworld;

import java.io.Serializable;

import org.neo4j.api.core.*;
import org.neo4j.api.core.Traverser.Order;

/**
 * Example class that constructs a simple node space with message attributes and then prints them.
 */
public class NeoTest implements Serializable{

	private static final long serialVersionUID = 1L;

	public enum RelTypes implements RelationshipType {
        KNOWS
    }

    public static void main(String[] args) {
        NeoService neo = new EmbeddedNeo("var/base");


        Transaction tx = neo.beginTx();
        try {
        	// Create Thomas 'Neo' Anderson
        	Node mrAnderson = neo.createNode();
        	mrAnderson.setProperty( "name", "Thomas Anderson" );
        	mrAnderson.setProperty( "age", 29 );

        	// Create Morpheus
        	Node morpheus = neo.createNode();
        	morpheus.setProperty( "name", "Morpheus" );
        	morpheus.setProperty( "rank", "Captain" );
        	morpheus.setProperty( "occupation", "Total bad ass" );

        	// Create Trinity
        	Node trinity = neo.createNode();
        	trinity.setProperty( "name", "Trinity" );

        	// Create Oracle
        	Node oracle = neo.createNode();
        	oracle.setProperty( "name", "Oracle" );
        	oracle.setProperty( "occupation", "wizard?" );

        	//Create Cypher
            Node cypher = neo.createNode();
            cypher.setProperty( "name", "Cypher" );
            cypher.setProperty( "last name", "Reagan" );
            
        	//Create Smith
            Node smith = neo.createNode();
            smith.setProperty( "name", "Agent Smith" );
            smith.setProperty( "version", "1.0b" );
            smith.setProperty( "language", "C++" );

            //Relations
        	mrAnderson.createRelationshipTo(morpheus, RelTypes.KNOWS);
        	mrAnderson.createRelationshipTo(trinity, RelTypes.KNOWS);
        	morpheus.createRelationshipTo(trinity, RelTypes.KNOWS);
        	morpheus.createRelationshipTo( cypher, RelTypes.KNOWS );
        	cypher.createRelationshipTo( smith, RelTypes.KNOWS );

    		printFriends(mrAnderson);
        }
        finally {
            tx.finish();
            neo.shutdown();
        }
    }

    private static void printFriends( Node person )
    {
        System.out.println( person.getProperty( "name" ) + "'s friends:" );
        Traverser traverser = person.traverse( Order.BREADTH_FIRST,
            StopEvaluator.END_OF_GRAPH,
            ReturnableEvaluator.ALL_BUT_START_NODE, RelTypes.KNOWS,
            Direction.OUTGOING );
        for ( Node friend : traverser )
        {
            TraversalPosition position = traverser.currentPosition();
            System.out.println( "At depth " + position.depth() + " => "
                + friend.getProperty( "name" ) );
        }
    }
}
