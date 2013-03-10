package uk.ac.cardiff.comsc.kis.peeps.preference.common;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.Attribute;

public class DiscreteAttributeItem extends AttributeItem implements Iterable<String> {
	
	/** Logger */
	static Logger logger = Logger.getLogger(DiscreteAttributeItem.class);


	/** Holds the value of this subet item */
    private ArrayList<String> values;
    
    
    /** Constructor */
    public DiscreteAttributeItem(String attributeName) {
        super(Attribute.DISCRETE_ATTRIBUTE, attributeName);
        if (logger.isDebugEnabled()) {
        	logger.debug("Created new DiscreteAttributeItem object");
        }
        this.values = new ArrayList<String>();
    }
    
    /** Overloaded Constructor */
    public DiscreteAttributeItem(String attributeName, String value) {
        super(Attribute.DISCRETE_ATTRIBUTE, attributeName);
        if (logger.isDebugEnabled()) {
        	logger.debug("Created new DiscreteAttributeItem object");
        }
        this.values = new ArrayList<String>();
        setValue(value);
    }

    /** Overloaded Constructor */
    public DiscreteAttributeItem(String attributeName, ArrayList<String> values) {
        super(Attribute.DISCRETE_ATTRIBUTE, attributeName);
        if (logger.isDebugEnabled()) {
        	logger.debug("Created new DiscreteAttributeMultiItem object");
        }
        this.values = new ArrayList<String>();
        setValues(values);
    }
    
    public String getValue() {
    	return values.get(0);
    }
    
    public ArrayList<String> getValues() {
        return values;
    }
    
    public int getNumberOfValues() {
    	return values.size();
    }

    public void setValue(String value) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("DiscreteAttributeItem '" + super.getAttributeName() + "': Adding value '" + value + "'");
    	}
    	values.add(value);
    }
    
    public void setValues(ArrayList<String> values) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("DiscreteAttributeItem '" + super.getAttributeName() + "': Adding (" + values.size() + ") values '" + values.toString() + "'");
    	}
        this.values = values;
    }
    
    
    /** Object output methods */
    
    public Iterator<String> iterator() {
    	return values.iterator();
    }
    
    public String toString() {
        String out = new String();
        
        out = "(";
        for (int i = 0 ; i < values.size() - 1 ; i++) {
        	out += "(" + super.getAttributeName() + " = \"" + values.get(i) + "\")";
        	out += " OR ";
        }
        if (values.size() > 0) {
        	out += "(" + super.getAttributeName() + " = \"" + values.get(values.size() - 1) + "\")";
        } else {
        	out += "(" + super.getAttributeName() + " = \"EMPTY???\")";
        }
        out += ")";
        
        return out;

    }
}
