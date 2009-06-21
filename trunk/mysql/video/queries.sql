
SELECT orders.number, customers.first_name, salesmans.first_name, items.name
FROM orders JOIN (items_per_order, customers, salesmans, items ) ON (orders.id=items_per_order.order_id AND
orders.customer_id = customers.id AND
orders.salesman_id = salesmans.id AND
items_per_order.item_id = items.id);
