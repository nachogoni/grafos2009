package org.robertoCarlos;

import java.util.Iterator;
import java.util.Random;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.EmbeddedNeo;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.neo4j.api.core.RelationshipType;
import org.neo4j.api.core.ReturnableEvaluator;
import org.neo4j.api.core.StopEvaluator;
import org.neo4j.api.core.Transaction;
import org.neo4j.api.core.Traverser;
import org.neo4j.api.core.Traverser.Order;

public class RobertoCarlos {

	private String[] nombres;

	public static long start;
	public static long end;
	public static double promedio;
	public static long cant;

	public RobertoCarlos() {
		promedio = 0D;
		cant = 0;

		this.nombres = new String[] { "Diego", "Carlos", "Pipo", "Maxito",
				"Juani", "Pablo", "Martín" };

	}

	public enum Relaciones implements RelationshipType {
		AMIGOS
	}

	public String[] getNombres() {
		return this.nombres;
	}

	public static void main(String[] args) {
		// DB path
		NeoService neo = new EmbeddedNeo("var/robertoCarlos");
		RobertoCarlos rc = new RobertoCarlos();
		Transaction tx = null;
		Node robertoCarlos = null;

		Random r = new Random();

		start = System.currentTimeMillis();
		tx = neo.beginTx();
		try {
			// Create Roberto Carlos
			robertoCarlos = neo.createNode();
			robertoCarlos.setProperty("nombre", "Roberto Carlos");

			for (int i = 0; i < 10000; i++) {
				Node aNode = neo.createNode();

				int nombreIndex = r.nextInt(rc.getNombres().length);
				aNode.setProperty("nombre", rc.getNombres()[nombreIndex] + i);

				robertoCarlos.createRelationshipTo(aNode, Relaciones.AMIGOS);
			}
			tx.success();
		} finally {
			tx.finish();
		}

		end = System.currentTimeMillis();
		System.out.println("La db se creó en " + (end - start) + " ms.");

		start = System.currentTimeMillis();
		tx = neo.beginTx();
		try {
			Iterator<Relationship> iter = robertoCarlos.getRelationships(
					Relaciones.AMIGOS).iterator();

			while (iter.hasNext()) {
				iter.next();
				cant++;
			}
			tx.success();

		} finally {
			tx.finish();
		}

		System.out.println("Cantidad de amigos " + cant);
		end = System.currentTimeMillis();

		System.out.println("La consulta se hizo en " + (end - start) + " ms.");

		neo.shutdown();

	}
}
