package uk.ac.cardiff.comsc.kis.peeps.preference.preferenceset;

import java.util.Iterator;

import org.apache.log4j.Logger;

import uk.ac.cardiff.comsc.common.graph.adjacencymatrix.Graph;
import uk.ac.cardiff.comsc.common.graph.adjacencymatrix.Vertex;
import uk.ac.cardiff.comsc.common.graph.adjacencymatrix.VertexCollection;

public class PreferenceSet implements Iterable<Attribute> {

	// Logger 
	static Logger logger = Logger.getLogger(PreferenceSet.class);

	// Graph to hold the preference set
	private Graph preferenceSetGraph;
	
	
	/** Constructors */
	public PreferenceSet() {
		if (logger.isDebugEnabled()) {
			logger.info("New PreferenceSet object created");
		}
		preferenceSetGraph = new Graph();
	}
	
	/** Getters and Setters */
	
	public int getTotalPreferenceSetSize() {
		int count = 0;
		for (int i = 0; i < getNumberOfAttributes() ; i++) {
			switch (getAttributeAt(i).getAttributeType()) {
			case Attribute.DISCRETE_ATTRIBUTE:
				count += getAttributeAt(i).getNumberOfValues();
				break;
			case Attribute.NUMERIC_ATTRIBUTE:
				count += 1;
				break;
			}
		}
		return count;
	}
	
	
	/** Main methods */
	
    public Attribute addAttribute(int attributeType, String attributeName) { //throws UnknownAttributeTypeException {
        Attribute attr;
        if (attributeType == Attribute.DISCRETE_ATTRIBUTE) {
            attr = new DiscreteAttribute(attributeName);
            preferenceSetGraph.addVertex(attr);
        } else if (attributeType == Attribute.NUMERIC_ATTRIBUTE) {
            attr = new NumericAttribute(attributeName);
            preferenceSetGraph.addVertex(attr);
        } else {
        	//throw new UnknownAttributeTypeException();
        	attr = null;
        }
        return attr;
    }
    
    public void removeAttribute(String attributeName) {
    	preferenceSetGraph.removeVertex(findVertex(attributeName));
    }
    
    public Vertex findVertex(String attributeName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Finding vertex '" + attributeName + "'");
    	}
    	for(Vertex vertex : preferenceSetGraph.getVertexCollection()) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("* Looking at vertex '" + vertex.getName() + "' - '" + ((Attribute)vertex.getContent()).getName() + "'");
        	}
    		if (((Attribute)vertex.getContent()).getName().equals(attributeName)) {
    			return vertex;
    		}
    	}
    	return null;
    }
    
    public Attribute findAttribute(String attributeName) {
    	return (Attribute)findVertex(attributeName).getContent();
    }
    
    public void setPreferredOrdering(String value1, String value2) {
    	preferenceSetGraph.addDirectedEdge(findVertex(value1), findVertex(value2));
    }

    public void setEquallyPreferred(String value1, String value2) {
    	preferenceSetGraph.addEdge(findVertex(value1), findVertex(value2));
    }
    
    public void makeGraphFullyDirected(String method) {
    	// Make the graph of attributes fully directed
    	preferenceSetGraph.makeGraphFullyDirected(method);
    	
    	// Then, for each DiscreteAttribute, make that graph fully directed
    	for (Attribute attr : this) {
    		if (attr.getAttributeType() == Attribute.DISCRETE_ATTRIBUTE) {
    			((DiscreteAttribute)attr).makeGraphFullyDirected(method);
    		}
    	}
    	// Note: NumericAttributes don't need anything doing 
    }
    
    public Attribute getAttributeAt(int attributeIndex) {
    	return (Attribute)preferenceSetGraph.getVertex(attributeIndex).getContent();
    }
    
    
    public Attribute getAttribute(String attributeName) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Getting attribute '" + attributeName + "'");
    	}
    	Vertex vertexToFind = findVertex(attributeName);
    	if (logger.isDebugEnabled()) {
    		logger.debug("Found attribute");
    	}
    	return (Attribute)preferenceSetGraph.getVertex(vertexToFind).getContent();
    }
    
    public int getNumberOfAttributes() {
    	return preferenceSetGraph.getNumberOfVertices();
    }
    
    public AttributeCollection getSortedAttributeCollection() {
    	VertexCollection sortedVertices = preferenceSetGraph.topologicalSort();
    	AttributeCollection sortedAttrs = new AttributeCollection();
    	
    	// For every vertex in the sorted vertex collection, copy it into the attributecollection
    	for (Vertex vertex : sortedVertices) {
    		sortedAttrs.add((Attribute)vertex.getContent());
    	}
    	
    	return sortedAttrs;
    }
    
    public String internalGraphToString() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Converterting internal graph to string");
    	}
    	
    	
    	String out = new String();
        
    	if (logger.isDebugEnabled()) {
    		logger.debug("Topologically sorting graph");
    	}
    	VertexCollection sortedVertices = preferenceSetGraph.topologicalSort();

    	for (int i = 0 ; i < sortedVertices.size() ; i++ ) {
        	if (logger.isDebugEnabled()) {
        		logger.debug(" Name of attribute is " + ((Attribute)sortedVertices.get(i).getContent()).getName());
        	}
        	out += "\n" + ((Attribute)sortedVertices.get(i).getContent()).toString() + "\n";

        	// As long as we're not on the last attribute
            if (i < sortedVertices.size() - 1) {
            	if (Graph.relativePreference(sortedVertices.get(i), sortedVertices.get(i + 1)) == Graph.NATURALLY_EQUAL) {
                    out += " - ";
                } else if (Graph.relativePreference(sortedVertices.get(i), sortedVertices.get(i + 1)) == Graph.NATURALLY_DIRECTED) {
                    out += " >> ";
                } else if (Graph.relativePreference(sortedVertices.get(i), sortedVertices.get(i + 1)) == Graph.ARTIFICIALLY_DIRECTED) {
                    out += " > ";
                } else {
                	out += " ? ";
                }
            }
        }
        return out;
    }

    /** Object output methods */
    
    public String toString() {
        String out = new String();
        
        out += internalGraphToString();
        
        return out;
    }
    
    public Iterator<Attribute> iterator() {
    	return new PreferenceSetIterator<Attribute>();
    }
    
    private class PreferenceSetIterator<Attribute> implements Iterator {
        Iterator iter = preferenceSetGraph.topologicalSort().iterator();
    	
    	public boolean hasNext() {
            return iter.hasNext();
        }
        
        public Attribute next() {
            return (Attribute)(((Vertex)iter.next()).getContent());
        }
        
        public void remove() {
            iter.remove();
        }   
    }
}
