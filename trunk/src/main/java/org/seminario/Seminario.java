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

		NeoService neo = new EmbeddedNeo("db/seminario");
		Transaction tx = null;
		Customer aCustomer = null;
		Order anOrder = null;
		Long start, end;
		
		for(int n = 1000 ; n < 8000 ; n+=1000) {
			
			tx = neo.beginTx();
			try {
				start = System.currentTimeMillis();
				//Normal for plugin demo.
	//			createCustomers(neo);
	//			Big for benchmarks!
				createLotsOfCustomers(neo, n);
	
				end = System.currentTimeMillis();
				System.out.println("La db se creó en " + (end - start) + " ms.");
				System.out.println(n);
				tx.success();
			} finally {
				tx.finish();
			}
			
			tx = neo.beginTx();
			try {
				start = System.currentTimeMillis();
				CustomerFactory customerFactory = new CustomerFactoryImpl(neo);
	
				Iterator<Customer> iter = customerFactory.getAll();
				while (iter.hasNext()) {
					aCustomer = iter.next();
	
					Iterator<Order> orderIter = aCustomer.getAllOrders();
					while (orderIter.hasNext()) {
						anOrder = orderIter.next();
						String ret = null;
						ret = String.format("Order: %d\nSalesman: %s\n Items:\n",
								anOrder.getOrderNumber(), anOrder.getSalesman()
										.getFirstName());
	
						for (Item item : anOrder.getItems()) {
							ret += String.format("%s ________ %d\n",
									item.getName(), item.getPrice());
						}
					}
				}
	
				tx.success();
	
			} finally {
				tx.finish();
			}

			end = System.currentTimeMillis();
			System.out.println("La consulta se hizo en " + (end - start) + " ms.");
		}
		neo.shutdown();
	}

	private static void createLotsOfCustomers(NeoService neo, int n) {
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

		ItemFactory itemFactory = new ItemFactoryImpl(neo);
		CustomerFactory customerFactory = new CustomerFactoryImpl(neo);
		OrderFactory orderFactory = new OrderFactoryImpl(neo);
		SalesmanFactory salesmanFactory = new SalesmanFactoryImpl(neo);

//		1000 loops
		for(int i = 0 ; i < n ; i++) {
			int j = 0;
			for(String name : names) {
				// Order is created.
				anOrder = orderFactory.createOrder();
				anOrder.setOrderNumber(1+i);
				
				// Salesman is created and it's added to the order.
				aSalesman = salesmanFactory.createSalesman();
				aSalesman.setFirstName(salesmanNames[j]);
				anOrder.setSalesman(aSalesman);	

				// Orders will have 2 items
				for (int k = 0; k < 2; k++) {
					// Item is created and added.
					anItem = itemFactory.createItem();
					anItem.setName(movieNames[(j+k) % movieNames.length]);
					anItem.setPrice(prices[(j+k) % prices.length]);
					anOrder.addItem(anItem);
				}

				// Customer is created and order is added.
				aCustomer = customerFactory.createCustomer();
				aCustomer.setFirstName(name);
				aCustomer.addOrder(anOrder);
				j++;
			}
		}
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
			for (int i = 0; i < 1 + r.nextInt(3); i++) {
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
