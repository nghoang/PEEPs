package uk.ac.cardiff.comsc.kis.peeps.mediator.evaluation;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.mediator.Peeps;

public class PeepsEvalVaryItem {

	/** Logger */
	static Logger logger = Logger.getLogger(PeepsEvalVaryItem.class);

	private int C_NUM_RUNS_PER_DB;
	private int C_NUM_REP_OF_RUNS;
	private int C_NUM_RESTOGET;
	private int C_DB_SIZE;
	private int C_DB_NUMATTR;
	private int C_DB_NUMVALPERATTR;
	private int C_DATAGEN_METHOD;
	private int C_DB_PRICEGEN_METHOD;
	private double C_DB_MINPRICE;
	private double C_DB_MAXPRICE;
	private int C_PS_NUMATTR;
	private int C_PS_NUMVALPERATTR;

	private PeepsRunCollection runColl;
	private String outMode;


	/** Constructors */

	public PeepsEvalVaryItem() {
		if (logger.isDebugEnabled()) {
			logger.debug("Reading in properties file");
		}

		Properties props = new Properties();
		try {
			//System.out.println(System.getProperty("user.dir") + "/PEEPS.properties");
			InputStream propsIn = new FileInputStream(System.getProperty("user.dir") + "/PEEPS.properties");
			props.load(propsIn);

			C_NUM_RUNS_PER_DB = Integer.parseInt(props.getProperty("C_NUM_RUNS_PER_DB"));
			C_NUM_REP_OF_RUNS = Integer.parseInt(props.getProperty("C_NUM_REP_OF_RUNS"));
			C_NUM_RESTOGET = Integer.parseInt(props.getProperty("C_NUM_RESTOGET"));
			C_DB_SIZE = Integer.parseInt(props.getProperty("C_DB_SIZE"));
			C_DB_NUMATTR = Integer.parseInt(props.getProperty("C_DB_NUMATTR"));
			C_DB_NUMVALPERATTR = Integer.parseInt(props.getProperty("C_DB_NUMVALPERATTR"));
			C_DATAGEN_METHOD = Integer.parseInt(props.getProperty("C_DATAGEN_METHOD"));
			C_DB_PRICEGEN_METHOD = Integer.parseInt(props.getProperty("C_DB_PRICEGEN_METHOD"));
			C_DB_MINPRICE = Double.parseDouble(props.getProperty("C_DB_MINPRICE"));
			C_DB_MAXPRICE = Double.parseDouble(props.getProperty("C_DB_MAXPRICE"));
			C_PS_NUMATTR = Integer.parseInt(props.getProperty("C_PS_NUMATTR"));
			C_PS_NUMVALPERATTR = Integer.parseInt(props.getProperty("C_PS_NUMVALPERATTR"));
			outMode = props.getProperty("outMode");

			if (logger.isDebugEnabled()) {
				logger.debug("* C_NUM_RUNS_PER_DB = " + C_NUM_RUNS_PER_DB);
				logger.debug("* C_NUM_REP_OF_RUNS = " + C_NUM_REP_OF_RUNS);
				logger.debug("* C_NUM_RESTOGET = " + C_NUM_RESTOGET);
				logger.debug("* C_DB_SIZE = " + C_DB_SIZE);
				logger.debug("* C_DB_NUMATTR = " + C_DB_NUMATTR);
				logger.debug("* C_DB_NUMVALPERATTR = " + C_DB_NUMVALPERATTR);
				logger.debug("* C_DATAGEN_METHOD = " + C_DATAGEN_METHOD);
				logger.debug("* C_DB_PRICEGEN_METHOD = " + C_DB_PRICEGEN_METHOD);
				logger.debug("* C_DB_MINPRICE = " + C_DB_MINPRICE);
				logger.debug("* C_DB_MAXPRICE = " + C_DB_MAXPRICE);
				logger.debug("* C_PS_NUMATTR = " + C_PS_NUMATTR);
				logger.debug("* C_PS_NUMVALPERATTR = " + C_PS_NUMVALPERATTR);
				logger.debug("* outMode = " + outMode);
			}

		} catch (Exception e) {
			logger.error("Could not read file - " + e.toString());

		}

		runColl = new PeepsRunCollection();
		
		if (outMode.equals("normal")) {
			System.out.println("\t\t|DB Setup\t\t\t|PS Setup\t\t|Main Setup\t|Results\t|Measures");
			System.out.println("Run/vR/R/GA/dbR\t|DBID\tGen_Alg\tNA/NVPA/Size\t|PSID\tGen_Alg\tNA/NVPA\t|GR_Alg\tNRToGet\t|NumQ\tNumRes\t|Tov/Tsu/Tbm/Talg\t\tTOv/TS/TR\tPrivL\tExpl_MP/TopNR");
		} else if (outMode.equals("CSV")) {
			System.out.println(",,,,DB Setup,,,,,PS Setup,,,Main Setup,,Results,,Measures");
			System.out.println("Run,VaryRunID,RepID,GA,dbRepID,DBID,Gen_Alg,NA,NVPA,Size,PSID,Gen_Alg,NA,NVPA,GR_Alg,NRToGet,NumQ,NumRes,Tov,Tsu,Tbm,Talg,TrafficOv,TrafficS,TrafficR,PrivL,Expl_MP,Expl_TopNR");
		} else {
			System.out.println("Not a valid out option");
		}


	}

