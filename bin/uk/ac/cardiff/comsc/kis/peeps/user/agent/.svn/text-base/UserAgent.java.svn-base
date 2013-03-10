 package uk.ac.cardiff.comsc.kis.peeps.user.agent;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.mediator.Peeps;
import uk.ac.cardiff.comsc.kis.peeps.mediator.evaluation.PeepsRunStockDB;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.PreferenceSet;
import uk.ac.cardiff.comsc.kis.peeps.preference.subset.PreferenceSubset;
import uk.ac.cardiff.comsc.kis.peeps.preference.subset.PreferenceSubsetCollection;
import uk.ac.cardiff.comsc.kis.peeps.preference.utils.PreferenceConverter;
import uk.ac.cardiff.comsc.kis.peeps.vendor.agent.VendorAgent;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.DatabaseConnection;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.results.Result;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.results.ResultCollection;

public class UserAgent {
	
	private static int QUERY_NORMAL = 0;
	private static int QUERY_FINALSORT = 1; 
	
	private static int MODE_BENCHMARK = 0;
	private static int MODE_ALG = 1;
	private static int MODE_FINALSORT = 2;

	/** Logger */
	static Logger logger = Logger.getLogger(UserAgent.class);
	
	// The user's preferences 
	PreferenceSet preferenceSet;
	
	// Their preferences split into subsets
	PreferenceSubsetCollection preferenceSubsetCollectionBenchmark;
	PreferenceSubsetCollection preferenceSubsetCollection;
	
	// A collection of SQL queries representing their prefs 
	ArrayList<String> queryCollectionBenchmark;
	ArrayList<String> queryCollection;
	ArrayList<String> queryCollectionFinalsort;
	
	// The result collection returned
	ResultCollection resultCollectionBenchmark;
	ResultCollection resultCollection;
	ResultCollection resultCollectionFinalsort;
	
	// The number of results to get
	int numResultsToGet;
	
	// The measurements for this run
	
	private int measureTrafficSent; // in bytes
	private int measureTrafficReceived; // in bytes
	private int measureTraffic;
	
	private int numQueriesSent; // in numbers ;-)
	
	private int largestSubsetSent;

	private int measureExplMostPreferred;
	private int measureExplTopNRem;
	
	private int runID; // The run id to pass to the savetodb method
	private PeepsRunStockDB peepsRunStockDB; // The database to pass to the subset->sql converter


	/** Constructors */
	
	public UserAgent(int _runID, PeepsRunStockDB _peepsRunStockDB) {
		if (logger.isDebugEnabled()) {
			logger.debug("Created new UserAgent Object");
		}
		this.runID = _runID;
		this.peepsRunStockDB = _peepsRunStockDB;
	}

	
	/** Getters and setters */
	
	public PreferenceSet getPrefs() {
		return preferenceSet;
	}

	public void setPrefs(PreferenceSet preferenceSet) {
		this.preferenceSet = preferenceSet;
	}
	
	public PreferenceSubsetCollection getPreferenceSubsetCollection() {
		return preferenceSubsetCollection;
	}

	public void setPreferenceSubsetCollection(PreferenceSubsetCollection preferenceSubsetCollection) {
		this.preferenceSubsetCollection = preferenceSubsetCollection;
	}

	
	public ResultCollection getResultCollection() {
		return resultCollection;
	}
	
	public void setResultCollection(ResultCollection resultCollection) {
		this.resultCollection = resultCollection;
	}

	
	public ArrayList<String> getQueryCollection() {
		return queryCollection;
	}

	public void setQueryCollection(ArrayList<String> queryCollection) {
		this.queryCollection = queryCollection;
	}


	public void setNumberOfResultsToGet(int numResultsToGet) {
		this.numResultsToGet = numResultsToGet;
	}
	
	public int getMeasureTrafficOverall() {
		return measureTraffic;
	}
	
	public int getMeasureTrafficSent() {
		return measureTrafficSent;
	}
	
	public int getMeasureTrafficReceived() {
		return measureTrafficReceived;
	}
	
	public int getLargestSubsetSent() {
		return largestSubsetSent;
	}
	
	public int getTotalPreferenceSetSize() {
		return preferenceSet.getTotalPreferenceSetSize();
	}
	
	public int getNumberOfResultsReturned() {
		return resultCollection.size();
	}
	
	public int getNumOfQueriesSent() {
		return numQueriesSent;
	}
	
	public int getMeasureExplMostPreferred() {
		return measureExplMostPreferred;
	}
	
	public int getMeasureExplTopNRem() {
		return measureExplTopNRem;
	}

	
	/** public methods */
	
	public void setupDBSupportingTables() {
		createDBSupportingTables(runID, peepsRunStockDB);
	}

