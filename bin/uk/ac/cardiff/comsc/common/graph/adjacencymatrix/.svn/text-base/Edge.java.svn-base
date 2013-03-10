package uk.ac.cardiff.comsc.common.graph.adjacencymatrix;

import org.apache.log4j.Logger;

public class Edge {

	/** Logger */
	static Logger logger = Logger.getLogger(Edge.class);

	/** Keeps track of how many edges we've created */
	static int numberOfEdges = 0;

	/** The edge's name */
    private String name;

	/** The edge's label */
    private String label;

    /** The edge's content */
    private Object content;

    /** The edge's colour*/
    private java.awt.Color colour;

    /** The edge's weight */
    private double weight;

    /** Whether this edge has been visited */
    private boolean visited;

    /** Whether the edge is directed */
    private boolean directed;

    /** Whether the edge's directed-ness was artificially created */
    private boolean artificiallyDirected;
    
    /** Whether this edge was artificially created */
    private boolean artificiallyCreated;

    /** A reference to the start vertex on this edge */
    private Vertex startVertex;

    /** A reference to the end vertex on this edge */
    private Vertex endVertex;

    
	/** Empty Constructor */
    public Edge() {
        if (logger.isDebugEnabled()) {
			logger.debug("New Edge object created");
		}

        // Set the name to the edge's number and increment number
		setName("Edge " + numberOfEdges);
    	numberOfEdges++;
    	
        setVisited(false);
        setDirected(false);
        setArtificiallyCreated(false);
        setArtificiallyDirected(false);
    }

    
    /** Getters and Setters */
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Setting edge's name to '" + name + "'");
    	}
        this.name = name;
    }
    
    
    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(String label) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s label to '" + label + "'");
		}
		this.label = label;
    }
    
    
    public Object getContent() {
        return this.content;
    }
    
    public void setContent(Object content) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + content + "''s content type to '" + content.getClass().toString() + "'");
		}
        this.content = content;
    }
    
    
    public java.awt.Color getColour() {
        return this.colour;
    }
    
    public void setColour(java.awt.Color colour) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s colour to '" + colour.toString() + "'");
		}
        this.colour = colour;
    }
    
    
    public double getWeight() {
        return this.weight;
    }
    
    public void setWeight(double weight) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s weight to '" + weight + "'");
		}
        this.weight = weight;
    }
    
    
    public boolean isVisited() {
        return this.visited;
    }
    
    public void setVisited(boolean visited) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s visited status to '" + visited + "'");
		}
        this.visited = visited;
    }

    
    public Vertex getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Vertex startVertex) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s start vertex to '" + startVertex.getName() + "'");
		}
        this.startVertex = startVertex;
    }
    

    public Vertex getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(Vertex endVertex) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s end vertex to '" + endVertex.getName() + "'");
		}
        this.endVertex = endVertex;
    }


    public VertexCollection getEndVertices() {
        VertexCollection endVertices = new VertexCollection();
        endVertices.add(startVertex);
        endVertices.add(endVertex);
    	return endVertices;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s directed status to '" + directed + "'");
		}
        this.directed = directed;
    }


	public boolean isArtificiallyCreated() {
		return artificiallyCreated;
	}

	public void setArtificiallyCreated(boolean artificiallyCreated) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s artificially created status to '" + artificiallyCreated + "'");
		}
		this.artificiallyCreated = artificiallyCreated;
	}


	public boolean isArtificiallyDirected() {
		return artificiallyDirected;
	}

	public void setArtificiallyDirected(boolean artificiallyDirected) {
		if (logger.isDebugEnabled()) {
			logger.debug("Setting edge '" + name + "''s artificially directed status to '" + artificiallyDirected + "'");
		}
		this.artificiallyDirected = artificiallyDirected;
	}


	/** Object output methods */
	
	public String toString() {
		String out = new String();
		
		out = "Edge '" + this.name + "' = label: '" + this.label + "' / visited: '" + this.visited + "' / colour: '";
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
		out += "' / directed: '" + this.directed + "' / artificially directed: '" + this.artificiallyDirected + "' / artificially created: '" + this.artificiallyCreated + "' / Start Vertex ('" + startVertex.getName() + "') / End Vertex ('" + endVertex.getName() + "')";
		
		return out;
	}

}