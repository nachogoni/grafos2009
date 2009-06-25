package org.robertoCarlos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class RobertoCarlosJava {

	public static long start;
	public static long end;

	public static void main(String[] args) {
		RobertoCarlosJava mydb = new RobertoCarlosJava();
		mydb.connect();
	}

	private void connect() {
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Integer cant = 0;
        Random r = new Random();
        String[] names = new String[] { "Diego", "Carlos", "Pipo", "Maxito",
				"Juani", "Pablo", "Martín" };

    	for(int n = 1000 ; n < 60000; n+= 1000) {
	        start = System.currentTimeMillis();
	        try {
	              Class.forName("com.mysql.jdbc.Driver");
	              String url = "jdbc:mysql://localhost:3306/neo4j";
	
	              conn = DriverManager.getConnection(url, "neo4j", "neo4j");
	              stmt = conn.createStatement();
	              stmt.execute("DROP TABLE amigos");
	              stmt.execute(	"CREATE TABLE amigos" +
	              				"("+
	            		  		"id          int AUTO_INCREMENT          not null,"+
	            		  		"nombre      char(20)                    not null,"+
	            		  		"primary key(id)" +
	            		  		")");
	
	        	for (int i = 0; i < n; i++) {
	  				
	  				int nombreIndex = r.nextInt(names.length);
	  				String name = names[nombreIndex] + i;
	
	  				stmt.execute("INSERT INTO amigos (nombre) VALUES ('" +name+ "')");
				}
	            end = System.currentTimeMillis();
	            System.out.println("La db se creó en " + (end - start) + " ms.");
	
	    		start = System.currentTimeMillis();
	    		rs = stmt.executeQuery("select count(*) as count from amigos");
	    		while(rs.next()) {
	    			cant = rs.getInt("count");
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
	
			System.out.println("Cantidad de amigos " + cant);
			end = System.currentTimeMillis();
	
			System.out.println("La consulta se hizo en " + (end - start) + " ms.");
    	}
	}
}
