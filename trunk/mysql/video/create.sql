DROP database IF EXISTS video;

CREATE database IF NOT EXISTS video DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;

use video;

DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS salesmans;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS items_per_order;

CREATE TABLE customers
(
    id          int AUTO_INCREMENT          not null,
    first_name  varchar(50)                 not null,
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

CREATE TABLE salesmans
(
    id          int AUTO_INCREMENT          not null,
    first_name  varchar(50)                 not null,
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

CREATE TABLE orders
(
    id          int AUTO_INCREMENT          not null,
    number      int                         not null,
    salesman_id int                         not null,
    customer_id int                         not null,
    foreign key(salesman_id) references salesmans(id),
    foreign key(customer_id) references customers(id),
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

CREATE TABLE items
(
    id          int AUTO_INCREMENT          not null,
    name        varchar(50)                 not null,
    price       decimal(10,2)               not null,
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

CREATE TABLE items_per_order
(
    id          int AUTO_INCREMENT          not null,
    item_id     int                         not null,
    order_id    int                         not null,
    foreign key(item_id) references items(id),
    foreign key(order_id) references orders(id),
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

-- Permisos
GRANT ALL ON video.* TO 'video'@'localhost' IDENTIFIED BY 'video';
