package org.seminario.order;

import org.seminario.customer.Customer;

public interface Order {
    public void setOrderNumber(long orderNumber);
    public long getOrderNumber();
    public Customer getCustomer();
}