	/** Main methods */

	private void doRunAllAlgs(PeepsRunStockDB stockDB, PeepsRunPreferenceSet prefset, int varyRunID, int repID) {
		for (int dbRepID = 1 ; dbRepID <= C_NUM_RUNS_PER_DB; dbRepID++) {
			runColl.addRun(new PeepsRun(
					varyRunID,
					repID,
					dbRepID,
					stockDB,
					prefset,
					Peeps.SUBSALG_MINSIZESUBS,
					C_NUM_RESTOGET,
					outMode
			));
		}
		for (int dbRepID = 1 ; dbRepID <= C_NUM_RUNS_PER_DB; dbRepID++) {
			runColl.addRun(new PeepsRun(
					varyRunID,
					repID,
					dbRepID,
					stockDB,
					prefset,
					Peeps.SUBSALG_ALLINONE,
					C_NUM_RESTOGET,
					outMode
			));
		}
		for (int dbRepID = 1 ; dbRepID <= C_NUM_RUNS_PER_DB; dbRepID++) {
			runColl.addRun(new PeepsRun(
					varyRunID,
					repID,
					dbRepID,
					stockDB,
					prefset,
					Peeps.SUBSALG_RELAXDOWN,
					C_NUM_RESTOGET,
					outMode
			));
		}
	}
	
	
	public void doSingleRun() {

		PeepsRunStockDB stockDB = new PeepsRunStockDB(C_DB_SIZE, C_DB_NUMATTR, C_DB_NUMVALPERATTR, C_DATAGEN_METHOD, C_DB_PRICEGEN_METHOD, C_DB_MINPRICE, C_DB_MAXPRICE);
		PeepsRunPreferenceSet prefset = new PeepsRunPreferenceSet(C_PS_NUMATTR, C_PS_NUMVALPERATTR, C_DATAGEN_METHOD);

		for (int runRep = 1 ; runRep <= C_NUM_REP_OF_RUNS; runRep++) {
			doRunAllAlgs(stockDB, prefset, 1, runRep);
		}
		stockDB = null;
		prefset = null;
	}
	
	
	public void doVaryNothing(int numRuns) {

		for (int run = 1; run <= numRuns; run++) {
			for (int runRep = 1 ; runRep <= C_NUM_REP_OF_RUNS; runRep++) {
				PeepsRunStockDB stockDB = new PeepsRunStockDB(C_DB_SIZE, C_DB_NUMATTR, C_DB_NUMVALPERATTR, C_DATAGEN_METHOD, C_DB_PRICEGEN_METHOD, C_DB_MINPRICE, C_DB_MAXPRICE);
				PeepsRunPreferenceSet prefset = new PeepsRunPreferenceSet(C_PS_NUMATTR, C_PS_NUMVALPERATTR, C_DATAGEN_METHOD);

				doRunAllAlgs(stockDB, prefset, run, runRep);

				stockDB = null;
				prefset = null;
			}
		}
	}


