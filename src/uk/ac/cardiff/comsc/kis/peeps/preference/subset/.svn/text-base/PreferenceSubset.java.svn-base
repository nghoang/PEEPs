package uk.ac.cardiff.comsc.kis.peeps.preference.subset;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.kis.peeps.preference.common.AttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.DiscreteAttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.NumericAttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.NumericAttribute;

public class PreferenceSubset implements Iterable<AttributeItem>{

	/** Logger */
	static Logger logger = Logger.getLogger(PreferenceSubset.class);

	/** ArrayList to hold the preference subset items */
	private ArrayList<AttributeItem> preferenceSubsetItems;
	
	/** The rank of the subset */
    private int rank;
            
    
    /** Creates a new instance of PreferenceSubset */
    public PreferenceSubset() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Crated new PreferenceSubset object");
    	}
        preferenceSubsetItems = new ArrayList<AttributeItem>();
    }

    
    /** Getters and Setters */
    
    public void setRank(int rank) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Setting rank of subset to " + rank);
    	}
        this.rank = rank;
    }
    
    public int getRank() {
        return this.rank;
    }
    
    public int getSize() {
    	int size = 0;
    	
    	for (AttributeItem attrItem : preferenceSubsetItems) {
    		size += attrItem.getNumberOfValues();
    	}
    	
    	return size;
    }

    
    /** Main methods */
    
    public void addDiscreteItem(DiscreteAttributeItem dai) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Adding new DiscreteItem - '" + dai.getAttributeName() + "' = '" + dai.getValue() + "'");
    	}
        preferenceSubsetItems.add(dai);
    }
    
    public void addNumericItem(NumericAttributeItem nai) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Adding new PreferenceSubsetNumericItem - '" + nai.getAttributeName() + "' type '" + nai.getNameOfNumericAttributeType() + "'");
    	}
    	preferenceSubsetItems.add(nai);
    }
    

    /** Object output methods */
  
    public Iterator<AttributeItem> iterator() {
        return (Iterator<AttributeItem>)preferenceSubsetItems.iterator();
    }
    
    public String toString() {
        String out = new String();
        
        System.out.print("Subset (rank " + rank + ") ");
        for (AttributeItem attrItem : this) {
            System.out.print(" / " + attrItem.toString());
        }
        
        return out;
    }
}
