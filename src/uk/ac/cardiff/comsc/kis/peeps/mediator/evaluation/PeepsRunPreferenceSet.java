package uk.ac.cardiff.comsc.kis.peeps.mediator.evaluation;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.mediator.Peeps;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.Attribute;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.DiscreteAttribute;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.NumericAttribute;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.PreferenceSet;

public class PeepsRunPreferenceSet {
	
	/** Logger */
	static Logger logger = Logger.getLogger(PeepsRunPreferenceSet.class);

	private static int numOfPreferenceSets = 0;
	
	// The ID of the preference set
	private int id;
	
	// How many attributes
	private int numAttributes;
	
	// How many values per attribute
	private int numValuesPerAttribute;
	
	// Data generation method
	private int dataGenMethod;
	
	PreferenceSet preferenceSet;
		
	
	/** Constructors */
	public PeepsRunPreferenceSet(int _numAttributes, int _numValuesPerAttribute, int _dataGenMethod) {
		if (logger.isDebugEnabled()) {
			logger.debug("Created New PeepsRunPreferenceSet object - id '" + (numOfPreferenceSets + 1) + "' | NumAttr '" + _numAttributes + "' | NumValPerAttr '" + _numValuesPerAttribute + "' | DataGenMeth '" + Peeps.getName(_dataGenMethod) + "' |");
		}
		// Increment number of instances
		numOfPreferenceSets++;
		id = numOfPreferenceSets;
		
		numAttributes = _numAttributes;
		numValuesPerAttribute = _numValuesPerAttribute;
		dataGenMethod = _dataGenMethod;
	}
	
	
	/** Getters and Setters */
	
	public int getID() {
		return this.id;
	}
	
	public PreferenceSet getPreferenceSet() {
		return preferenceSet;
	}

	public int getDataGenMethod() {
		return this.dataGenMethod;
	}
	
	public int getNumAttributes() {
		return this.numAttributes;
	}
	
	public int getNumValuesPerAttribute() {
		return this.numValuesPerAttribute;
	}
	
	/** Object methods */	

	public void runSetup() {
		
		// Do the stuff
		createPreferenceSet();
		populatePreferenceSet();
		
	}
	
	private void createPreferenceSet() {
		if (logger.isInfoEnabled()) {
			logger.info("Creating preference set with id '" + id + "|" + numAttributes + "/" + numValuesPerAttribute);
		}
		preferenceSet = new PreferenceSet();
		if (logger.isInfoEnabled()) {
			logger.info("* Finished creating preference set");
		}

	}

	//Algorithm 5.1
	private void populatePreferenceSet() {
		if (logger.isInfoEnabled()) {
			logger.info("Populating preference set with id '" + id + "', using method '" + Peeps.getName(dataGenMethod) +"'");
		}
		// Set the preferences up depending on how we want them
		switch (dataGenMethod) {
		case Peeps.DATAGEN_CAR_SIMPLE_EXAMPLE:
			preferenceSet.addAttribute(Attribute.DISCRETE_ATTRIBUTE, "Make");
			((DiscreteAttribute)preferenceSet.getAttribute("Make")).addValueToAttribute("Mercedes");
			((DiscreteAttribute)preferenceSet.getAttribute("Make")).addValueToAttribute("BMW");
			((DiscreteAttribute)preferenceSet.getAttribute("Make")).addValueToAttribute("Ford");
			((DiscreteAttribute)preferenceSet.getAttribute("Make")).setPreferredOrdering("Mercedes", "BMW");
			((DiscreteAttribute)preferenceSet.getAttribute("Make")).setEquallyPreferred("BMW", "Ford");
			preferenceSet.addAttribute(Attribute.DISCRETE_ATTRIBUTE, "Colour");
			((DiscreteAttribute)preferenceSet.getAttribute("Colour")).addValueToAttribute("Silver");
			((DiscreteAttribute)preferenceSet.getAttribute("Colour")).addValueToAttribute("Black");
			((DiscreteAttribute)preferenceSet.getAttribute("Colour")).addValueToAttribute("Red");
			((DiscreteAttribute)preferenceSet.getAttribute("Colour")).addValueToAttribute("Blue");
			((DiscreteAttribute)preferenceSet.getAttribute("Colour")).setEquallyPreferred("Silver", "Black");
			((DiscreteAttribute)preferenceSet.getAttribute("Colour")).setPreferredOrdering("Black", "Red");
			((DiscreteAttribute)preferenceSet.getAttribute("Colour")).setEquallyPreferred("Red", "Blue");
			preferenceSet.addAttribute(Attribute.NUMERIC_ATTRIBUTE, "Price");
			((NumericAttribute)preferenceSet.getAttribute("Price")).setNumericAttributeType(NumericAttribute.LESSTHAN);
			((NumericAttribute)preferenceSet.getAttribute("Price")).setValue(3000);
			preferenceSet.setPreferredOrdering("Make","Colour");
			preferenceSet.setEquallyPreferred("Colour","Price");
			break;
		case Peeps.DATAGEN_RANDOM_UNIFORM:
			// For each attribute
			for (int attrNum = 1; (attrNum <= numAttributes); attrNum++) {
				preferenceSet.addAttribute(Attribute.DISCRETE_ATTRIBUTE, "Attr_" + attrNum);

				// Set the values
				for (int valueNum = 1; (valueNum <= numValuesPerAttribute); valueNum++) {
					// Set value
					// For now, just setting the value to the num. So prefs will be 1>2>3>4 etc
					((DiscreteAttribute)preferenceSet.getAttribute("Attr_" + attrNum)).addValueToAttribute("" + valueNum);
					// Set relative preferences
					// For now, alternate between > and =, so prefs will be 1=2>3=4 etc
					if (valueNum != 1) {
						if ((valueNum % 2) == 0) {
							((DiscreteAttribute)preferenceSet.getAttribute("Attr_" + attrNum)).setEquallyPreferred("" + (valueNum -1), "" + valueNum);
						} else if (((valueNum - 1) % 2) == 0) {
							((DiscreteAttribute)preferenceSet.getAttribute("Attr_" + attrNum)).setPreferredOrdering("" + (valueNum -1), "" + valueNum);
						}
					}
				}
				
				// Now do relative prefs between attrs
				if (attrNum !=1) {
					if ((attrNum % 2) == 0) {
						preferenceSet.setPreferredOrdering("Attr_" + (attrNum-1), "Attr_" + attrNum);
					} else if (((attrNum - 1) % 2) == 0) {
						preferenceSet.setEquallyPreferred("Attr_" + (attrNum-1), "Attr_" + attrNum);
					}
				}
			}
			break;
		}
		if (logger.isInfoEnabled()) {
			logger.info("* Finished populating preference set with " + preferenceSet.getNumberOfAttributes() + " attributes.");
		}
		if (logger.isDebugEnabled()) {
			logger.debug(preferenceSet.toString());
		}
		if (logger.isInfoEnabled()) {
			logger.info("* Making Preference Set fully directed.");
		}
		preferenceSet.makeGraphFullyDirected("in-input-order");
	}
	
	/** Object output methods */
	
	public String toString() {
		return new String("PeepsRunStockDBToString");
	}
}
