package uk.ac.cardiff.comsc.kis.peeps.preference.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.mediator.Peeps;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.AttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.DiscreteAttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.NumericAttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.Attribute;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.AttributeCollection;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.DiscreteAttribute;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.NumericAttribute;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.PreferenceSet;
import uk.ac.cardiff.comsc.kis.peeps.preference.subset.PreferenceSubset;
import uk.ac.cardiff.comsc.kis.peeps.preference.subset.PreferenceSubsetCollection;

public class PreferenceConverter {

	/** Logger */
	static Logger logger = Logger.getLogger(PreferenceConverter.class);
	
	
	/** Static methods */
	
	public static PreferenceSubsetCollection PreferenceSetToPreferenceSubsetCollection(PreferenceSet preferenceSet, int method) {
		if (logger.isDebugEnabled()) {
			logger.debug("PreferenceSetToPreferenceSubset converter called, using method '" + method + "'");
		}
		PreferenceSubsetCollection preferenceSubsetCollection = new PreferenceSubsetCollection();
		AttributeCollection sortedAttrs;
		int numAttrs;
		int preferenceSubsetID;
		
		Attribute att;

		switch (method) {
		
			case (Peeps.SUBSALG_ALLINONE):
			
				if (logger.isDebugEnabled()) {
					logger.debug("Getting the sorted attribute list");
				}
				sortedAttrs = preferenceSet.getSortedAttributeCollection();
				numAttrs = sortedAttrs.size();
			
				// Add a new subset
				preferenceSubsetID = preferenceSubsetCollection.addNewSubset();
				
				// for every attribute
				for (Attribute attr : sortedAttrs) {
					if (attr.getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
						if (logger.isDebugEnabled()) {
							logger.debug("* Attribute is a Discrete Attribute \"" + attr.getName() + "\". Adding all items to the same DAI");
						}
						ArrayList<String> items = new ArrayList<String>();
						for (DiscreteAttributeItem daiPrefs : (DiscreteAttribute)attr) {
							if (logger.isDebugEnabled()) {
								logger.debug("* * Adding item \"" + daiPrefs.getValue() + "\"");
							}
							items.add(daiPrefs.getValue());
						}
					preferenceSubsetCollection.getSubset(preferenceSubsetID).addDiscreteItem(new DiscreteAttributeItem(attr.getName(),items));
					}
				}
				break;
			
			case (Peeps.SUBSALG_MINSIZESUBS):
			case (Peeps.SUBSALG_BENCHMARK):
				
				if (logger.isDebugEnabled()) {
					logger.debug("Getting the sorted attribute list");
				}
				sortedAttrs = preferenceSet.getSortedAttributeCollection();
				numAttrs = sortedAttrs.size();
				
				// Set up stuff to keep track of where we are
				int[] currentPosn = new int[numAttrs];
				int[] maxPosn = new int[numAttrs];
				for (int i = 0 ; i < numAttrs; i++) {
					currentPosn[i] = 0;
					maxPosn[i] = sortedAttrs.get(i).getNumberOfValues() - 1;
				}
				
				// Now, iterate!
				
				// While there are still items to do
				while (!Arrays.equals(currentPosn,maxPosn)) {
					if (logger.isDebugEnabled()) {
						logger.debug("There are still subsets to do. Adding next...");
					}

					// Add a new subset
					preferenceSubsetID = preferenceSubsetCollection.addNewSubset();
					
					// The current subset
					for (int currentAttr = 0 ; currentAttr < numAttrs ; currentAttr++) {
						att = sortedAttrs.get(currentAttr);
						if (logger.isDebugEnabled()) {
							logger.debug("* Looking at attribute " + currentAttr);
						}
						if (att.getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
							if (logger.isDebugEnabled()) {
								logger.debug("* * Attribute is a Discrete Attribute. Current position is '" + currentPosn[currentAttr] + "' out of a max of '" + maxPosn[currentAttr] + "'");
							}
							preferenceSubsetCollection.getSubset(preferenceSubsetID).addDiscreteItem((DiscreteAttributeItem)att.getItemAt(currentPosn[currentAttr]));
						} else if (att.getAttributeType() == Attribute.NUMERIC_ATTRIBUTE) {
							if (logger.isDebugEnabled()) {
								logger.debug("* * Attribute is a Numeric Attribute. Current position is '" + currentPosn[currentAttr] + "' out of a max of '" + maxPosn[currentAttr] + "'");
							}
							preferenceSubsetCollection.getSubset(preferenceSubsetID).addNumericItem((NumericAttributeItem)att.getItemAt(currentPosn[currentAttr]));
						}
					}
					
									
					// Increment the numbers
					currentPosn[numAttrs - 1]++;
					for (int i = numAttrs - 1 ; i > 0 ; i--) {
						if (currentPosn[i] > maxPosn[i]) {
							currentPosn[i] = 0;
							currentPosn[i - 1]++;
						}
					}
				}
				
				preferenceSubsetID = preferenceSubsetCollection.addNewSubset();
				
				for (int currentAttr = 0 ; currentAttr < numAttrs ; currentAttr++) {
					att = sortedAttrs.get(currentAttr);
					if (att.getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
						preferenceSubsetCollection.getSubset(preferenceSubsetID).addDiscreteItem((DiscreteAttributeItem)att.getItemAt(currentPosn[currentAttr]));
					} else if (att.getAttributeType() == Attribute.NUMERIC_ATTRIBUTE) {
						preferenceSubsetCollection.getSubset(preferenceSubsetID).addNumericItem((NumericAttributeItem)att.getItemAt(currentPosn[currentAttr]));
					}
				}
				break;

			case Peeps.SUBSALG_RELAXDOWN:
				
				if (logger.isDebugEnabled()) {
					logger.debug("Getting the sorted attribute list");
				}
				sortedAttrs = preferenceSet.getSortedAttributeCollection();
				numAttrs = sortedAttrs.size();
				
				// Set up stuff to keep track of where we are
				int currentPosnAttr = numAttrs - 1;
				int currentPosnVal = 0;
				
				// Now, iterate!
				
				// While there are still items to do
				while (currentPosnAttr != -1) {
					if (logger.isDebugEnabled()) {
						logger.debug("There are still subsets to do. Adding next...");
					}

					// Add a new subset
					preferenceSubsetID = preferenceSubsetCollection.addNewSubset();
					
					// For all attributes
					for (int currentAttr = 0 ; currentAttr < numAttrs ; currentAttr++) {
						att = sortedAttrs.get(currentAttr);
						if (logger.isDebugEnabled()) {
							logger.debug("* Looking at attribute " + currentAttr);
						}

						// If we're on an attribute that is currently only having the first item added
						if (currentAttr < currentPosnAttr) {
							if (logger.isDebugEnabled()) {
								logger.debug("* * Attribute is unrelaxed. Adding first value only.");
							}

							if (att.getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
								if (logger.isDebugEnabled()) {
									logger.debug("* * Attribute is a Discrete Attribute. Current position is '" + currentPosnAttr + "," + currentPosnVal  + "'");
								}
								preferenceSubsetCollection.getSubset(preferenceSubsetID).addDiscreteItem((DiscreteAttributeItem)att.getItemAt(0));
							} else if (att.getAttributeType() == Attribute.NUMERIC_ATTRIBUTE) {
								if (logger.isDebugEnabled()) {
									logger.debug("* * Attribute is a Numeric Attribute. Current position is '" + currentPosnAttr + "," + currentPosnVal  + "'");
								}
								preferenceSubsetCollection.getSubset(preferenceSubsetID).addNumericItem((NumericAttributeItem)att.getItemAt(0));
							}
							
							
						// Else, if we're on an attribute that we're relaxing
						} else if (currentAttr == currentPosnAttr) {
							if (logger.isDebugEnabled()) {
								logger.debug("* * Attribute is currently being relaxed. Adding first '" + currentPosnVal + " values.");
							}

							if (att.getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
								if (logger.isDebugEnabled()) {
									logger.debug("* * Attribute is a Discrete Attribute. Current position is '" + currentPosnAttr + "," + currentPosnVal  + "'");
								}
								ArrayList<String> items = new ArrayList<String>();
								int currentVal = 0;
								for (DiscreteAttributeItem daiPrefs : (DiscreteAttribute)att) {
									if (currentVal <= currentPosnVal) {
										if (logger.isDebugEnabled()) {
											logger.debug("* * * Adding item \"" + daiPrefs.getValue() + "\"");
										}
										items.add(daiPrefs.getValue());
									}
									currentVal++;
								}
								preferenceSubsetCollection.getSubset(preferenceSubsetID).addDiscreteItem(new DiscreteAttributeItem(att.getName(),items));
								
							}
							// TODO if att == NUMERIC
														
							
						// Otherwise, we're on an already fully relaxed attribute
						} else if (currentAttr > currentPosnAttr){
							if (logger.isDebugEnabled()) {
								logger.debug("* * Attribute is fully relaxed. Adding all values.");
							}
							
							if (att.getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
								if (logger.isDebugEnabled()) {
									logger.debug("* * Attribute is a Discrete Attribute. Current position is '" + currentPosnAttr + "," + currentPosnVal  + "'");
								}
								ArrayList<String> items = new ArrayList<String>();
								for (DiscreteAttributeItem daiPrefs : (DiscreteAttribute)att) {
									if (logger.isDebugEnabled()) {
										logger.debug("* * * Adding item \"" + daiPrefs.getValue() + "\"");
									}
									items.add(daiPrefs.getValue());
								}
								preferenceSubsetCollection.getSubset(preferenceSubsetID).addDiscreteItem(new DiscreteAttributeItem(att.getName(),items));
								
							}
							// TODO if att == NUMERIC
						}
						
						
					}
					
					
					
									
					// Increment the numbers
					currentPosnVal++;
					if (currentPosnVal > (((Attribute)sortedAttrs.get(currentPosnAttr)).getNumberOfValues() - 1)) {
						currentPosnAttr--;
						currentPosnVal = 0;
					}
				}
				
				break;
		}		
		return preferenceSubsetCollection;
	}
	
