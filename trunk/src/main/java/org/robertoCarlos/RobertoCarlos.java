package org.robertoCarlos;

import java.util.Iterator;
import java.util.Random;

import org.neo4j.api.core.Direction;
import org.neo4j.api.core.EmbeddedNeo;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.RelationshipType;
import org.neo4j.api.core.ReturnableEvaluator;
import org.neo4j.api.core.StopEvaluator;
import org.neo4j.api.core.Transaction;
import org.neo4j.api.core.Traverser;
import org.neo4j.api.core.Traverser.Order;

public class RobertoCarlos {

	private String[] lugares;
	private String[] nombres;

	public static long start;
	public static long end;
	public static double promedio;
	public static long cant;

	public RobertoCarlos() {
		promedio = 0D;
		cant = 0;

		this.lugares = new String[] { "Argentina", "Brasil", "España",
				"México", "Noruega", "Venezuela", "Chile", "USA", "Chile",
				"Bolivia", "Francia" };

		this.nombres = new String[] { "Diego", "Carlos", "Pipo", "Maxito",
				"Juani", "Pablo", "Martín" };

	}

	public enum Relaciones implements RelationshipType {
		AMIGOS
	}

	public String[] getLugares() {
		return this.lugares;
	}

	public String[] getNombres() {
		return this.nombres;
	}

	public static void main(String[] args) {
		// DB path
		NeoService neo = new EmbeddedNeo("var/robertoCarlos");
		RobertoCarlos rc = new RobertoCarlos();

		Random r = new Random();

		Transaction tx = neo.beginTx();
		try {
			start = System.currentTimeMillis();
			// Create Roberto Carlos
			Node robertoCarlos = neo.createNode();
			robertoCarlos.setProperty("nombre", "Roberto Carlos");

			for (int i = 0; i < 10000; i++) {
				Node aNode = neo.createNode();

				int nombreIndex = r.nextInt(rc.getNombres().length);
				aNode.setProperty("nombre", rc.getNombres()[nombreIndex] + i);

				int lugaresIndex = r.nextInt(rc.getLugares().length);
				aNode.setProperty("lugar", rc.getLugares()[lugaresIndex] + i);

				robertoCarlos.createRelationshipTo(aNode, Relaciones.AMIGOS);
			}
			end = System.currentTimeMillis();
			System.out.println("La db se creó en " + (end - start) + " ms.");

			start = System.currentTimeMillis();
//			imprimeAmigos(robertoCarlos);
			Iterator iter = robertoCarlos.getRelationships(Relaciones.AMIGOS).iterator();
			while (iter.hasNext()) {
				iter.next();
				cant++;
			}
			System.out.println("Cantidad de amigos " + cant);
			end = System.currentTimeMillis();
			
			System.out.println("La consulta se hizo en " + (end - start)
					+ " ms.");
			System.out.println("How much time is spent per node in the "
					+ "traversal: " + (promedio / cant) + " ms.");
		} finally {
			tx.finish();
			neo.shutdown();
		}
	}

	private static void imprimeAmigos(Node person) {
		long start = 0;
		long end = 0;
		start = System.currentTimeMillis();
		System.out.println("Amigos de: " + person.getProperty("nombre"));

		Traverser traverser = person.traverse(Order.BREADTH_FIRST,
				StopEvaluator.END_OF_GRAPH,
				ReturnableEvaluator.ALL_BUT_START_NODE, Relaciones.AMIGOS,
				Direction.OUTGOING);
		for (Node friend : traverser) {
			cant++;
			// System.out.println( "Al amigo " + friend.getProperty("nombre") +
			// " lo conoció en " +friend.getProperty("lugar"));
		}
		System.out.println("Cantidad de amigos " + cant);
		end = System.currentTimeMillis();
		promedio += (end - start);
	}
}
