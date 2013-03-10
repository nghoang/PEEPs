package uk.ac.cardiff.comsc.kis.peeps.preference.common;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.Attribute;

public class AttributeItem {

	/** Logger */
	static Logger logger = Logger.getLogger(AttributeItem.class);

	/** Definitions for the types of Attribute available */
	public static final int DISCRETE_ATTRIBUTE = 0;
    public static final int NUMERIC_ATTRIBUTE = 1;
    
    /** The name of the attribute this item is referring to */
    protected String attributeName;
    
    /** The type of the attribute this item is referring to */
    protected int attributeType;
            
            
    /** Constructor */
    public AttributeItem(int attributeType, String attributeName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Created new AttributeItem Object");
    	}
    	this.attributeType = attributeType;
        this.attributeName = new String(attributeName);
    }
    
    
    /** Getters and Setters */

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Setting name of AttributeItem to '" + attributeName + "'");
    	}
        this.attributeName = attributeName;
    }
    
    public int getAttributeType() {
        return attributeType;
    }
    
    public int getNumberOfValues() {
    	if (attributeType == Attribute.DISCRETE_ATTRIBUTE) {
            return ((DiscreteAttributeItem)this).getNumberOfValues();
        } else if (attributeType == Attribute.NUMERIC_ATTRIBUTE) {
            return ((NumericAttributeItem)this).getNumberOfValues();
        } else {
        	return 0;
        }
    }


    /** Object output methods */
    
    public String toString() {
        String out = new String();
        
        if (attributeType == Attribute.DISCRETE_ATTRIBUTE) {
            out = ((DiscreteAttributeItem)this).toString();
        } else if (attributeType == Attribute.NUMERIC_ATTRIBUTE) {
            out = ((NumericAttributeItem)this).toString();
        }
        
        return out;
    }

}
