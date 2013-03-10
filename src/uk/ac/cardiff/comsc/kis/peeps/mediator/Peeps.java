package uk.ac.cardiff.comsc.kis.peeps.mediator;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.mediator.evaluation.PeepsEvalVaryItem;
import uk.ac.cardiff.comsc.kis.peeps.vendor.database.DatabaseConnection;

public class Peeps {
	
	/** Logger */
	static Logger logger = Logger.getLogger(Peeps.class);

	// Types of run available 
    public static final int DATAGEN_CAR_SIMPLE_EXAMPLE = 1;
    public static final int DATAGEN_RANDOM_UNIFORM = 2;
    
    public static final int PRICEGEN_CAR_SIMPLE_EXAMPLE = 101;
    public static final int PRICEGEN_RANDOM_UNIFORM = 102;
    
    public static final int SUBSALG_BENCHMARK = 200;
    public static final int SUBSALG_MINSIZESUBS = 201;
    public static final int SUBSALG_ALLINONE = 202;
    public static final int SUBSALG_RELAXDOWN = 203;
    public static final int SUBSALG_RANDOM = 204;
    
    public static final int EXPL_NOEXPL = 300;
    public static final int EXPL_BOT20PCPRICE = 301;
    
    // Max runtime per run
    public static final long MAX_RUNTIME = 300000; // 1,000 = 1 second. 60,000 = 1 minute. 300,000 = 5 minutes.  
    
    
	/** Getters and setters*/
	
	public static String getName(int name) {
		String out;
		
		switch (name) {
		case DATAGEN_CAR_SIMPLE_EXAMPLE:
			out = new String("DATAGEN_CAR_SIMPLE_EXAMPLE");
			break;
		case DATAGEN_RANDOM_UNIFORM:
			out = new String("DATAGEN_RANDOM_UNIFORM");
			break;
		case PRICEGEN_CAR_SIMPLE_EXAMPLE:
			out = new String("PRICEGEN_CAR_SIMPLE_EXAMPLE");
			break;
		case PRICEGEN_RANDOM_UNIFORM:
			out = new String("PRICEGEN_RANDOM_UNIFORM");
			break;
		case SUBSALG_BENCHMARK:
			out = new String("SUBALG_BENCHMARK");
			break;
		case SUBSALG_MINSIZESUBS:
			out = new String("SUBSALG_MINSIZESUBS");
			break;
		case SUBSALG_RELAXDOWN:
			out = new String("SUBSALG_RELAXDOWN");
			break;
		case SUBSALG_ALLINONE:
			out = new String("SUBSALG_ALLINONE");
			break;
		case EXPL_NOEXPL:
			out = new String("EXPL_NOEXPL");
			break;
		case EXPL_BOT20PCPRICE:
			out = new String("EXPL_BOT20PCPRICE");
			break;
		default: 
			out = new String("Unknown");
			break;
		}
		
		return out;
	}
	
	/** Public methods */
	
	public void run(String mode, int numRuns, int min, int max) {
		
		// First, reset the DB
		DatabaseConnection dbConn = new DatabaseConnection();
		dbConn.resetDB();
		dbConn = null;
		
		PeepsEvalVaryItem eval = new PeepsEvalVaryItem();

		if (mode.equals("SingleRun")) {
			eval.doSingleRun();
		} else if (mode.equals("VaryNothing")) {
			eval.doVaryNothing(numRuns);
		} else if (mode.equals("VaryDBSize")) {
			eval.doVaryDBSize(numRuns, min, max);
		} else if (mode.equals("VaryDBNumAttr")) {
			eval.doVaryDBNumAttr(numRuns, min, max);
		} else if (mode.equals("VaryDBNumValPerAttr")) {
			eval.doVaryDBNumValPerAttr(numRuns, min, max);
		} else if (mode.equals("VaryPSNumAttr")) {
			eval.doVaryPSNumAttr(numRuns, min, max);
		} else if (mode.equals("VaryPSNumValPerAttr")) {
			eval.doVaryPSNumValPerAttr(numRuns, min, max);
		} else if (mode.equals("VaryEverything")) {
			eval.doVaryEverything(numRuns);
		} else {
			System.out.println("Not a valid option!");
		}
		
	}

	
	/** the main method that kicks it all off, baby */
	
	public static void main(String[] args) {
		
		Peeps peeps = new Peeps();
		peeps.run(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
	}
}