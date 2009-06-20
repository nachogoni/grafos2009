package org.seminario.order;

import java.util.Collection;

import org.seminario.customer.Customer;
import org.seminario.item.Item;
import org.seminario.salesman.Salesman;

public interface Order {
    public void setOrderNumber(long orderNumber);
    public long getOrderNumber();
    public void addItem(Item item);
    public Collection<Item> getItems();
    public void setSalesman(Salesman salesman);
    public Salesman getSalesman();
    public Customer getCustomer();
}
