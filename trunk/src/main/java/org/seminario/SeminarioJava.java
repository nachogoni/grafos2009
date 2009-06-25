package org.seminario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SeminarioJava {
	public static long start;
	public static long end;

	public static void main(String[] args) {
		SeminarioJava mydb = new SeminarioJava();
		mydb.connect();
	}

	private void connect() {
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        start = System.currentTimeMillis();
        try {
              Class.forName("com.mysql.jdbc.Driver");
              String url = "jdbc:mysql://localhost:3306/video";

              conn = DriverManager.getConnection(url, "video", "video");
              stmt = conn.createStatement();

              //Los inserts estan hechos a mano en la db.
//        	for (int i = 0; i < 100000; i++) {
//  				
//  				int nombreIndex = r.nextInt(names.length);
//  				String name = names[nombreIndex] + i;
//
//  				stmt.execute("INSERT INTO amigos (nombre) VALUES ('" +name+ "')");
//			}
            end = System.currentTimeMillis();
            System.out.println("La db se creó en " + (end - start) + " ms.");

    		start = System.currentTimeMillis();
    		rs = stmt.executeQuery(
    				"SELECT orders.number, customers.first_name, "+
    	    		"salesmans.first_name, items.name, items.price "+
    	    		"FROM orders JOIN (items_per_order, customers, salesmans, items ) "+
    	    		"ON (orders.id=items_per_order.order_id AND	"+
    	    		"orders.customer_id = customers.id AND "+
    	    		"orders.salesman_id = salesmans.id AND "+
    	    		"items_per_order.item_id = items.id)");

    		while(rs.next()) {
    			String ret = null;
				ret = String.format("Order: %d\nSalesman: %s\n Items:\n",
						rs.getInt("orders.number"),
						rs.getString("salesmans.first_name"));

				ret += String.format("%s ________ %d\n",
						rs.getString("items.name"),
						43);
    		}

        } catch (ClassNotFoundException exception) {
              exception.printStackTrace();
        } catch(SQLException sqlex) {
              sqlex.printStackTrace();
        } finally {
              try {
                    rs.close();
              } catch(Exception e) {}
              try {
                    stmt.close();
              } catch(Exception e) {}
              try {
                    conn.close();
              } catch(Exception e) {}
        }

		end = System.currentTimeMillis();

		System.out.println("La consulta se hizo en " + (end - start) + " ms.");
	}
	
}