	public void doVaryDBSize(int numRuns, int min, int max) {
		int stepSize;

		if (numRuns > 1) {
			stepSize = (max - min) / (numRuns - 1);
		} else {
			stepSize = 0;
		}
		int currentSize = min;

		for (int run = 1; run <= numRuns; run++) {
			for (int runRep = 1 ; runRep <= C_NUM_REP_OF_RUNS; runRep++) {
				PeepsRunStockDB stockDB = new PeepsRunStockDB(currentSize, C_DB_NUMATTR, C_DB_NUMVALPERATTR, C_DATAGEN_METHOD, C_DB_PRICEGEN_METHOD, C_DB_MINPRICE, C_DB_MAXPRICE);
				PeepsRunPreferenceSet prefset = new PeepsRunPreferenceSet(C_PS_NUMATTR, C_PS_NUMVALPERATTR, C_DATAGEN_METHOD);
				
				doRunAllAlgs(stockDB, prefset, run, runRep);

				stockDB = null;
				prefset = null;
			}
			currentSize += stepSize;
		}
	}

	public PeepsRunCollection doVaryDBNumAttr(int numRuns, int min, int max) {
		PeepsRunCollection runColl = new PeepsRunCollection();
		int stepSize;

		if (numRuns > 1) {
			stepSize = (max - min) / (numRuns - 1);
		} else {
			stepSize = 0;
		}
		int currentSize = min;

			for (int run = 1; run <= numRuns; run++) {
				for (int runRep = 1 ; runRep <= C_NUM_REP_OF_RUNS; runRep++) {
				PeepsRunStockDB stockDB = new PeepsRunStockDB(C_DB_SIZE, currentSize, C_DB_NUMVALPERATTR, C_DATAGEN_METHOD, C_DB_PRICEGEN_METHOD, C_DB_MINPRICE, C_DB_MAXPRICE);
				PeepsRunPreferenceSet prefset = new PeepsRunPreferenceSet(C_PS_NUMATTR, C_PS_NUMVALPERATTR, C_DATAGEN_METHOD);

				doRunAllAlgs(stockDB, prefset, run, runRep);

				stockDB = null;
				prefset = null;
			}
			currentSize += stepSize;
		}

		return runColl;
	}


	public PeepsRunCollection doVaryDBNumValPerAttr(int numRuns, int min, int max) {
		PeepsRunCollection runColl = new PeepsRunCollection();
		int stepSize;

		if (numRuns > 1) {
			stepSize = (max - min) / (numRuns - 1);
		} else {
			stepSize = 0;
		}
		int currentSize = min;

			for (int run = 1; run <= numRuns; run++) {
				for (int runRep = 1 ; runRep <= C_NUM_REP_OF_RUNS; runRep++) {
				PeepsRunStockDB stockDB = new PeepsRunStockDB(C_DB_SIZE, C_DB_NUMATTR, currentSize, C_DATAGEN_METHOD, C_DB_PRICEGEN_METHOD, C_DB_MINPRICE, C_DB_MAXPRICE);
				PeepsRunPreferenceSet prefset = new PeepsRunPreferenceSet(C_PS_NUMATTR, C_PS_NUMVALPERATTR, C_DATAGEN_METHOD);

				doRunAllAlgs(stockDB, prefset, run, runRep);

				stockDB = null;
				prefset = null;
			}
			currentSize += stepSize;
		}		
		return runColl;
	}

