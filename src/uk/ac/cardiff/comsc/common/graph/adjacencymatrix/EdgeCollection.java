package uk.ac.cardiff.comsc.common.graph.adjacencymatrix;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class EdgeCollection implements Iterable<Edge> {
	
	/** Logger */
	static Logger logger = Logger.getLogger(EdgeCollection.class);

	/** ArrayList to hold the colletion of Vertices */
	private ArrayList<Edge> edgeCollection;
	

	/** Empty Constructor */
	public EdgeCollection() {
		if (logger.isDebugEnabled()) {
			logger.debug("New EdgeCollection object created");
		}
		edgeCollection = new ArrayList<Edge>();
	}
	
	
	/** Main methods */

	public void add(Edge edge) {
		if (logger.isDebugEnabled()) {
			logger.debug("Adding edge '" + edge.getName() + "' to EdgeCollection");
		}
		edgeCollection.add(edge);
	}
	
	public void remove(Edge edge) {
		if (logger.isDebugEnabled()) {
			logger.debug("Removing edge '" + edge.getName() + "' from EdgeCollection");
		}
		edgeCollection.remove(edge);
	}

	public Edge get(int index) {
		return edgeCollection.get(index);
	}
	
	public int indexOf(Edge edge) {
		return edgeCollection.indexOf(edge);
	}

	
	public int size() {
		return edgeCollection.size();
	}
	
	public boolean isEmpty() {
		return edgeCollection.isEmpty();
	}

	
	/** Object output methods */
	
	public Iterator<Edge> iterator() {
		return edgeCollection.iterator();
	}
}
