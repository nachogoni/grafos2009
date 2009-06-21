use video;

INSERT INTO customers VALUES(NULL, 'Charly');
INSERT INTO customers VALUES(NULL, 'Maxi');
INSERT INTO customers VALUES(NULL, 'Juani');
INSERT INTO customers VALUES(NULL, 'Laura');

INSERT INTO salesmans VALUES(NULL, 'Salesman1');
INSERT INTO salesmans VALUES(NULL, 'Salesman2');
INSERT INTO salesmans VALUES(NULL, 'Salesman3');
INSERT INTO salesmans VALUES(NULL, 'Salesman4');

INSERT INTO items VALUES(NULL, 'bambi', 15);
INSERT INTO items VALUES(NULL, 'aladdin', 20);
INSERT INTO items VALUES(NULL, 'toy story', 30);
INSERT INTO items VALUES(NULL, 'dumbo', 10);
INSERT INTO items VALUES(NULL, 'rey leon', 20);

INSERT INTO orders VALUES(NULL, 100, 1, 1);
INSERT INTO orders VALUES(NULL, 101, 2, 2);
INSERT INTO orders VALUES(NULL, 102, 3, 3);

INSERT INTO items_per_order VALUES(NULL, 1, 1);
INSERT INTO items_per_order VALUES(NULL, 2, 1);
INSERT INTO items_per_order VALUES(NULL, 3, 1);
INSERT INTO items_per_order VALUES(NULL, 4, 2);
INSERT INTO items_per_order VALUES(NULL, 3, 2);

commit;