	public PeepsRunCollection doVaryPSNumAttr(int numRuns, int min, int max) {
		PeepsRunCollection runColl = new PeepsRunCollection();
		int stepSize;
		
		// First, check whether we've specified more attr than exist in the db. if so, drop it to the max
		if (max > C_DB_NUMATTR) {
			max = C_DB_NUMATTR;
		}

		if (numRuns > 1) {
			stepSize = (max - min) / (numRuns - 1);
		} else {
			stepSize = 0;
		}
		int currentSize = min;

			for (int run = 1; run <= numRuns; run++) {
				for (int runRep = 1 ; runRep <= C_NUM_REP_OF_RUNS; runRep++) {
				PeepsRunStockDB stockDB = new PeepsRunStockDB(C_DB_SIZE, C_DB_NUMATTR, C_DB_NUMVALPERATTR, C_DATAGEN_METHOD, C_DB_PRICEGEN_METHOD, C_DB_MINPRICE, C_DB_MAXPRICE);
				PeepsRunPreferenceSet prefset = new PeepsRunPreferenceSet(currentSize, C_PS_NUMVALPERATTR, C_DATAGEN_METHOD);

				doRunAllAlgs(stockDB, prefset, run, runRep);

				stockDB = null;
				prefset = null;
			}
			currentSize += stepSize;
		}		
		return runColl;
	}

	public PeepsRunCollection doVaryPSNumValPerAttr(int numRuns, int min, int max) {
		PeepsRunCollection runColl = new PeepsRunCollection();
		int stepSize;

		if (numRuns > 1) {
			stepSize = (max - min) / (numRuns - 1);
		} else {
			stepSize = 0;
		}
		int currentSize = min;

			for (int run = 1; run <= numRuns; run++) {
				for (int runRep = 1 ; runRep <= C_NUM_REP_OF_RUNS; runRep++) {
				PeepsRunStockDB stockDB = new PeepsRunStockDB(C_DB_SIZE, C_DB_NUMATTR, C_DB_NUMVALPERATTR, C_DATAGEN_METHOD, C_DB_PRICEGEN_METHOD, C_DB_MINPRICE, C_DB_MAXPRICE);
				PeepsRunPreferenceSet prefset = new PeepsRunPreferenceSet(C_PS_NUMATTR, currentSize, C_DATAGEN_METHOD);

				doRunAllAlgs(stockDB, prefset, run, runRep);

				stockDB = null;
				prefset = null;
			}
			currentSize += stepSize;
		}
		return runColl;
	}
	
	public PeepsRunCollection doVaryEverything(int numRuns) {
		PeepsRunCollection runColl = new PeepsRunCollection();

		int dbSize, dbNumAttr, dbNumValPerAttr, psNumAttr, psNumValPerAttr;
		
		RandomData randomData = new RandomDataImpl();
		
		for (int run = 1; run <= numRuns; run++) {
			for (int runRep = 1 ; runRep <= C_NUM_REP_OF_RUNS; runRep++) {
				dbSize = (int)randomData.nextUniform(1, 250000);
				dbNumAttr = (int)randomData.nextUniform(5, 30);
				dbNumValPerAttr = (int)randomData.nextUniform(5, 30);
				int tmp = dbNumAttr - 1;
				if (tmp > 6) {
					tmp = 6;
				}
				psNumAttr = (int)randomData.nextUniform(1, tmp);
				tmp = dbNumValPerAttr - 1;
				if (tmp > 9) {
					tmp = 9;
				}
				psNumValPerAttr = (int)randomData.nextUniform(1, tmp);
				PeepsRunStockDB stockDB = new PeepsRunStockDB(dbSize, dbNumAttr, dbNumValPerAttr, C_DATAGEN_METHOD, C_DB_PRICEGEN_METHOD, C_DB_MINPRICE, C_DB_MAXPRICE);
				PeepsRunPreferenceSet prefset = new PeepsRunPreferenceSet(psNumAttr, psNumValPerAttr, C_DATAGEN_METHOD);

				doRunAllAlgs(stockDB, prefset, run, runRep);

				stockDB = null;
				prefset = null;
			}
		}
		return runColl;	
	}
}
