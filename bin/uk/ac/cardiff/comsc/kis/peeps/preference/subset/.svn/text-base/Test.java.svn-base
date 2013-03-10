package uk.ac.cardiff.comsc.kis.peeps.preference.subset;

import uk.ac.cardiff.comsc.kis.peeps.preference.common.DiscreteAttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.NumericAttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset.NumericAttribute;

public class Test {
    
    /** Creates a new instance of Test */
    public Test() {
        PreferenceSubsetCollection subsetCollection = new PreferenceSubsetCollection();
        
        int currentSubset = subsetCollection.addNewSubset();
        
        ((PreferenceSubset)subsetCollection.getSubset(currentSubset)).addDiscreteItem(new DiscreteAttributeItem("Make","BMW"));
        ((PreferenceSubset)subsetCollection.getSubset(currentSubset)).addDiscreteItem(new DiscreteAttributeItem("Colour","Black"));
        ((PreferenceSubset)subsetCollection.getSubset(currentSubset)).addNumericItem( new NumericAttributeItem("Mileage", NumericAttribute.LESSTHAN, 20000));
        ((PreferenceSubset)subsetCollection.getSubset(currentSubset)).addDiscreteItem(new DiscreteAttributeItem("Electric_Windows","Yes"));
        
        currentSubset = subsetCollection.addNewSubset();
        ((PreferenceSubset)subsetCollection.getSubset(currentSubset)).addDiscreteItem(new DiscreteAttributeItem("Make","Ford"));
        ((PreferenceSubset)subsetCollection.getSubset(currentSubset)).addDiscreteItem(new DiscreteAttributeItem("Colour","Blue"));
        ((PreferenceSubset)subsetCollection.getSubset(currentSubset)).addDiscreteItem(new DiscreteAttributeItem("Electric_Windows","Yes"));
        
        System.out.println(subsetCollection.toString());
        
    }
    
    public static void main(String args[]) {
        Test main = new Test();
    }
}