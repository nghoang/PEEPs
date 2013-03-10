package uk.ac.cardiff.comsc.kis.peeps.mediator.evaluation;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class PeepsRunCollection {

	// Logger
	static Logger logger = Logger.getLogger(PeepsRunCollection.class);

	// ArrayList to hold the colletion of Vertices
	private ArrayList<PeepsRun> peepsRunCollection;
	

	/** Constructors */
	public PeepsRunCollection() {
		if (logger.isDebugEnabled()) {
			logger.debug("New PeepsRunCollection object created");
		}
		peepsRunCollection = new ArrayList<PeepsRun>();
	}
	
	/** Main methods */
	
	public void addRun(PeepsRun peepsRun) {
		peepsRunCollection.add(peepsRun);
	}
	
	
	/** Object output methods */
	
	public String toString() {
		String out = new String();
		for (PeepsRun peepsRun : peepsRunCollection) {
			out += peepsRun.toString(); 
		}
		return out;
	}
	
	public String toCSV() {
		String out = new String();
		for (PeepsRun peepsRun : peepsRunCollection) {
			out += peepsRun.toCSV(); 
		}
		return out;
	}
}
