package uk.ac.cardiff.comsc.kis.peeps.vendor.database.results;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.vendor.database.DatabaseConnection;

public class ResultCollection implements Iterable<Result>{
	
	/** Logger */
	static Logger logger = Logger.getLogger(ResultCollection.class);

	/** ArrayList to hold the collection of Vertices */
	private ArrayList<Result> resultCollection;

	
	/** Empty Constructor */
	public ResultCollection() {
		if (logger.isDebugEnabled()) {
			logger.debug("New ResultCollection object created");
		}
		resultCollection = new ArrayList<Result>();
	}
	
	/** Main methods */

	public void parseResultSet(ResultSet rs) {
		if (logger.isDebugEnabled()) {
			logger.debug("Parsing Result Set into Result Collection: ");
		}
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				if (logger.isDebugEnabled()) {
					logger.debug("There are results left. Adding to resultCollection");
				}
				ArrayList<String> values = new ArrayList<String>();

				for (int i = 3; i <= rsmd.getColumnCount() - 1; i++) {
					values.add(rs.getString(i));
				}
				add(new Result(rs.getInt(2), values, rs.getDouble(rsmd.getColumnCount())));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("There are no more results left. All done!");
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}
	
	public void saveToDB(int mode, int runID) {
		if (logger.isInfoEnabled()) {
			logger.info("Saving ResultCollection to database");
		}
		
		// INSERT INTO Results_Run1_BM ()
		// INSERT INTO Results_Presort ()
		
		String sqlToInsertItemCommonStart = new String();
		int itemNum = 1;
		switch (mode) {
		case 0: // Benchmark
			sqlToInsertItemCommonStart = "INSERT INTO Results_Run" + runID + "_BM VALUES (";
			break;
		case 1: // Normal - presort
			sqlToInsertItemCommonStart = "INSERT INTO Results_Run" + runID + "_Presort VALUES (";
			break;
		case 2: // Normal - Final sort
			sqlToInsertItemCommonStart = "INSERT INTO Results_Run" + runID + " VALUES (";
		}
		
		String sqlToInsertItem = new String();

		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.openConnection();

		// Now for each result, add to db
		for (Result res: resultCollection) {
			// First, the common start
			sqlToInsertItem = sqlToInsertItemCommonStart;
			// Next, the prefrank and item id
			//sqlToInsertItem += res.getPrefrank() + "," + res.getId() + ",";
			sqlToInsertItem += itemNum + ", " + res.getId() + ", ";
			// Next, the values
			for (String value : res.getAttributes()) {
				sqlToInsertItem += value + ",";
			}
			// Finally, the price
			sqlToInsertItem += res.getPrice() + ")";

			// Now run the sql to insert the item
			if (logger.isDebugEnabled()) {
				logger.debug("* Executing SQL to insert item = " + sqlToInsertItem);
			}
			dbConn.executeSQL(sqlToInsertItem);
			
			itemNum++;
		}
		
		dbConn.closeConnection();
		dbConn = null;
		}
	
	
	public int add(Result result) {
		if (logger.isDebugEnabled()) {
			logger.debug("Adding result to resultCollection");
		}
		if (!doesResultWithIDAlreadyExist(result.getId())) {
			if (logger.isDebugEnabled()) {
				logger.debug("* Result unique, adding");
			}
			resultCollection.add(result);
			return 1;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("* Result already in ResultCollection, not adding");
			}
			return 0;
		}
	}
	
	public int add(ResultCollection resultCollectionToAdd) {
		if (logger.isDebugEnabled()) {
			logger.debug("Adding ResultCollection to ResultCollection");
		}
		int numAdded = 0;
		for (Result result : resultCollectionToAdd) {
			if (logger.isDebugEnabled()) {
				logger.debug("* Adding result " + result.toString());
			}
			numAdded += add(result);
		}
		return numAdded;
	}
	
	public void remove(Result result) {
		if (logger.isDebugEnabled()) {
			logger.debug("Removing result from ResultCollection");
		}
		resultCollection.remove(result);
	}

	public Result get(int index) {
		return resultCollection.get(index);
	}
	
	public int indexOf(Result result) {
		return resultCollection.indexOf(result);
	}
	
	public int getIndexOfResultWithID(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for result with id " + id);
		}
		
		for (Result res : resultCollection) {
			if (logger.isDebugEnabled()) {
				logger.debug("Looking at result " + res.getId());
			}
			if (res.getId() == id) {
				if (logger.isDebugEnabled()) {
					logger.debug("Match!");
				}
				return resultCollection.indexOf(res); 
			}
		}
		
		return -1;
	}
	
	public boolean doesResultWithIDAlreadyExist(int id) {
		if (getIndexOfResultWithID(id) == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	public void sort(String sortMethod) {
		if (sortMethod.equals("Price")) {
			Collections.sort(resultCollection,Result.PriceComparator);
		} else if (sortMethod.equals("Stock")) {
			Collections.sort(resultCollection,Result.StockComparator);
		}
	}

	
	public int size() {
		return resultCollection.size();
	}
	
	public int sizeInChars() {
		int out = 0;
		for (Result res: resultCollection) {
			out += res.toString().length();
		}
		return out;
	}
	
	public boolean isEmpty() {
		return resultCollection.isEmpty();
	}

	
	/** Object output methods */
	
	public Iterator<Result> iterator() {
		return resultCollection.iterator();
	}
	
	public String toString() {
		String out = new String();
		
		for (Result res: resultCollection) {
			out += res.toString() + "\n";
		}
		return out;
	}
}
