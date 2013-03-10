package uk.ac.cardiff.comsc.kis.peeps.mediator.evaluation;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.mediator.Peeps;
import uk.ac.cardiff.comsc.kis.peeps.user.agent.UserAgent;
import uk.ac.cardiff.comsc.kis.peeps.vendor.agent.VendorAgent;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.results.ResultCollection;

public class PeepsRun {

	/** Logger */
	static Logger logger = Logger.getLogger(PeepsRun.class);
	
	// The ID of this particular run
	static int numOfRuns = 0;
	int id;
	
	private int varyRunID;
	private int repID;
	private int dbRepID;
	
	// The UA and VA for this particular run
	UserAgent ua;
	VendorAgent va;

	// The stock database used for this run
	PeepsRunStockDB peepsRunStockDB;
	
	// The preference set used for this run
	PeepsRunPreferenceSet peepsRunPreferenceSet;
	
	// The subset creation algorithm used for this run
	private int subsetAlgorithm;
	
	// The number of results to get in this run
	private int numResultsToGet;
	
	// Measures for this run;
	private long measureTimeStart;
	private long measureTimeStartCreateDB;
	private long measureTimeStartCreatePS;
	private long measureTimeStartBM;
	private long measureTimeStartAlg;
	private long measureTimeStartFS;
	private long measureTimeEnd;
	
	private long measureTimeOverall;
	private long measureTimeSU;
	private long measureTimeBM;
	private long measureTimeAlg;
	
	private double measurePrivacyloss;
	private String measurePrivacylossOP;
	
	private int f_stockDBID;
	private int f_stockDBDataGenMethod;
	private int f_stockDBSize;
	private int f_stockDBNumAttr;
	private int f_stockDBNumValPerAttr;
	private int f_psID;
	private int f_psDataGenMethod;
	private int f_psNumAttr;
	private int f_psNumValPerAttr;
	private int f_numQSent;
	private int f_numRRec;
	private int f_measTraffOv;
	private int f_measTraffS;
	private int f_measTraffR;
	private int f_measExplMostPreferred;
	private int f_measExplTopNRem;
	
	
	/** Constructors */
	public PeepsRun(int _varyRunID, int _repID, int _dbRepID, PeepsRunStockDB _peepsRunStockDB, PeepsRunPreferenceSet _peepsRunPreferenceSet, int _subsetAlgorithm, int _numResultsToGet, String outMode) {

		measureTimeStart = System.currentTimeMillis();
		
		numOfRuns++;
		id = numOfRuns;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Created New PeepsRun object (" + id + ") StockDB '" + _peepsRunStockDB.getID() + "' | PrefSet '" + _peepsRunPreferenceSet.getID() + "' | Alg '" + Peeps.getName(_subsetAlgorithm) + "' | NumResToGet '" + _numResultsToGet + "' |");
		}
		
				
		// Set all the variables
		this.varyRunID = _varyRunID;
		this.repID = _repID;
		this.dbRepID = _dbRepID;
		this.peepsRunStockDB = _peepsRunStockDB;
		this.peepsRunPreferenceSet = _peepsRunPreferenceSet;
		this.subsetAlgorithm = _subsetAlgorithm;
		this.numResultsToGet = _numResultsToGet;		
		
		
		// Create the user agent and vendor agent
		ua = new UserAgent(id, peepsRunStockDB);
		va = new VendorAgent();

		// Next, do all the run stuff
		setupDB();
		setupPrefs();
		runBenchmark();
		runGradualRelease();

		measureTimeEnd = System.currentTimeMillis();
		calculateMeasures();
		
		setThingsToNull();
		
