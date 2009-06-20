package org.seminario;

import java.util.Iterator;
import java.util.Random;

import org.neo4j.api.core.EmbeddedNeo;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Transaction;
import org.seminario.customer.Customer;
import org.seminario.customer.CustomerFactory;
import org.seminario.customer.CustomerFactoryImpl;
import org.seminario.item.Item;
import org.seminario.item.ItemFactory;
import org.seminario.item.ItemFactoryImpl;
import org.seminario.order.Order;
import org.seminario.order.OrderFactory;
import org.seminario.order.OrderFactoryImpl;
import org.seminario.salesman.Salesman;
import org.seminario.salesman.SalesmanFactory;
import org.seminario.salesman.SalesmanFactoryImpl;

public class Seminario {

	public static void main(String[] args) {

		NeoService neo = new EmbeddedNeo("db2/seminario");
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
		String[] names = { "Charly", "Maxi", "Juani", "Laura" };
		String[] movieNames = { "bambi", "aladdin", "toy story", "dumbo",
				"rey leon" };
		String[] salesmanNames = { "Salesman1", "Salesman2", "Salesman3",
				"Salesman3" };
		Long[] prices = { 15L, 20L, 30L, 10L, 20L };
		Customer aCustomer = null;
		Salesman aSalesman = null;
		Order anOrder = null;
		Item anItem = null;

		Random r = new Random();

		ItemFactory itemFactory = new ItemFactoryImpl(neo);
		CustomerFactory customerFactory = new CustomerFactoryImpl(neo);
		OrderFactory orderFactory = new OrderFactoryImpl(neo);
		SalesmanFactory salesmanFactory = new SalesmanFactoryImpl(neo);

		for (String name : names) {
			// Order is created.
			anOrder = orderFactory.createOrder();
			anOrder.setOrderNumber(r.nextInt(100));

			// Salesman is created and it's added to the order.
			aSalesman = salesmanFactory.createSalesman();
			aSalesman.setFirstName(salesmanNames[r
					.nextInt(salesmanNames.length)]);
			anOrder.setSalesman(aSalesman);

			// Orders will have 0-3 items
			for (int i = 0; i < r.nextInt(3); i++) {
				// Item is created and added.
				anItem = itemFactory.createItem();
				anItem.setName(movieNames[r.nextInt(movieNames.length)]);
				anItem.setPrice(prices[r.nextInt(prices.length)]);
				anOrder.addItem(anItem);
			}

			// Customer is created and order is added.
			aCustomer = customerFactory.createCustomer();
			aCustomer.setFirstName(name);
			aCustomer.addOrder(anOrder);
		}
	}

}
