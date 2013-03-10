package uk.ac.cardiff.comsc.common.graph.adjacencymatrix;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class VertexCollection implements Iterable<Vertex> {
	
	/** Logger */
	static Logger logger = Logger.getLogger(VertexCollection.class);

	/** ArrayList to hold the colletion of Vertices */
	private ArrayList<Vertex> vertexCollection;
	
	
	/** Empty Constructor */
	public VertexCollection() {
		if (logger.isDebugEnabled()) {
			logger.debug("New VertexCollection object created");
		}
		vertexCollection = new ArrayList<Vertex>();
	}
	
	
	/** Main methods */
	
	public void add(Vertex vertex) {
		if (logger.isDebugEnabled()) {
			logger.debug("Adding Vertex '" + vertex.getName() + "' to VertexCollection");
		}
		vertexCollection.add(vertex);
	}
	
	public void remove(Vertex vertex) {
		if (logger.isDebugEnabled()) {
			logger.debug("Removing Vertex '" + vertex.getName() + "' from VertexCollection");
		}
		vertexCollection.remove(vertex);
	}
	
	public Vertex get(int index) {
		return vertexCollection.get(index);
	}
	
	public int indexOf(Vertex vertex) {
		return vertexCollection.indexOf(vertex);
	}
	
	public int size() {
		return vertexCollection.size();
	}
	
	public boolean isEmpty() {
		return vertexCollection.isEmpty();
	}
	
	
	/** Object output methods */
	
	public Iterator<Vertex> iterator() {
		return vertexCollection.iterator();
	}
}
