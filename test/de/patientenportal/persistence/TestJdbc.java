package de.patientenportal.persistence;


import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	private static Connection myConnection;

	public static void main(String[] args) {

		String jdbcUrl = "jdbc:mysql://localhost:3306/patientenportal?useSSL=false";
		String user = "root";
		String pass = "";
		
		try {
			System.out.println("Connecting to database: " + jdbcUrl);
			
			myConnection = DriverManager.getConnection(jdbcUrl, user, pass);
			
			System.out.println("Connection successful: " + myConnection);
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}

}