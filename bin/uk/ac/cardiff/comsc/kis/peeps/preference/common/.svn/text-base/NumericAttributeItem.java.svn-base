package uk.ac.cardiff.comsc.kis.peeps.preference.common;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.Attribute;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.NumericAttribute;

public class NumericAttributeItem extends AttributeItem {

	/** Logger */
	static Logger logger = Logger.getLogger(NumericAttributeItem.class);

    /** The type of numeric attribute this item is holding */
    private int numericAttributeType;
    
	/** The lower value of the attribute, if the type is BETWEEN */
	private double lowerValue;

	/** The lower value of the attribute, if the type is BETWEEN */
	private double higherValue;

	/** The value of the attribute, if the type is AROUND, LESSTHAN or GREATHERTHAN */
	private double value;

    
	/** Basic constructor */
    public NumericAttributeItem(String attributeName, int numericAttributeType) {
        super(Attribute.NUMERIC_ATTRIBUTE, attributeName);
        if (logger.isDebugEnabled()) {
        	logger.debug("Created new PreferenceSubsetNumericItem object");
        }
        this.numericAttributeType = numericAttributeType;
    }
    
	/** Overloaded constructor */
    public NumericAttributeItem(String attributeName, int numericAttributeType, double value) {
        super(Attribute.NUMERIC_ATTRIBUTE, attributeName);
        if (logger.isDebugEnabled()) {
        	logger.debug("Created new PreferenceSubsetNumericItem object");
        }
        this.numericAttributeType = numericAttributeType;
        this.value = value;
    }
    
	/** Overloaded constructor */
    public NumericAttributeItem(String attributeName, int numericAttributeType, double lowerValue, double higherValue) {
        super(Attribute.NUMERIC_ATTRIBUTE, attributeName);
        if (logger.isDebugEnabled()) {
        	logger.debug("Created new PreferenceSubsetNumericItem object");
        }
        this.numericAttributeType = numericAttributeType;
        this.lowerValue = lowerValue;
        this.higherValue = higherValue;
    }
    
    
    
    /** Getters and setters */
    
	
	public String getNameOfNumericAttributeType() {
		return NumericAttribute.getNameOfNumericAttributeType(this.numericAttributeType);
	}
	
    public int getNumericAttributeType() {
        return this.numericAttributeType;
    }
    
    public int getNumberOfValues() {
    	return 1; // For measuring the size of an attribute item
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("PreferenceSubsetNumericItem '" + super.getAttributeName() + "': Adding value '" + value + "'");
    	}
        this.value = value;
    }

    public double getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(double lowerValue) {
		logger.debug("PreferenceSubsetDiscrete Item '" + super.getAttributeName() + "': Adding lower value '" + lowerValue + "'");
        this.lowerValue = lowerValue;
    }

    public double getHigherValue() {
        return higherValue;
    }

    public void setHigherValue(double higherValue) {
		logger.debug("PreferenceSubsetDiscrete Item '" + super.getAttributeName() + "': Adding higher value '" + higherValue + "'");
        this.higherValue = higherValue;
    }
    
    
    /** Object output methods */
    
    public String toString() {
        String out = new String();
               
		out += super.getAttributeName() + " = " + getNameOfNumericAttributeType();

		if (getNumericAttributeType() == NumericAttribute.AROUND || getNumericAttributeType() == NumericAttribute.LESSTHAN || getNumericAttributeType() == NumericAttribute.GREATERTHAN ) {
			out += "(" + value + ")";
		} else if (getNumericAttributeType() == NumericAttribute.BETWEEN) {
			out += "(" + lowerValue + "," + higherValue + ")";
		}

		return out;
    }
}
