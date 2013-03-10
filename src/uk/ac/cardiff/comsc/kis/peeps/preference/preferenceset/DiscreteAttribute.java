package uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset;

import java.util.Iterator;

import org.apache.log4j.Logger;
import uk.ac.cardiff.comsc.common.graph.adjacencymatrix.Graph;
import uk.ac.cardiff.comsc.common.graph.adjacencymatrix.Vertex;
import uk.ac.cardiff.comsc.common.graph.adjacencymatrix.VertexCollection;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.AttributeItem;
import uk.ac.cardiff.comsc.kis.peeps.preference.common.DiscreteAttributeItem;

public class DiscreteAttribute extends Attribute implements Iterable<DiscreteAttributeItem> {

	/** Logger */
	static Logger logger = Logger.getLogger(DiscreteAttribute.class);

	/**  Graph which holds the preference graph of the discrete attribute*/
	private Graph values;
    
	
	/** Empty Constructor */
    public DiscreteAttribute(String attributeName) {
    	super(Attribute.DISCRETE_ATTRIBUTE, attributeName);
        if (logger.isDebugEnabled()) {
        	logger.debug("Created new DiscreteAttribute object");
        }
    	values = new Graph();
    }

    
    /** Main methods */
    
    public void addValueToAttribute(String value) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Attribute '" + super.getName() + "': Adding value '" + value + "'");
    	}
    	values.addVertex(new DiscreteAttributeItem(super.getName(),value));
    }
    
    public void removeValueFromAttribute(String value) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Attribute '" + super.getName() + "': Removing value '" + value + "'");
    	}
    	values.removeVertex(findVertex(value));
    }
    
    public Vertex findVertex(String valueToFind) {
    	for (Vertex vertex : values.getVertexCollection()) {
    		if (((AttributeItem)vertex.getContent()).getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
    			if (((DiscreteAttributeItem)vertex.getContent()).getValue().equals(valueToFind)) {
    				return vertex;
    			}
    		}
    	}
    	return null;
    }
    
    public DiscreteAttributeItem findAttributeItem(String valueToFind) {
    	return (DiscreteAttributeItem)findVertex(valueToFind).getContent();
    }
    
    
    public void setPreferredOrdering(String value1, String value2) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Attribute '" + super.getName() + "': Setting preferred ordering between values '" + value1 + "' and '" + value2 + "'");
    	}
    	values.addDirectedEdge(findVertex(value1),findVertex(value2));
    }
    
    public void setEquallyPreferred(String value1, String value2) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Attribute '" + super.getName() + "': Setting values '" + value1 + "' and '" + value2 + "' as equally preferred");
    	}
    	values.addEdge(findVertex(value1),findVertex(value2));
    }
    
    public int relativePreference(String value1, String value2) {
    	return Graph.relativePreference(findVertex(value1), findVertex(value2));
    }

        
    public void makeGraphFullyDirected(String method) {
        values.makeGraphFullyDirected(method);
        logger.debug("Graph has been made fully directed");
    }
    
    public int getNumberOfValues() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Getting number of values for discrete attribute (" + super.getName() + ")");
    		logger.debug("Number of values is " + values.getNumberOfVertices());
    	}
    	return values.getNumberOfVertices();
    }
    
    public DiscreteAttributeItem getItemAt(int indexOfItem) {
    	VertexCollection sortedVertices = values.topologicalSort();
    	return (DiscreteAttributeItem)sortedVertices.get(indexOfItem).getContent();
    }
    
    /** Object output methods */
    
    public String internalGraphToString() {
        String out = new String();

        VertexCollection sortedVertices = values.topologicalSort();

        for (int i = 0 ; i < sortedVertices.size() ; i++ ) {
            out += ((DiscreteAttributeItem)sortedVertices.get(i).getContent()).getValue();

            // As long as we're not on the last attribute
            if (i < sortedVertices.size() - 1) {
            	if (Graph.relativePreference(sortedVertices.get(i), sortedVertices.get(i + 1)) == Graph.NATURALLY_EQUAL) {
                    out += " - ";
                } else if (Graph.relativePreference(sortedVertices.get(i), sortedVertices.get(i + 1)) == Graph.NATURALLY_DIRECTED) {
                    out += " >> ";
                } else if (Graph.relativePreference(sortedVertices.get(i), sortedVertices.get(i + 1)) == Graph.ARTIFICIALLY_DIRECTED) {
                    out += " > ";
                }
            }
        }
        
        return out;
    }
    
    public String toString() {
        String out = new String();
        
        out += super.getName() + " ( ";
        out += internalGraphToString();
        out += " )";
        
        return out;
    }

        
    public String showAllStuff() {
        return values.showAllStuff();
    }
    
    
    
    public Iterator<DiscreteAttributeItem> iterator() {
        return new DiscreteAttributeIterator<DiscreteAttributeItem>();
    }
    
    private class DiscreteAttributeIterator<DiscreteAttributeValue> implements Iterator {
    	Iterator iter = values.topologicalSort().iterator();
        
        public boolean hasNext() {
            return iter.hasNext();
        }
        
        public DiscreteAttributeItem next() {
        	return (DiscreteAttributeItem)((Vertex)iter.next()).getContent();
        }
        
        public void remove() {
            iter.remove();
        }   
    }
}
