package org.seminario.customer;

import java.util.Iterator;

import org.seminario.order.Order;

public interface Customer {
    public void setFirstName( String firstName );
    public String getFirstName();
    public void addOrder(Order order);
    public Iterator<Order> getAllOrders();
}