		if (outMode.equals("normal")) {
			System.out.println(toString());
		} else if (outMode.equals("CSV")) {
			System.out.println(toCSV());
		}
}
	
	/** Getters and Setters */
	
	
	/** public methods */

	
	/** private methods */
	
	private void setupDB() {
		if (logger.isInfoEnabled()) {
			logger.info("==== Setting up DB ====");
		}
		measureTimeStartCreateDB = System.currentTimeMillis();
		// TODO: Check if DB already set up, only do this if not
		peepsRunStockDB.runSetup();
		ua.setupDBSupportingTables();
	}
	
	private void setupPrefs() {
		if (logger.isInfoEnabled()) {
			logger.info("==== Setting up user preferences ====");
		}

		measureTimeStartCreatePS = System.currentTimeMillis();
		peepsRunPreferenceSet.runSetup();
		ua.setPrefs(peepsRunPreferenceSet.getPreferenceSet());
	}
	
	private void runBenchmark() {
		if (logger.isInfoEnabled()) {
			logger.info("==== Running Benchmarking process ====");
		}
		measureTimeStartBM = System.currentTimeMillis();
		va.setExploitationProfile(Peeps.EXPL_NOEXPL);
		ua.setNumberOfResultsToGet(-1);
		ua.runBenchmark(va);
		ua.saveBenchmarkResultsToDB();
	}
	
	private void runGradualRelease() {
		if (logger.isInfoEnabled()) {
			logger.info("==== Running Gradual Release process ====");
		}
		measureTimeStartAlg = System.currentTimeMillis();
		va.setExploitationProfile(Peeps.EXPL_BOT20PCPRICE);
		ua.setNumberOfResultsToGet(numResultsToGet);
		ua.runGradualRelease(va,subsetAlgorithm);

		measureTimeStartFS = System.currentTimeMillis();
		ua.saveResultsToDB();
		va.setExploitationProfile(Peeps.EXPL_NOEXPL);
		ua.sortResultsByPreference(va);
		ua.saveFinalResultsToDB();
	}
	
	private void calculateMeasures() {
		if (logger.isInfoEnabled()) {
			logger.info("==== Calculating Measures ====");
		}
		
		// Set up o/p formats
		DecimalFormat opform = new DecimalFormat("0.000");
		opform.setGroupingUsed(false);
		
		
		// Get the UA to calculate the measures it handles
		ua.calculateMeasures();
		
		// Now do our own direct bits
		measureTimeOverall = measureTimeEnd - measureTimeStart;
		measureTimeSU = measureTimeStartBM - measureTimeStart;
		measureTimeBM = measureTimeStartAlg - measureTimeStartBM;
		measureTimeAlg = measureTimeEnd - measureTimeStartAlg;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Privacy loss: Largest Subset = " + ua.getLargestSubsetSent() + " / Total Pref Set Size = " + ua.getTotalPreferenceSetSize());
		}
		measurePrivacyloss = (double) ua.getLargestSubsetSent() / ua.getTotalPreferenceSetSize();
		measurePrivacylossOP = opform.format(measurePrivacyloss);
	}
	
	private void setThingsToNull() {

		f_stockDBID =  peepsRunStockDB.getID();
		f_stockDBDataGenMethod = peepsRunStockDB.getDataGenMethod();
		f_stockDBNumAttr = peepsRunStockDB.getNumAttributes();
		f_stockDBNumValPerAttr = peepsRunStockDB.getNumValuesPerAttribute();
		f_stockDBSize = peepsRunStockDB.getSizeOfDB();
		f_psID = peepsRunPreferenceSet.getID();
		f_psDataGenMethod = peepsRunPreferenceSet.getDataGenMethod();
		f_psNumAttr = peepsRunPreferenceSet.getNumAttributes();
		f_psNumValPerAttr = peepsRunPreferenceSet.getNumValuesPerAttribute();
		f_numQSent = ua.getNumOfQueriesSent();
		f_numRRec = ua.getNumberOfResultsReturned();
		f_measTraffOv = ua.getMeasureTrafficOverall();
		f_measTraffS = ua.getMeasureTrafficSent();
		f_measTraffR = ua.getMeasureTrafficReceived();
		f_measExplMostPreferred = ua.getMeasureExplMostPreferred();
		f_measExplTopNRem = ua.getMeasureExplTopNRem();

		
		
		
		ua.setThingsToNull();
		ua = null;
		va = null;
		peepsRunStockDB = null;
		peepsRunPreferenceSet = null;
		System.gc();
	}
	
	
	
	/** Object output methods */
	
	public String toString() {
		return id + "/" + varyRunID + "/" + repID + "/" + subsetAlgorithm + "/" + dbRepID + "\t|" + f_stockDBID + "\t" + f_stockDBDataGenMethod + "\t" + f_stockDBNumAttr + "/" + f_stockDBNumValPerAttr + "/" + f_stockDBSize + "\t|" + f_psID + "\t" + f_psDataGenMethod + "\t" + f_psNumAttr + "/" + f_psNumValPerAttr + "\t|" + subsetAlgorithm + "\t" + numResultsToGet + "\t|" + f_numQSent + "\t" + f_numRRec + "\t|" + measureTimeOverall + "ms/" + measureTimeSU + "ms/" + measureTimeBM + "ms/" + measureTimeAlg + "ms\t" + f_measTraffOv + "b/" + f_measTraffS + "b/" + f_measTraffR + "b\t" + measurePrivacylossOP + "\t" + f_measExplMostPreferred + "/" + f_measExplTopNRem;
	}
	
	public String toCSV() {
		return id + "," + varyRunID + "," + repID + "," + subsetAlgorithm +  "," + dbRepID  + "," + f_stockDBID + "," + f_stockDBDataGenMethod + "," + f_stockDBNumAttr + "," + f_stockDBNumValPerAttr  + "," + f_stockDBSize + "," + f_psID + "," + f_psDataGenMethod + "," + f_psNumAttr + "," + f_psNumValPerAttr + "," + subsetAlgorithm + "," + numResultsToGet + "," + f_numQSent + "," + f_numRRec + "," + measureTimeOverall + "," + measureTimeSU + "," + measureTimeBM + "," + measureTimeAlg + "," + f_measTraffOv + "," + f_measTraffS + "," + f_measTraffR + "," + measurePrivacylossOP + "," + f_measExplMostPreferred + "," + f_measExplTopNRem;
	}
}
