package uk.ac.cardiff.comsc.kis.peeps.vendor.agent;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.mediator.Peeps;
import uk.ac.cardiff.comsc.kis.peeps.user.agent.UserAgent;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.DatabaseConnection;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.results.Result;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.results.ResultCollection;

public class VendorAgent {
	
	/** Logger */
	static Logger logger = Logger.getLogger(VendorAgent.class);

	// Connection to the db
	DatabaseConnection dbConn; 

	// How we're going to exploit results 
	int exploitationProfile;


	/** Constructors */
	
	public VendorAgent() {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating new VendorAgent");
		}
		dbConn = new DatabaseConnection();
	}
	
	
	/** Getters and setters */

	public void setExploitationProfile(int method) {
		exploitationProfile = method;
	}

	
	/** public methods */

	public void openDBConn() {
		dbConn.openConnection();
	}
	
	public void closeDBConn() {
		dbConn.closeConnection();
	}
	
	public ResultCollection runQuery(String query) {
		if (logger.isDebugEnabled()) {
			logger.debug("Running query " + query + " against database");
		}

		//dbConn.openConnection();
		ResultCollection resColl = new ResultCollection();
		resColl.parseResultSet(dbConn.sendQuery(query));
		//dbConn.closeConnection();

		if (resColl.size() != 0 && exploitationProfile != Peeps.EXPL_NOEXPL) {
			resColl = exploit(resColl);
		}
		return resColl;
	}

	
	/** private methods */

	private ResultCollection exploit(ResultCollection resultsToExploit) {
		if (logger.isDebugEnabled()) {
			logger.debug("Exploiting results:");
		}
		ResultCollection exploitedResults = new ResultCollection();
		
		switch (exploitationProfile) {

		case Peeps.EXPL_BOT20PCPRICE:
			if (logger.isDebugEnabled()) {
				logger.debug("* Using method 'EXPL_BOT20PCPRICE':");
			}
			if (resultsToExploit.size() > 1) {
				if (logger.isDebugEnabled()) {
					logger.debug("* More than one result, finding out the amount of items to drop:");
				}
				
				int numToDrop = (int)Math.ceil((resultsToExploit.size() / 5));
				int [] idsOfResultsToDrop = new int[numToDrop];
				double [] pricesOfResultsToDrop = new double[numToDrop];
				
				// Figure out lowest priced items
				
				// Find out the highest price for comparison
				int highestID = resultsToExploit.get(0).getId();
				double highestPrice = resultsToExploit.get(0).getPrice();

				for (Result res : resultsToExploit) {
					if ((res.getPrice() > highestPrice)) {
						highestID = res.getId();
						highestPrice = res.getPrice();
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("* * Highest id/price = " + highestID + "/" + highestPrice);
				}				
				
				int currentNum = 0;
				
				while (currentNum < numToDrop) {
					if (logger.isDebugEnabled()) {
						logger.debug("* * Current Num = " + currentNum);
					}

					int lowestID = highestID;
					double lowestPrice = highestPrice;
					
					for (Result res : resultsToExploit) {
						if (logger.isDebugEnabled()) {
							logger.debug("* * * Looking at result " + res.getId() + "/" + res.getPrice());
						}
						boolean alreadyInList = false;
						for (int k = 0; k < currentNum; k++) {
							if (idsOfResultsToDrop[k] == res.getId()) {
								if (logger.isDebugEnabled()) {
									logger.debug("* * * This is already in the list!");
								}
								alreadyInList = true;
							}								
						}
						if ((res.getPrice() < lowestPrice) && (!alreadyInList)) {
							if (logger.isDebugEnabled()) {
								logger.debug("* * * New Lowest");
							}
							lowestID = res.getId();
							lowestPrice = res.getPrice();
						}
					}
					idsOfResultsToDrop[currentNum] = lowestID;
					pricesOfResultsToDrop[currentNum] = lowestPrice;
					currentNum++;
				}
				String out = new String();
				out = "ids/prices of results to drop are ";
				for (int k = 0; k < numToDrop ; k++) {
					out += idsOfResultsToDrop[k] + "-" + pricesOfResultsToDrop[k] + " | ";
				}
				if (logger.isDebugEnabled()) {
					logger.debug(out);
				}
				
				// Take lowest prices out of the result set
				for (Result res : resultsToExploit) {
					boolean include = true;
					for (int i = 0 ; i < numToDrop ; i++) {
						if (idsOfResultsToDrop[i] == res.getId()) {
							include = false;
						}
					}
					if (include) {
						if (logger.isDebugEnabled()) {
							logger.debug("* * Result '" + res.getId() + "' added");
						}
						exploitedResults.add(res);
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("* * Result '" + res.getId() + "' NOT added");
						}
					}
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("* Not more than one result, not exploiting");
				}
				exploitedResults = resultsToExploit;
			}


			break;
		}
		return exploitedResults;
	}
	
	
	/** object output methods */
}
