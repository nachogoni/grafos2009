package org.seminario.customer;

import org.seminario.order.Order;

public interface Customer {
    public void setFirstName( String firstName );
    public String getFirstName();
    public void addOrder(Order order);
}
