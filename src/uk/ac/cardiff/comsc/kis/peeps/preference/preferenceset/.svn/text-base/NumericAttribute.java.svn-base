package uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset;

import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.preference.common.DiscreteAttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.NumericAttributeItem;

public class NumericAttribute extends Attribute implements Iterable<NumericAttributeItem> {

	/** Logger */
	static Logger logger = Logger.getLogger(NumericAttribute.class);

	/** Definitions for the type of NumericAttributes available */
	public static final int MINIMAL = 0;
	public static final int MAXIMAL = 1;
	public static final int AROUND = 2;
	public static final int BETWEEN = 3;
	public static final int LESSTHAN = 4;
	public static final int GREATERTHAN = 5;

	/** What type of numericAttribute this object is */
	private int numericAttributeType;

	/** The lower value of the attribute, if the type is BETWEEN */
	private double lowerValue;

	/** The lower value of the attribute, if the type is BETWEEN */
	private double higherValue;

	/** The value of the attribute, if the type is AROUND, LESSTHAN or GREATHERTHAN */
	private double value;

	
	/** Static method to get the Name of the NumericAttributeType */
	public static String getNameOfNumericAttributeType(int numericAttributeType) {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting name of NumericAttribute Type for type " + numericAttributeType);
		}
		String out = new String();

		switch (numericAttributeType) {
		case NumericAttribute.MINIMAL:
			out = "Minimise";
			break;
		case NumericAttribute.MAXIMAL:
			out = "Maximise";
			break;
		case NumericAttribute.AROUND:
			out = "Around";
			break;
		case NumericAttribute.BETWEEN:
			out = "Between";
			break;
		case NumericAttribute.LESSTHAN:
			out = "Less Than";
			break;
		case NumericAttribute.GREATERTHAN:
			out = "Greater Than";
			break;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Name is + '" + out + "'");
		}

