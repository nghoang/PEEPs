package uk.ac.cardiff.comsc.kis.peeps.preference.subset;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;


public class PreferenceSubsetCollection implements Iterable<PreferenceSubset>{

	/** Logger */
	static Logger logger = Logger.getLogger(PreferenceSubsetCollection.class);

    /** ArrayList holding the preferencesubsets */
    private ArrayList<PreferenceSubset> preferenceSubsetCollection;
            
    /** Empty Constructor */
    public PreferenceSubsetCollection() {
        preferenceSubsetCollection = new ArrayList<PreferenceSubset>();
    }
    
    
    /** Main Methods */
    
    public int addNewSubset() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Adding new Subset to the PreferenceSubsetCollection");
    	}
        PreferenceSubset subset = new PreferenceSubset();
        preferenceSubsetCollection.add(subset);
        subset.setRank(preferenceSubsetCollection.indexOf(subset));
        return subset.getRank();
    }
    
    public PreferenceSubset getSubset(int rank) {
        return (PreferenceSubset)preferenceSubsetCollection.get(rank);
    }

    
    /** Object output methods */
    
    public Iterator<PreferenceSubset> iterator() {
        return preferenceSubsetCollection.iterator();
    }
    
    public String toString() {
        String out = new String();
        
        System.out.println("Preference Subset Collection:");
        System.out.println("--");
        
        for (PreferenceSubset prefSubset : this) {
            System.out.println(prefSubset.toString());
        }
        System.out.println("--");
        
        return out;
    }
}
