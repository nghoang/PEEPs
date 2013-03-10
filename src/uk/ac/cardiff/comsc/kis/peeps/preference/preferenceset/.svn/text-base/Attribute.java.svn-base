package uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.preference.common.AttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.DiscreteAttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.NumericAttributeItem;

public class Attribute {
	
	/** Logger */
	static Logger logger = Logger.getLogger(Attribute.class);

	/** Definitions for the types of Attribute available */
    public static final int DISCRETE_ATTRIBUTE = 0;
    public static final int NUMERIC_ATTRIBUTE = 1;    

    /** The attribute's type */
    protected int attributeType;
    
    /** The attribute's name */
    protected String attributeName;
    
    
    /** Constructor */
    public Attribute(int attributeType, String attributeName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Created new Attribute Object");
    	}
    	this.attributeType = attributeType;
    	this.attributeName = attributeName;
    }
    
    
    /** Getters and Setters */
    public int getAttributeType() {
        return this.attributeType;
    }
    
    public String getName() {
        return this.attributeName;
    }
    
    public int getNumberOfValues() {
    	int out = -1;
    	if (this.attributeType == Attribute.DISCRETE_ATTRIBUTE) {
    		out = ((DiscreteAttribute)this).getNumberOfValues();
    	} else if (this.attributeType == Attribute.NUMERIC_ATTRIBUTE) {
    		out = ((NumericAttribute)this).getNumberOfValues();
    	}
    	return out;
    }
    
    public AttributeItem getItemAt(int indexOfItem) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Getting item at position '" + indexOfItem + "'");
    	}
    	if (this.attributeType == Attribute.DISCRETE_ATTRIBUTE) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Item is discrete, calling discrete getItemAt method");
        	}
    		return ((DiscreteAttribute)this).getItemAt(indexOfItem);
    	} else if (this.attributeType == Attribute.NUMERIC_ATTRIBUTE) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Item is numeric, calling numeric getItemAt method");
        	}
    		return ((NumericAttribute)this).getItemAt(indexOfItem);   
    	}
    	return null;
    }
    
    
    /** Object output methods */
    
    public String toString() {
        String out = new String();
        
        if (attributeType == DISCRETE_ATTRIBUTE) {
            out = ((DiscreteAttribute)this).toString();
        } else if (attributeType == NUMERIC_ATTRIBUTE) {
            out = ((NumericAttribute)this).toString();
        }
        return out;
    }
}
