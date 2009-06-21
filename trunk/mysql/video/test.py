# -*- coding: utf-8 -*-

def main():
    names = ["Charly", "Maxi", "Juani", "Laura"]
    movieNames = ["bambi", "aladdin", "toy story", "dumbo", "rey leon"]
    salesmanNames = ["Salesman1", "Salesman2", "Salesman3", "Salesman3"]
    prices = [ 15, 20, 30, 10, 20 ]

    customers = 1
    salesmans = 1
    orders = 1
    items = 1

    print 'use video;'

    for i in range(10000):
        j = 0

        for name in names:
            print 'INSERT INTO salesmans VALUES(%s, \'%s\');' %(salesmans, salesmanNames[j])
            print 'INSERT INTO customers VALUES(%s, \'%s\');' %(customers, name)
            print 'INSERT INTO orders VALUES(%s, %s, %s, %s);' %(orders, i+1, salesmans, customers)

            for k in range(2):
                print 'INSERT INTO items VALUES(%s, \'%s\', %s);' %(items, movieNames[(j+k) % len(movieNames)], prices[(j+k)%len(prices)])
                print 'INSERT INTO items_per_order VALUES(NULL, %s, %s);' %(items, orders)
                items += 1

            salesmans += 1
            customers += 1
            orders += 1
            j += 1

if __name__ == '__main__':
    main()