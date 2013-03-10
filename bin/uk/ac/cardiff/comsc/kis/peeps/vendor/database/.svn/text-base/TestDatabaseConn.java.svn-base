package uk.ac.cardiff.comsc.kis.peeps.vendor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConn {

	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn  = DriverManager.getConnection("jdbc:mysql:///PEEPS_Example_DB", "root", "rootpword");
			
			if (!conn.isClosed()) {
				System.out.println("Successfully connected to MySQL server using TCP/IP...");
			}
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println("Exception: " + e.getMessage());
			}
		}
	}
		
}