	public void runBenchmark(VendorAgent va) {
		preferenceSubsetCollectionBenchmark = createSubsets(Peeps.SUBSALG_BENCHMARK);
		queryCollectionBenchmark = createQueries(preferenceSubsetCollectionBenchmark, QUERY_NORMAL);
		resultCollectionBenchmark = sendQueries(va, preferenceSubsetCollectionBenchmark, queryCollectionBenchmark, MODE_BENCHMARK);
	}
	
	public void runGradualRelease(VendorAgent va, int subsetAlgorithm) {
		preferenceSubsetCollection = createSubsets(subsetAlgorithm);
		queryCollection = createQueries(preferenceSubsetCollection, QUERY_NORMAL);
		resultCollection = sendQueries(va, preferenceSubsetCollection, queryCollection, MODE_ALG);
	}
	
	public void saveBenchmarkResultsToDB() {
		saveResultsToDB(0, resultCollectionBenchmark);
	}
	
	public void saveResultsToDB() {
		saveResultsToDB(1, resultCollection);
	}
	
	public void sortResultsByPreference(VendorAgent va) {
		if (logger.isInfoEnabled()) {
			logger.info("* # Running final sort process");
		}
		queryCollectionFinalsort = createQueries(preferenceSubsetCollectionBenchmark, QUERY_FINALSORT);
		resultCollectionFinalsort = sendQueries(va,preferenceSubsetCollectionBenchmark, queryCollectionFinalsort, MODE_FINALSORT);
	}
	
	public void saveFinalResultsToDB() {
		saveResultsToDB(2, resultCollectionFinalsort);
	}

	public void calculateMeasures() {
		
		// Network traffic
		measureTraffic = measureTrafficSent + measureTrafficReceived;
		
		// Exploitation measures

		// Get the position in the benchmark results of the result that is at the top of the actual results
		measureExplMostPreferred = resultCollectionBenchmark.getIndexOfResultWithID(((Result)resultCollectionFinalsort.get(0)).getId());
		measureExplTopNRem = (numResultsToGet - 1) - resultCollectionFinalsort.getIndexOfResultWithID(((Result)resultCollectionBenchmark.get(numResultsToGet - 1)).getId());
		
	}

	public void doShowPreferences() {
		System.out.println(showPreferences());
	}

	public void doMakePreferencesFullyDirected() {
		if (logger.isDebugEnabled()) {
			logger.debug("Making user preferences fully directed");
		}
		preferenceSet.makeGraphFullyDirected("in-input-order");
	}

	public void setThingsToNull() {
		// Their preferences split into subsets
		preferenceSubsetCollectionBenchmark = null;
		preferenceSubsetCollection = null;
		
		// A collection of SQL queries representing their prefs 
		queryCollectionBenchmark = null;
		queryCollection = null;
		queryCollectionFinalsort = null;
		
		// The result collection returned
		resultCollectionBenchmark = null;
		resultCollection = null;
		resultCollectionFinalsort = null;
	}
	

	/** private methods */
	
	private PreferenceSubsetCollection createSubsets(int method) {
		if (logger.isInfoEnabled()) {
			logger.info("* Creating preference subsets from preferences, using method " + Peeps.getName(method));
		}
		// Convert Preferences to a set of preference subsets
		return PreferenceConverter.PreferenceSetToPreferenceSubsetCollection(preferenceSet, method);
	}
	
	private ArrayList<String> createQueries(PreferenceSubsetCollection preferenceSubsetCollectionToConvert, int createQueryMode) {
		if (logger.isInfoEnabled()) {
			logger.info("* Creating SQL Queries");
		}
		ArrayList<String> queries = new ArrayList<String>();
		// Next, create a query for each preferences subset
       	for (PreferenceSubset prefSubset : preferenceSubsetCollectionToConvert) {
       		queries.add(PreferenceConverter.PreferenceSubsetToSQL(prefSubset,peepsRunStockDB.getID(), runID, createQueryMode));
       	}
		return queries;
	}

