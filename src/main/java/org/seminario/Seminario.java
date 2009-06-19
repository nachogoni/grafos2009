package org.seminario;

import java.util.Iterator;
import java.util.Random;

import org.neo4j.api.core.EmbeddedNeo;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Transaction;
import org.seminario.customer.Customer;
import org.seminario.customer.CustomerFactory;
import org.seminario.customer.CustomerFactoryImpl;
import org.seminario.order.Order;
import org.seminario.order.OrderFactory;
import org.seminario.order.OrderFactoryImpl;

public class Seminario {

	public static void main(String[] args) {

		NeoService neo = new EmbeddedNeo("db/seminario");
		Transaction tx = neo.beginTx();
		Customer aCustomer = null;

		try {
			createCustomers(neo);

			CustomerFactory customerFactory = new CustomerFactoryImpl(neo);

			Iterator<Customer> iter = customerFactory.getAll(); 
			while (iter.hasNext()) {
				aCustomer = iter.next();
				System.out.println("Nombre: " + aCustomer.getFirstName());
			}
				
			tx.success();

		} finally {
			tx.finish();
		}

		neo.shutdown();
	}

	private static void createCustomers(NeoService neo) {
		String[] names = {"Charly", "Jorge", "Pepe", "Nico"};
		Customer aCustomer = null;
		Order anOrder = null;
		Random r = new Random();
		
		CustomerFactory customerFactory = new CustomerFactoryImpl(neo);
		OrderFactory orderFactory = new OrderFactoryImpl(neo);

		for (String name : names) {
			anOrder = orderFactory.createOrder();
			anOrder.setOrderNumber(r.nextInt(100));

			aCustomer = customerFactory.createCustomer();
			aCustomer.setFirstName(name);
			aCustomer.addOrder(anOrder);
		}
	}

}