	public static String PreferenceSubsetToSQL(PreferenceSubset prefSubset, int dbID, int runID, int createQueryMode) {
		if (logger.isDebugEnabled()) {
			logger.debug("Converting Preference Subset to SQL query");
		}
		String sql = new String();
		
		
		// Build the query
		
		// Start with the common start
		
		switch(createQueryMode) {
		case 0: // Normal
			sql = "SELECT * FROM StockDB_" + dbID + " WHERE ";
			break;
		case 1: // Final sort
			sql = "SELECT * FROM Results_Run" + runID + "_Presort WHERE ";
		}
		

		// Then for each item add the where clause
		for (AttributeItem attrItem : prefSubset) {
			if (attrItem.getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
				if (logger.isDebugEnabled()) {
					logger.debug("Current Item is Discrete - " + attrItem.toString() + " - so just adding it");
				}
				sql += attrItem.toString();
			} else if (attrItem.getAttributeType() == Attribute.NUMERIC_ATTRIBUTE) {
				if (logger.isDebugEnabled()) {
					logger.debug("CurrentItem is Numeric - " + attrItem.toString() + " = calculating SQL and adding");
				}
				if (((NumericAttributeItem)attrItem).getNumericAttributeType() == NumericAttribute.GREATERTHAN) {
					sql += attrItem.getAttributeName();
					sql += " > ";
					sql += ((NumericAttributeItem)attrItem).getValue();
				} else if (((NumericAttributeItem)attrItem).getNumericAttributeType() == NumericAttribute.LESSTHAN) {
					sql += attrItem.getAttributeName();
					sql += " < ";
					sql += ((NumericAttributeItem)attrItem).getValue();
				} else if (((NumericAttributeItem)attrItem).getNumericAttributeType() == NumericAttribute.BETWEEN) {
					sql += "(";
					sql += attrItem.getAttributeName();
					sql += " > ";
					sql += ((NumericAttributeItem)attrItem).getLowerValue();
					sql += " AND ";
					sql += attrItem.getAttributeName();
					sql += " < ";
					sql += ((NumericAttributeItem)attrItem).getHigherValue();
					sql += ")";
				}
			}
			sql += " AND ";
		}
		// Remove the trailing " AND "
		sql = sql.substring(0, sql.length() - 5);
		sql += "ORDER BY PRICE DESC"; // Just temporary
		if (logger.isDebugEnabled()) {
			logger.debug("Generated SQL is: " + sql);
		}
		return sql;
	}
}