	private ResultCollection sendQueries(VendorAgent va, PreferenceSubsetCollection preferenceSubsetCollection, ArrayList<String> queries, int mode) {
		// Turn on or off benchmarking mode - no measures when in bm mode
		boolean measuring = false;
		if (mode == MODE_ALG) {
			measuring = true;
		} 
		
		if (measuring) {
			numQueriesSent = 0;
			measureTrafficSent = 0;
			measureTrafficReceived = 0;
			largestSubsetSent = 0;
		}

		if (logger.isInfoEnabled()) {
			logger.info("* Sending queries to VendorAgent (measuring mode is " + measuring + ")");
		}
				
		// Set up a few things first
		ResultCollection results = new ResultCollection();
		ResultCollection resCollForThisQuery;
		Iterator it = queries.iterator();
		int numResultsSoFar = 0;

		va.openDBConn();
		// While there are still queries to send and we don't have enough results
		while (it.hasNext() && (numResultsSoFar < numResultsToGet || numResultsToGet == -1)) {
			// Get the next query, add the size of it if not bm-ing
			String query = (String)it.next();
			if (measuring) {
				if (logger.isDebugEnabled()) {
					logger.debug("Largest sized prefsubset so far is " + largestSubsetSent + ". Current is " + ((PreferenceSubset)(preferenceSubsetCollection.getSubset(numQueriesSent))).getSize() + ".");
				}
				if (((PreferenceSubset)(preferenceSubsetCollection.getSubset(numQueriesSent))).getSize() > largestSubsetSent) {
					largestSubsetSent = ((PreferenceSubset)(preferenceSubsetCollection.getSubset(numQueriesSent))).getSize();
				}
				numQueriesSent++;
				measureTrafficSent += query.length();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("* * Sending query: " + query);
			}

			// Send the query to the va, add the size of the results returned if not bm-ing
			resCollForThisQuery = va.runQuery(query);
			if (measuring) {
				measureTrafficReceived += resCollForThisQuery.sizeInChars();
			}
			
			// Add the results received for this particular query to the collection of all results, keep track of how many results we have
			numResultsSoFar += results.add(resCollForThisQuery);
						
			// Display info about the res set
			if (logger.isDebugEnabled()) {
				logger.debug("* * * Result set returned contains " + resCollForThisQuery.size() + " items:");
				if (!(resCollForThisQuery.size() == 0)) {
					logger.debug("* * * " + resCollForThisQuery.toString());
				}
				logger.debug("* * * Total number of results so far: " + numResultsSoFar);
			}
		}
		va.closeDBConn();
		
		return results;
	}	
	
	private void createDBSupportingTables(int runID, PeepsRunStockDB peepsRunStockDB) {
		if (logger.isInfoEnabled()) {
			logger.info("Creating supporting tables in DB for run " + runID + " and StockDB with id " + peepsRunStockDB.getID());
		}

		String sqlRestableBM = new String(); // sql to create table for bm results
		String sqlRestablePS = new String(); // sql to create table for presorted results
		String sqlRestableAC = new String(); // sql to create table for full results
		
		// start with the table names
		sqlRestableBM = "CREATE TABLE Results_Run" + runID + "_BM (";
		sqlRestablePS = "CREATE TABLE Results_Run" + runID + "_Presort (";
		sqlRestableAC = "CREATE TABLE Results_Run" + runID + " (";
		
		// Now we want the common bits
		sqlRestableBM += "prefrank integer NOT NULL, id integer NOT NULL,";
		sqlRestablePS += "prefrank integer NOT NULL, id integer NOT NULL,";
		sqlRestableAC += "prefrank integer NOT NULL, id integer NOT NULL,";
		
		// Now the variable bits
		switch(peepsRunStockDB.getDataGenMethod()) {
		case Peeps.DATAGEN_CAR_SIMPLE_EXAMPLE:
			sqlRestableBM += "make char(10), model char(10), colour char(10), engine_size char(10), electric_windows char(1), sunroof char(1),";
			sqlRestablePS += "make char(10), model char(10), colour char(10), engine_size char(10), electric_windows char(1), sunroof char(1),";
			sqlRestableAC += "make char(10), model char(10), colour char(10), engine_size char(10), electric_windows char(1), sunroof char(1),";
			break;
		case Peeps.DATAGEN_RANDOM_UNIFORM:
	        for (int i = 1; i <= peepsRunStockDB.getNumAttributes(); i++) {
	        	sqlRestableBM += "Attr_" + i + " char(2),";
	        	sqlRestablePS += "Attr_" + i + " char(2),";
	        	sqlRestableAC += "Attr_" + i + " char(2),";
	        }
	        break;
		}
		
		// and finally the common last bits
		sqlRestableBM += "price decimal(7,2), primary key (id) )";
		sqlRestablePS += "price decimal(7,2), primary key (id) )";
		sqlRestableAC += "price decimal(7,2), primary key (id) )";
		
		// Now go and create the tables
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.openConnection();
		
		if (logger.isDebugEnabled()) {
			logger.debug("* Executing sql = " + sqlRestableBM);
		}
		dbConn.executeSQL(sqlRestableBM);
		if (logger.isDebugEnabled()) {
			logger.debug("* Executing sql = " + sqlRestablePS);
		}
		dbConn.executeSQL(sqlRestablePS);
		if (logger.isDebugEnabled()) {
			logger.debug("* Executing sql = " + sqlRestableAC);
		}
		dbConn.executeSQL(sqlRestableAC);
		
		dbConn.closeConnection();
		dbConn = null;

	}
	
	private void saveResultsToDB(int mode, ResultCollection resColl) {
		// mode 0 = BM, 1 = normal, 2 = finalsort
		resColl.saveToDB(mode, runID);
	}
		

	
	/** Object output methods */
	
	public String showPreferences() {
		return preferenceSet.toString();
	}

	public String showQueries() {
		String out = new String();
		
		for (String query : queryCollection) {
			out += query + "\n";
		}
		
		return out;
	}
}
