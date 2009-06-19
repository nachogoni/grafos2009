package org.seminario.customer;

import java.util.Iterator;

public interface CustomerFactory {
	public Customer createCustomer();
	public Iterator<Customer> getAll();
}
