package uk.ac.cardiff.comsc.kis.peeps.vendor.database.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.mediator.Peeps;
import uk.ac.cardiff.comsc.kis.peeps.vendor.agent.VendorAgent;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.DatabaseConnection;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.results.Result;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.results.ResultCollection;

public class DatabaseDataCreator {

	/** Logger */
	static Logger logger = Logger.getLogger(DatabaseDataCreator.class);

	/** Database connection */
	Connection conn = null;


	/** Empty constructor */
	public DatabaseDataCreator() {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating new DatabaseDataCreator");
		}
	}

	/** Main Methods */
	
	public void openConnection() {
		if (logger.isDebugEnabled()) {
			logger.debug("Opening Connection to Database");
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn  = DriverManager.getConnection("jdbc:mysql:///PEEPS_Example_DB", "root", "rootpword");
			
			if (!conn.isClosed()) {
				if (logger.isDebugEnabled()) {
					logger.info("Successfully connected to MySQL server using TCP/IP...");
				}
			}
		} catch (Exception e) {
			logger.error("Error opening connection to database: " + e.getMessage());
		}
	}
	
	public void closeConnection() {
		if (logger.isDebugEnabled()) {
			logger.debug("Closing connection to Database");
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Error closing connection to database: " + e.getMessage());
		}
	}

	
	
	
	public void createData(String modeData, int numAttributes, int[] numValuesPerAttribute, String modeQtyGen, int minQty, int maxQty, String modePriceGen, double minPrice, double maxPrice, int sizeOfDB) {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating data using method " + modeData);
		}

		if (modeData.equals("car_simple_example")) {
			try {
				Statement st = conn.createStatement();
				
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('BMW', '850', 'Black', 3000, 'Y', 'Y', 25999.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Mercedes', '400CSL', 'Silver', 3000, 'Y', 'Y', 27999.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Toyota', 'MR2', 'Silver', 1998, 'Y', 'N', 6250.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Mercedes', '350CSL', 'Silver', 2500, 'Y', 'N', 26999.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Mercedes', '350CSL', 'White', 3500, 'N', 'N', 22999.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Ford', 'Mondeo', 'Silver', 2000, 'Y', 'N', 12999.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Mercedes', '350CSL', 'Black', 4000, 'N', 'N', 22999.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Ford', 'Focus', 'Blue', 1500, 'N', 'N', 12999.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Lexus', 'IS200', 'Green', 2500, 'Y', 'Y', 12499.00)");
				st.executeUpdate("INSERT INTO Stock (make, model, colour, engine_size, electric_windows, sunroof, price) " +
						"VALUES ('Mercedes', '200CSL', 'Black', 1500, 'N', 'N', 13499.00)");
				
			} catch (Exception e) {
				System.err.println("Exception: " + e.getMessage());
			}
			
		} else if (modeData.equals("random-uniform")) {
			
			try {
				Statement st = conn.createStatement();

				String sql = new String();
				String valueToInsert = new String();

				RandomData randomData = new RandomDataImpl(); 

				for (int val = 1; val <= sizeOfDB; val++) {
					sql = "INSERT INTO Stock (";

					for (int attNum = 1; attNum <= numAttributes; attNum++) {
						sql += "Att" + attNum + ", ";
					}

					sql += "Quantity, Price) VALUES (";

					for (int attNum = 1; attNum <= numAttributes; attNum++) {

						// Choose what value to put in!
						valueToInsert = "" + (int)randomData.nextUniform(1, numValuesPerAttribute[attNum -1] + 1);
						
						// Put value in
						sql += "'" + valueToInsert + "', ";
					}

					if (modeQtyGen.equals("random-uniform")) {
						sql += "" + (int)randomData.nextUniform(minQty, maxQty);
					}
					sql += ", ";
					if (modePriceGen.equals("random-uniform")) {
						sql += "" + (double)randomData.nextUniform(minPrice, maxPrice);
					}
					
					sql += ")";

					if (logger.isDebugEnabled()) {
						logger.debug("* Running: " + sql);
					}
					st.executeUpdate(sql);
				}
				
			} catch (Exception e) {
				System.err.println("Exception: " + e.getMessage());
			}

		}
	}

	public void clearExistingData() {
		if (logger.isDebugEnabled()) {
			logger.debug("Clearing database");
		}

		try {
			Statement st = conn.createStatement();

			st.executeUpdate("DELETE FROM Stock");
			
			st.executeUpdate("DELETE FROM StockTEMP");
			
			st.executeUpdate("DROP TABLE Stock");
			
			st.executeUpdate("DROP TABLE StockTEMP");

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
	}
	
	
	public void setupDatabase(String dataMode, int numAttributes) {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating DB");
		}

		try {
			Statement st = conn.createStatement();

			if (dataMode.equals("car_simple_example")) {
				st.executeUpdate("" +
						"create table Stock (" +
				        "id integer NOT NULL AUTO_INCREMENT," +
				        "make varchar(20)," +
				        "model varchar(20)," +
				        "colour varchar(20)," +
				        "engine_size integer," +
				        "electric_windows char(1)," +
				        "sunroof char(1)," +
				        "price decimal(7,2)," +
				        "primary key (id)" +
				        ")"
				);
				st.executeUpdate("" +
						"create table StockTEMP (" +
				        "id integer NOT NULL," +
				        "make varchar(20)," +
				        "model varchar(20)," +
				        "colour varchar(20)," +
				        "engine_size integer," +
				        "electric_windows char(1)," +
				        "sunroof char(1)," +
				        "price decimal(7,2)," +
				        "primary key (id)" +
				        ")"
				);
				
				
			} else if (dataMode.equals("random-uniform")) {

				String sql = new String();
				sql  = "create table Stock (";
		        sql += "id integer NOT NULL AUTO_INCREMENT,";

		        for (int i = 1; i <= numAttributes; i++) {
		        	sql += "Att" + i + " varchar(20),";
		        }
		        
		        sql += "quantity integer, ";
		        sql += "price decimal(7,2),";
		        sql += "primary key (id)";
		        sql += ")";

				st.executeUpdate(sql);

				String sqlTemp = new String();
				sqlTemp  = "create table StockTEMP (";
		        sqlTemp += "id integer NOT NULL AUTO_INCREMENT,";

		        for (int i = 1; i <= numAttributes; i++) {
		        	sqlTemp += "Att" + i + " varchar(20),";
		        }
		        
		        sqlTemp += "quantity integer, ";
		        sqlTemp += "price decimal(7,2),";
		        sqlTemp += "primary key (id)";
		        sqlTemp += ")";

				st.executeUpdate(sqlTemp);

			}

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
	}

	public void generateData(String modeData, int numAttributes, int[] numValuesPerAttribute, String modeQtyGen, int minQty, int maxQty, String modePriceGen, double minPrice, double maxPrice, int sizeOfDB) {
		
		openConnection();
		clearExistingData();
		setupDatabase(modeData, numAttributes);
		createData(modeData, numAttributes, numValuesPerAttribute, modeQtyGen, minQty, maxQty, modePriceGen, minPrice, maxPrice, sizeOfDB);
		closeConnection();
	}

}
