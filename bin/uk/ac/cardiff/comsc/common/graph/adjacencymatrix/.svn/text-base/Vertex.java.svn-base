package uk.ac.cardiff.comsc.common.graph.adjacencymatrix;

import org.apache.log4j.*;

public class Vertex{

	/** Logger */
	static Logger logger = Logger.getLogger(Vertex.class);

	/** Keeps track of how many vertices we've created */
	static private int numberOfVertices = 0;
	
	/** The vertex's name */
	private String name;
	
	/** The vertex's label */
	private String label; 
	
	/** The vertex's content */
	private Object content;
	
	/** The vertex's colour */
	private java.awt.Color colour;
	
	/** Whether we've already visited this vertex */
	private boolean visited;
	
	/** An ArrayList of edges connected to this vertex */
	private EdgeCollection incidentEdges;

	
	/** Empty Constructor */
	public Vertex() {
		if (logger.isDebugEnabled()) {
			logger.debug("New Vertex object created");
		}

		// Set the name to the vertex's number and increment number
		setName("Vertex " + numberOfVertices);
		numberOfVertices++;
		
		// Create the ArrayList to hold incident edges
		incidentEdges = new EdgeCollection();
}

	
	/** Getters and Setters */

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting vertex name to + '" + name + "'");
		}
		this.name = name;
	}


	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting vertex '" + name + "''s label to '" + label + "'");
		}
		this.label = label;
	}


	public Object getContent() {
		return this.content;
	}

	public void setContent(Object content) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting vertex '" + name + "''s content to type'" + content.getClass().toString() + "'");
		}
		this.content = content;
	}


	public java.awt.Color getColour() {
		return this.colour;
	}

	public void setColour(java.awt.Color color) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting vertex '" + name + "''s colour to '" + getColour().toString() + "'");
		}
		this.colour = color;
	}


	public boolean isVisited() {
		return this.visited;
	}

	public void setVisited(boolean visited) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting vertex '" + name + "''s visited status to '" + visited + "'");
		}
		this.visited = visited;
	}
	

	/** Main Methods */
	
	public void addIncidentEdge(Edge e) {
		if (logger.isDebugEnabled()) {
			logger.debug("Vertex '" + name + "': adding incident edge '" + e.getName() + "'");
		}
		incidentEdges.add(e);
	}

	public void removeIncidentEdge(Edge e) {
		if (logger.isDebugEnabled()) {
			logger.debug("Vertex '" + name + "': removing incident edge '" + e.getName() + "'");
		}
		incidentEdges.remove(e);
	}

	public EdgeCollection getIncidentEdges() {
		return incidentEdges;
	}

	public int getNumberOfIncidentEdges() {
		return incidentEdges.size();
	}

	public EdgeCollection getInEdges() {
		EdgeCollection inEdges = new EdgeCollection();

		// Figure which edges are either undirected and not visited or directed correctly and not visited
		for (Edge edge : incidentEdges) {
			if (
					(!edge.isDirected())
					||
					(edge.isDirected() && edge.getEndVertex().equals(this))
			   ) {
				inEdges.add(edge);
			}
		}
		
		return inEdges;
	}

	public EdgeCollection getOutEdges() {
		EdgeCollection outEdges = new EdgeCollection();

		for (Edge edge : incidentEdges) {
			if (    (!edge.isDirected())
					||
					(edge.isDirected() && edge.getStartVertex().equals(this))
			    ) {
				outEdges.add(edge);
			}
		}

		return outEdges;
	}


	/** Object output methods */
	
	public String toString() {
		String out = new String();
		
		out  = "Vertex '" + this.name + "' = label: '" + this.label + "' / visited: '" + this.visited + "' / colour: '";
		if (this.colour != null) {
			out += this.colour.toString();
		} else {
			out += "null";
		}
		out += "' / content type: '";
		if (this.content != null) {
			out += this.content.getClass().toString();
		} else {
			out += "null";
		}
		out +=  "' / Edges (";
		for (Edge edge : incidentEdges) {
			out += "'" + edge.getName() + "'";
		}
		out += ")";
		
		return out;
	}
}