		return out;
	}
	
	public static int getNumberOfValues(int numericAttributeType) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Getting number of values for this attribute type (" + NumericAttribute.getNameOfNumericAttributeType(numericAttributeType) + ")");
    	}
    	int out = 0;
		switch (numericAttributeType) {
		case NumericAttribute.MINIMAL:
			out = 1;
			break;
		case NumericAttribute.MAXIMAL:
			out = 1;
			break;
		case NumericAttribute.AROUND:
			out = 1;
			break;
		case NumericAttribute.BETWEEN:
			out = 1;
			break;
		case NumericAttribute.LESSTHAN:
			out = 2;
			break;
		case NumericAttribute.GREATERTHAN:
			out = 2;
			break;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Number of values is " + out);
		}
		return out;
    }

	
	
	/** Constructor */
	public NumericAttribute(String attributeName) {
		super(Attribute.NUMERIC_ATTRIBUTE, attributeName);
		if (logger.isDebugEnabled()) {
			logger.debug("Created new NumericAttribute Object");
		}
	}

	
	/** Getters and Setters */
	
	public String getNameOfNumericAttributeType() {
		return NumericAttribute.getNameOfNumericAttributeType(this.numericAttributeType);
	}

	public int getNumericAttributeType() {
		return this.numericAttributeType;
	}
	
	public void setNumericAttributeType(int numericAttributeType) {
		if (logger.isDebugEnabled()) {
			logger.debug("Attribute '" + super.getName() + "': setting NumericAttribute type to " + numericAttributeType + " (" + NumericAttribute.getNameOfNumericAttributeType(numericAttributeType) + ")");
		}
		this.numericAttributeType = numericAttributeType;
	}
	

	public double getLowerValue() {
		return lowerValue;
	}

	public void setLowerValue(double lowerValue) {
		if (logger.isDebugEnabled()) {
			logger.debug("Attribute '" + super.getName() + "': setting lower value to " + lowerValue);
		}
		this.lowerValue = lowerValue;
	}

	
	public double getHigherValue() {
		return higherValue;
	}

	public void setHigherValue(double higherValue) {
		if (logger.isDebugEnabled()) {
			logger.debug("Attribute '" + super.getName() + "': setting higher value to " + higherValue);
		}
		this.higherValue = higherValue;
	} 


	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		if (logger.isDebugEnabled()) {
			logger.debug("Attribute '" + super.getName() + "': setting value to " + value);
		}
		this.value = value;
	}
	
	public int getNumberOfValues() {
		return NumericAttribute.getNumberOfValues(this.numericAttributeType);
    }

    public NumericAttributeItem getItemAt(int indexOfValue) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Getting value at position " + indexOfValue);
    	}
    	switch (this.numericAttributeType) {
		case NumericAttribute.MINIMAL:
			switch (indexOfValue) {
			case 0:
				return new NumericAttributeItem(super.getName(), NumericAttribute.MINIMAL);
			}
			break;
		case NumericAttribute.MAXIMAL:
			switch (indexOfValue) {
			case 0:
				return new NumericAttributeItem(super.getName(), NumericAttribute.MAXIMAL);
			}
			break;
		case NumericAttribute.AROUND:
			switch (indexOfValue) {
			case 0:
				return new NumericAttributeItem(super.getName(), NumericAttribute.AROUND, this.value);
			}
			break;
		case NumericAttribute.BETWEEN:
			switch (indexOfValue) {
			case 0:
				return new NumericAttributeItem(super.getName(), NumericAttribute.BETWEEN, this.lowerValue, this.higherValue);
			}
			break;
		case NumericAttribute.LESSTHAN:
			switch (indexOfValue) {
			case 0:
				return new NumericAttributeItem(super.getName(), NumericAttribute.LESSTHAN, this.value);
			case 1:
				return new NumericAttributeItem(super.getName(), NumericAttribute.GREATERTHAN, this.value);
			}
			break;
		case NumericAttribute.GREATERTHAN:
			switch (indexOfValue) {
			case 0:
				return new NumericAttributeItem(super.getName(), NumericAttribute.GREATERTHAN, this.value);
			case 1:
				return new NumericAttributeItem(super.getName(), NumericAttribute.LESSTHAN, this.value);
			}
			break;
		}
    	return null;
    }

	/** Object output methods */

	public String toString() {
		String out = new String();

		out += super.getName() + " ( " + getNameOfNumericAttributeType();

		if (getNumericAttributeType() == NumericAttribute.AROUND || getNumericAttributeType() == NumericAttribute.LESSTHAN || getNumericAttributeType() == NumericAttribute.GREATERTHAN ) {
			out += "(" + value + ")";
		} else if (getNumericAttributeType() == NumericAttribute.BETWEEN) {
			out += "(" + lowerValue + "," + higherValue + ")";
		}

		out += " )";

		return out;
	}
	
	public Iterator<NumericAttributeItem> iterator() {
		return new NumericAttributeIterator();
	}
	
	private class NumericAttributeIterator<NumericAttributeItem> implements Iterator {
		private int position = 0;

		public boolean hasNext() {
			switch (numericAttributeType) {
			case NumericAttribute.MINIMAL:
				if (position < NumericAttribute.getNumberOfValues(NumericAttribute.MINIMAL)) {
					return true;
				} else {
					return false;
				}
			case NumericAttribute.MAXIMAL:
				if (position < NumericAttribute.getNumberOfValues(NumericAttribute.MAXIMAL)) {
					return true;
				} else {
					return false;
				}
			case NumericAttribute.AROUND:
				if (position < NumericAttribute.getNumberOfValues(NumericAttribute.AROUND)) {
					return true;
				} else {
					return false;
				}
			case NumericAttribute.BETWEEN:
				if (position < NumericAttribute.getNumberOfValues(NumericAttribute.BETWEEN)) {
					return true;
				} else {
					return false;
				}
			case NumericAttribute.LESSTHAN:
				if (position < NumericAttribute.getNumberOfValues(NumericAttribute.LESSTHAN)) {
					return true;
				} else {
					return false;
				}
			case NumericAttribute.GREATERTHAN:
				if (position < NumericAttribute.getNumberOfValues(NumericAttribute.GREATERTHAN)) {
					return true;
				} else {
					return false;
				}
			}
			return false;
        }
        
        public NumericAttributeItem next() {
        	position++;
    		return (NumericAttributeItem)getItemAt(position);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }   
	}
}
