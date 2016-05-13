package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

private static final String jdbcUrl="jdbc:mysql://localhost/metroparis?user=root&password=";
	
	public static Connection getConnection(){
		try {
			Connection con = DriverManager.getConnection(jdbcUrl);
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("errore connessione", e);
		}
		
	}
	
}
