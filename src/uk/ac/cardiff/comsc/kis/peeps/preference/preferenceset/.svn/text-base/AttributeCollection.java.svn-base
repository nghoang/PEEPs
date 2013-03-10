package uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class AttributeCollection implements Iterable<Attribute> {
	
	/** Logger */
	static Logger logger = Logger.getLogger(AttributeCollection.class);

	/** ArrayList to hold the colletion of Vertices */
	private ArrayList<Attribute> attributeCollection;
	

	/** Empty Constructor */
	public AttributeCollection() {
		if (logger.isDebugEnabled()) {
			logger.debug("New AttributeCollection object created");
		}
		attributeCollection = new ArrayList<Attribute>();
	}
	
	
	/** Main methods */

	public void add(Attribute attr) {
		if (logger.isDebugEnabled()) {
			logger.debug("Adding attribute '" + attr.getName() + "' to AttributeCollection");
		}
		attributeCollection.add(attr);
	}
	
	public void remove(Attribute attr) {
		if (logger.isDebugEnabled()) {
			logger.debug("Removing attribute '" + attr.getName() + "' from AttributeCollection");
		}
		attributeCollection.remove(attr);
	}

	public Attribute get(int index) {
		return attributeCollection.get(index);
	}
	
	public int indexOf(Attribute attr) {
		return attributeCollection.indexOf(attr);
	}

	
	public int size() {
		return attributeCollection.size();
	}
	
	public boolean isEmpty() {
		return attributeCollection.isEmpty();
	}

	
	/** Object output methods */
	
	public Iterator<Attribute> iterator() {
		return attributeCollection.iterator();
	}
}
