package uk.ac.cardiff.comsc.common.graph.adjacencymatrix;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

public class Graph {
    
	/** Logger */
	static Logger logger = Logger.getLogger(Graph.class);

	/** Definitions for the type of relative preference available */
	public static final int UNKNOWN = 0;
	public static final int NATURALLY_EQUAL = 1;
	public static final int ARTIFICIALLY_EQUAL = 2;
	public static final int NATURALLY_DIRECTED = 3;
	public static final int ARTIFICIALLY_DIRECTED = 4;

	/** A VertexCollection to hold all the vertices in the graph */
	private VertexCollection vertexCollection;
	
	/** An EdgeCollection to hold all the edges in the graph */
    private EdgeCollection edgeCollection;
    

    /** Static Methods */
    
    // Method to output the relative preference of two vertices
    public static int relativePreference(Vertex vertex1, Vertex vertex2) {
    	EdgeCollection edgesIncidentToVertex1 = vertex1.getIncidentEdges();
    	
    	// For all edges incident to vertex 1...
    	for (Edge edge : edgesIncidentToVertex1) {
    		// If we've found the relevant edge
    		if (edge.getEndVertex().equals(vertex2)) {
    			// Check the vertex on the other end of the edge
    			if (!edge.isDirected()) {
    				return Graph.NATURALLY_EQUAL;
    			} else if (edge.isDirected() && !edge.isArtificiallyDirected()) {
    				return Graph.NATURALLY_DIRECTED;
    			} else if (edge.isDirected() && edge.isArtificiallyDirected()) {
    				return Graph.ARTIFICIALLY_DIRECTED;
    			}
    		}
    	}
    	return Graph.UNKNOWN;
    }


    /** Empty Constructor */
    public Graph() {
        if (logger.isDebugEnabled()) {
        	logger.debug("New Graph object created");
        }
        vertexCollection = new VertexCollection();
        edgeCollection = new EdgeCollection();
    }
    

	/** Main methods */
    
    // Methods that deal with Vertex related things
    
    public Vertex addVertex() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Graph - Adding vertex");
    	}
        Vertex vertex = new Vertex();
        vertexCollection.add(vertex);
        return vertex;
    }
    
    public Vertex addVertex(String vertexName) {
        Vertex vertex = addVertex();
        vertex.setName(vertexName);
        return vertex;
    }

    public Vertex addVertex(Object content) {
        Vertex vertex = addVertex();
        vertex.setContent(content);
        return vertex;
    }

    public Vertex addVertex(String vertexName, Object content) {
        Vertex vertex = addVertex();
        vertex.setName(vertexName);
        vertex.setContent(content);
        return vertex;
    }
    

    public void removeVertex(Vertex vertex) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Graph - Removing vertex '" + vertex.getName() + "'");
    	}
    	
        // Firstly we want to delete all the edges referring to the vertex
        EdgeCollection incidentEdges = vertex.getIncidentEdges();
        
        if (logger.isDebugEnabled()) {
        	logger.debug("* Removing incident edges of vertex");
        }
    	
        // For all edges...
        for (Edge edge : incidentEdges) {
        	// Take the opposite vertex and remove the ref
        	if (logger.isDebugEnabled()) {
        		logger.debug("* Removing incident edge reference on Vertex '" + ((Vertex)getOpposite(vertex, edge)).getName() + "'");
        	}
            ((Vertex)getOpposite(vertex, edge)).removeIncidentEdge(edge);
            // Delete the edge
        	if (logger.isDebugEnabled()) {
        		logger.debug("* Removing incident edge '" + edge.getName() + "'");
        	}
            edgeCollection.remove(edge);
        }
        
        // Next we want to delete the vertex itself
        if (logger.isDebugEnabled()) {
        	logger.debug("* Removing Vertex + '" + vertex.getName() + "'");
        }
        vertexCollection.remove(vertex);
    }

    public void removeVertex(String vertexName) {
        removeVertex(getVertex(vertexName));
    }
    
    public void removeVertex(Object vertexContent) {
        removeVertex(getVertex(vertexContent));
    }
    

    public Vertex getVertex(int indexOfVertex) {
    	return vertexCollection.get(indexOfVertex);
    }
    
    public Vertex getVertex(String nameOfVertexToGet) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Trying to find vertex with name '" + nameOfVertexToGet + "':");
    	}
    	for (Vertex vertex : vertexCollection) {
    		if (vertex.getName().equals(nameOfVertexToGet)) {
    			if (logger.isDebugEnabled()) {
    				logger.debug("* Looking at vertex '" + vertex.getName() + "' ... Matched!");
    			}
    			return vertex;
    		}
			if (logger.isDebugEnabled()) {
				logger.debug("* Looking at vertex '" + vertex.getName() + "' ... No match");
			}
    	}
		if (logger.isDebugEnabled()) {
			logger.debug("No more vertices left to examine. No match was found.");
		}
        return null;
    }
    
    public Vertex getVertex(Vertex vertexToGet) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Trying to find vertex with a vertex object");
    	}
    	for (Vertex vertex : vertexCollection) {
    		if (vertex.equals(vertexToGet)) {
    			if (logger.isDebugEnabled()) {
    				logger.debug("* Looking at vertex '" + vertex.getName() + "' ... Matched!");
    			}
    			return vertex;
    		}
			if (logger.isDebugEnabled()) {
				logger.debug("* Looking at vertex '" + vertex.getName() + "' ... No match");
			}
    	}
		if (logger.isDebugEnabled()) {
			logger.debug("No more vertices left to examine. No match was found.");
		}
        return null;
    }

    public Vertex getVertex(Object contentOfVertexToGet) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Trying to find vertex with content type '" + contentOfVertexToGet.getClass().toString() + "':");
    	}
    	for (Vertex vertex : vertexCollection) {
    		if (vertex.getContent().equals(contentOfVertexToGet)) {
    			if (logger.isDebugEnabled()) {
    				logger.debug("* Looking at vertex '" + vertex.getName() + "' ... Matched!");
    			}
    			return vertex;
    		}
			if (logger.isDebugEnabled()) {
				logger.debug("* Looking at vertex '" + vertex.getName() + "' ... No match");
			}
    	}
		if (logger.isDebugEnabled()) {
			logger.debug("No more vertices left to examine. No match was found.");
		}
        return null;
    }
    

    public VertexCollection getVertexCollection() {
        return vertexCollection;
    }

    public int getNumberOfVertices() {
        return vertexCollection.size();
    }


    public EdgeCollection getIncidentEdges(Vertex vertex) {
        return vertex.getIncidentEdges();
    }
        
    public EdgeCollection getIncidentEdges(String vertexName) {
        return getIncidentEdges(getVertex(vertexName));
    }
    
    public EdgeCollection getIncidentEdges(Object content) {
        return getIncidentEdges(getVertex(content));
    }
    
    
    // Methods that deal with Edge related things
    
    public Edge addEdge(Vertex vertex1, Vertex vertex2) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Graph - Adding edge");
    	}
        Edge edge = new Edge();
        edge.setStartVertex(vertex1);
        edge.setEndVertex(vertex2);
        vertex1.addIncidentEdge(edge);
        vertex2.addIncidentEdge(edge);
        edgeCollection.add(edge);
        return edge;
    }

    public Edge addEdge(Vertex vertex1, Vertex vertex2, String name) {
        Edge edge = addEdge(vertex1,vertex2);
        edge.setName(name);
        return edge;
    }

    public Edge addEdge(Vertex vertex1, Vertex vertex2, Object content) {
        Edge edge = addEdge(vertex1,vertex2);
        edge.setContent(content);
        return edge;
    }

    public Edge addEdge(Vertex vertex1, Vertex vertex2, String name, Object content) {
        Edge edge = addEdge(vertex1,vertex2);
        edge.setName(name);
        edge.setContent(content);
        return edge;
    }

    public Edge addDirectedEdge(Vertex vertex1, Vertex vertex2) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Graph - Adding edge");
    	}
        Edge edge = new Edge();
        edge.setStartVertex(vertex1);
        edge.setEndVertex(vertex2);
        edge.setDirected(true);
        vertex1.addIncidentEdge(edge);
        vertex2.addIncidentEdge(edge);
        edgeCollection.add(edge);
        return edge;
    }

    public Edge addDirectedEdge(Vertex vertex1, Vertex vertex2, String name) {
        Edge edge = addDirectedEdge(vertex1,vertex2);
        edge.setName(name);
        return edge;
    }

    public Edge addDirectedEdge(Vertex vertex1, Vertex vertex2, Object content) {
        Edge edge = addDirectedEdge(vertex1,vertex2);
        edge.setContent(content);
        return edge;
    }
    
    public Edge addDirectedEdge(Vertex vertex1, Vertex vertex2, String name, Object content) {
        Edge edge = addDirectedEdge(vertex1,vertex2);
        edge.setName(name);
        edge.setContent(content);
        return edge;
    }
    

    public void removeEdge(Edge edge) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Graph - Removing edge '" + edge.getName() + "'");
    	}
        edgeCollection.remove(edge);
    }
    
    public void removeEdge(String name) {
        removeEdge(getEdge(name));
    }

    public void removeEdge(Object content) {
        removeEdge(getEdge(content));
    }
    
    
    public Edge getEdge(int indexOfEdge) {
    	return edgeCollection.get(indexOfEdge);
    }
    
    public Edge getEdge(String nameOfEdgeToGet) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Trying to find edge with name '" + nameOfEdgeToGet + "':");
    	}
    	for (Edge edge : edgeCollection) {
    		if (edge.getName().equals(nameOfEdgeToGet)) {
    			if (logger.isDebugEnabled()) {
    				logger.debug("* Looking at edge '" + edge.getName() + "' ... Matched!");
    			}
    			return edge;
    		}
			if (logger.isDebugEnabled()) {
				logger.debug("* Looking at edge '" + edge.getName() + "' ... No match");
			}
    	}
		if (logger.isDebugEnabled()) {
			logger.debug("No more edges left to examine. No match was found.");
		}
        return null;
    }
    
    public Edge getEdge(Edge edgeToGet) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Trying to find edge with content type '" + edgeToGet.getClass().toString() + "':");
    	}
    	for (Edge edge : edgeCollection) {
    		if (edge.equals(edgeToGet)) {
    			if (logger.isDebugEnabled()) {
    				logger.debug("* Looking at edge '" + edge.getContent().getClass().toString() + "' ... Matched!");
    			}
    			return edge;
    		}
			if (logger.isDebugEnabled()) {
				logger.debug("* Looking at edge '" + edge.getName() + "' ... No match");
			}
    	}
		if (logger.isDebugEnabled()) {
			logger.debug("No more edges left to examine. No match was found.");
		}
        return null;
    }

    public Edge getEdge(Object contentOfEdgeToGet) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Trying to find edge with content type '" + contentOfEdgeToGet.getClass().toString() + "':");
    	}
    	for (Edge edge : edgeCollection) {
    		if (edge.getContent().equals(contentOfEdgeToGet)) {
    			if (logger.isDebugEnabled()) {
    				logger.debug("* Looking at edge '" + edge.getContent().getClass().toString() + "' ... Matched!");
    			}
    			return edge;
    		}
			if (logger.isDebugEnabled()) {
				logger.debug("* Looking at edge '" + edge.getName() + "' ... No match");
			}
    	}
		if (logger.isDebugEnabled()) {
			logger.debug("No more edges left to examine. No match was found.");
		}
        return null;
    }

    public EdgeCollection getEdgeCollection() {
        return edgeCollection;
    }
    

    public EdgeCollection getUndirectedEdgeCollection() {
    	EdgeCollection undirectedEdgeCollection = new EdgeCollection();
    	
    	for (Edge edge : edgeCollection) {
    		if (!edge.isDirected()) {
    			undirectedEdgeCollection.add(edge);
    		}
    	}
    	return undirectedEdgeCollection;
    }
    

    public EdgeCollection getDirectedEdgeCollection() {
    	EdgeCollection directedEdgeCollection = new EdgeCollection();
    	
    	for (Edge edge : edgeCollection) {
    		if (edge.isDirected()) {
    			directedEdgeCollection.add(edge);
    		}
    	}
    	
    	return directedEdgeCollection;
    }
     
    public int getNumberOfEdges() {
        return edgeCollection.size();
    }
    
    public VertexCollection getEndVertices(Edge edge) {
    	return edge.getEndVertices();
    }
    

    public void makeEdgeDirected(Edge edge) {
        makeEdgeDirected(edge, edge.getStartVertex(), edge.getEndVertex());
    }
    
    public void makeEdgeDirected(Edge edge, Vertex vertex1, Vertex vertex2) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Making edge '" + edge.getName() + "' directed");
    	}
        if (!edge.isDirected()) {
        		edge.setDirected(true);
        		edge.setStartVertex(vertex1);
        		edge.setEndVertex(vertex2);
        }
    }
    
    
    // Misc Methods
    
    public Vertex getOpposite(Vertex vertex, Edge edge) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Finding opposite vertex of vertex '" + vertex.getName() + "' on edge '" + edge.getName() + "'");
    	}

        if (edge.getStartVertex().equals(vertex)) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Opposite vertex is '" + edge.getEndVertex().toString() + "'");
        	}
        	return edge.getEndVertex();
        } else if (edge.getEndVertex().equals(vertex)) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Opposite vertex is '" + edge.getStartVertex().toString() + "'");
        	}
        	return edge.getStartVertex();
        }
        
        /* Otherwise no matching vertex on the edge */
    	if (logger.isDebugEnabled()) {
    		logger.debug("No opposite vertex found (??!)");
    	}
    	return null;
    }
    
    

    public boolean areAdjacent(Vertex vertex1, Vertex vertex2) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Figuring out whether vertices '" + vertex1.getName() + "' and '" + vertex2.getName() + "' are adjacent");
    	}
    	
        /* First we want to find the vertex with the smallest amount of incident edges... */
        Vertex vertexWithSmallerAmountOfEdges, vertexWithLargerAmountOfEdges;
        if (vertex1.getNumberOfIncidentEdges() < vertex2.getNumberOfIncidentEdges()) {
            vertexWithSmallerAmountOfEdges = vertex1;
            vertexWithLargerAmountOfEdges = vertex2;
            if (logger.isDebugEnabled()) {
            	logger.debug("Vertex '" + vertex1.getName() + "' has smaller amount of edges.");
            }
        } else {
            vertexWithSmallerAmountOfEdges = vertex2;
            vertexWithLargerAmountOfEdges = vertex1;
            if (logger.isDebugEnabled()) {
            	logger.debug("Vertex '" + vertex2.getName() + "' has smaller amount of edges.");
            }
        }
        
        /* ...then we search that vertex's incidentedge list for the other vertex */

        EdgeCollection incidentEdges = vertexWithSmallerAmountOfEdges.getIncidentEdges();
        if (logger.isDebugEnabled()) {
        	logger.debug("Looking at incident edges of that vertex");
        }
        for (Edge e : incidentEdges) {
        	if (e.getStartVertex().equals(vertexWithLargerAmountOfEdges) || e.getEndVertex().equals(vertexWithLargerAmountOfEdges)) {
            	if (logger.isDebugEnabled()) {
            		logger.debug("Edge '" + e.getName() + "' ... Opposite vertex matches!");
            	}
        		return true;
        	}  else {
            	if (logger.isDebugEnabled()) {
            		logger.debug("Edge '" + e.getName() + "' ... no match on opposite vertex");
            	}
        	}
        }
		if (logger.isDebugEnabled()) {
			logger.debug("No more edges left to examine. No match was found.");
		}
        return false;
    }
    

    public Vertex getRoot() {
        VertexCollection vertices = new VertexCollection();
        
        vertices = topologicalSort();
        
        return vertices.get(0);
    }
    
    public boolean gotRoot() {
        return (vertexCollection.isEmpty() && edgeCollection.isEmpty());
    }
    
    public boolean isBlank() {
        return (vertexCollection.isEmpty() && edgeCollection.isEmpty());
    }
    

    public void resetGraphVisitations() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Resetting all graph visitations:");
    	}
    	for (Edge e : edgeCollection) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("* Resetting edge '" + e.getName() + "'");
    		}
    		e.setVisited(false);
    		e.setLabel(null);
    	}
    	for (Vertex v : vertexCollection) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("* Resetting vertex " + v.getName() + "'");
    		}
    		v.setVisited(false);
    		v.setLabel(null);
    	}
    }
    

    public void makeGraphFullyDirected(String method) {
        if (logger.isDebugEnabled()) {
        	logger.debug("Making graph fully directed - using method '" + method + "'");
        }

        if (method.equals("in-input-order")) {
        	for (Edge e : edgeCollection) {
        		if (!e.isDirected()) {
        			if (logger.isDebugEnabled()) {
        				logger.debug("Edge '" + e.getName() +"'not directed, making it so");
        			}
        			makeEdgeDirected(e);
        			e.setArtificiallyDirected(true);
        		}
        	}
        }
    }
    

    public void DFS(Vertex v) {
    	if (logger.isDebugEnabled()) {
            logger.debug("Doing DFS:");
    	}

        Vertex w;

        if (logger.isDebugEnabled()) {
        	logger.debug("* At vertex'" + v.getName() + "'");
        }
        v.setVisited(true);

        EdgeCollection edgeCollection = v.getIncidentEdges();
    	
        for (Edge e : edgeCollection) {
        	if (!e.isVisited()) {
        		if (logger.isDebugEnabled()) {
        			logger.debug("* * Edge '" + e.getName() + "' not yet visited. Checking opposite vertex");
        		}
        		w = getOpposite(v,e);
        		if (!w.isVisited()) {
            		if (logger.isDebugEnabled()) {
            			logger.debug("* * * Opposite vertex '" + w.getName() + "' not visited, marking edge DISCOVERY and moving there");
            		}
        			e.setLabel("DISCOVERY");
        			e.setVisited(true);
        			DFS(w);
        		} else {
            		if (logger.isDebugEnabled()) {
            			logger.debug("* * * Opposite vertex '" + w.getName() + "' already visited, marking edge BACK");
            		}
        			e.setLabel("BACK");
        			e.setVisited(true);
        		}
        	} else {
        		if (logger.isDebugEnabled()) {
        			logger.debug("* * Edge '" + e.getName() + "' already visited. Skipping");
        		}
        	}
        }
    }
    

    public void DirectedDFS(Vertex v) {
    	if (logger.isDebugEnabled()) {
            logger.debug("Doing DirectedDFS:");
    	}

        Vertex w;

        if (logger.isDebugEnabled()) {
        	logger.debug("* At vertex'" + v.getName() + "'");
        }
        v.setVisited(true);

        EdgeCollection edgeCollection = v.getOutEdges();
    	
        for (Edge e : edgeCollection) {
        	if (!e.isVisited()) {
        		if (logger.isDebugEnabled()) {
        			logger.debug("* * Edge '" + e.getName() + "' not yet visited. Checking opposite vertex");
        		}
        		w = getOpposite(v,e);
        		if (!w.isVisited()) {
            		if (logger.isDebugEnabled()) {
            			logger.debug("* * * Opposite vertex '" + w.getName() + "' not visited, marking edge DISCOVERY and moving there");
            		}
        			e.setLabel("DISCOVERY");
        			e.setVisited(true);
        			DFS(w);
        		} else {
            		if (logger.isDebugEnabled()) {
            			logger.debug("* * * Opposite vertex '" + w.getName() + "' already visited, marking edge BACK");
            		}
        			e.setLabel("BACK");
        			e.setVisited(true);
        		}
        	} else {
        		if (logger.isDebugEnabled()) {
        			logger.debug("* * Edge '" + e.getName() + "' already visited. Skipping");
        		}
        	}
        }
    }
    
    

    public void BFS(Vertex v) {
    	if (logger.isDebugEnabled()) {
            logger.debug("Doing BFS:");
            logger.debug("* Starting at vertex'" + v.getName() + "'");
    	}
        
        EdgeCollection incidentEdges;
        Vertex w;
        ArrayList<ArrayList> levels = new ArrayList<ArrayList>();
        levels.add(new ArrayList<Vertex>());

        if (logger.isDebugEnabled()) {
        	logger.debug("* Setting vertex '" + v.getName() + "' visited");
        }
        levels.get(0).add(v);
        v.setVisited(true);
        
        int i = 0;
        while (!levels.get(i).isEmpty()) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("* Current level (" + i + ") still has vertices in");
        	}
        	levels.add(new ArrayList<Vertex>());
        	if (logger.isDebugEnabled()) {
        		logger.debug("* Added new ArrayList<Vertex>");
        	}
        	for (int j = 0 ; j < ((ArrayList)levels.get(i)).size() ; j++) {
        		if (logger.isDebugEnabled()) {
        			logger.debug("* Looking at Vertex " + ((Vertex)levels.get(i).get(j)));
        		}
        		incidentEdges = ((Vertex)levels.get(i).get(j)).getIncidentEdges();
        		for (Edge edge : incidentEdges) {
        			if (!edge.isVisited()) {
        				w = getOpposite((Vertex)levels.get(i).get(j), edge);
        				if (logger.isDebugEnabled()) {
            				logger.debug("* * Looking at incident edge '" + edge.getName() + "' ... not yet visited. End vertex is " + w.getName());
        				}
        				if (!w.isVisited()) {
        					if (logger.isDebugEnabled()) {
        						logger.debug("* * * Vertex not yet visited. Setting edge DISCOVERY and visited true. Adding opposite vertex '" + w.getName() + "' to the next level");
        					}
        					edge.setLabel("DISCOVERY");
        					edge.setVisited(true);
        					w.setVisited(true);
        					levels.get(i + 1).add(w);
        				} else {
        					if (logger.isDebugEnabled()) {
        						logger.debug("* * * Vertex already. Setting edge CROSS and visited true");
        					}
        					edge.setLabel("CROSS");
        					edge.setVisited(true);
        				}
        			} else {
        				if (logger.isDebugEnabled()) {
        					logger.debug("* * Looking at incident edge '" + edge.getName() + "' ... already visited. Ignoring");
        				}
        			}
        		}
        	}
        	i++;
        }
    }
    
    

    public void DirectedBFS(Vertex v) {
    	if (logger.isDebugEnabled()) {
            logger.debug("Doing BFS:");
            logger.debug("* Starting at vertex'" + v.getName() + "'");
    	}
        
        EdgeCollection incidentEdges;
        Vertex w;
        ArrayList<ArrayList> levels = new ArrayList<ArrayList>();
        levels.add(new ArrayList<Vertex>());

        if (logger.isDebugEnabled()) {
        	logger.debug("* Setting vertex '" + v.getName() + "' visited");
        }
        levels.get(0).add(v);
        v.setVisited(true);
        
        int i = 0;
        while (!levels.get(i).isEmpty()) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("* Current level (" + i + ") still has vertices in");
        	}
        	levels.add(new ArrayList<Vertex>());
        	if (logger.isDebugEnabled()) {
        		logger.debug("* Added new ArrayList<Vertex>");
        	}
        	for (int j = 0 ; j < ((ArrayList)levels.get(i)).size() ; j++) {
        		if (logger.isDebugEnabled()) {
        			logger.debug("* Looking at Vertex " + ((Vertex)levels.get(i).get(j)));
        		}
        		incidentEdges = ((Vertex)levels.get(i).get(j)).getOutEdges();
        		for (Edge edge : incidentEdges) {
        			if (!edge.isVisited()) {
        				w = getOpposite((Vertex)levels.get(i).get(j), edge);
        				if (logger.isDebugEnabled()) {
            				logger.debug("* * Looking at incident edge '" + edge.getName() + "' ... not yet visited. End vertex is " + w.getName());
        				}
        				if (!w.isVisited()) {
        					if (logger.isDebugEnabled()) {
        						logger.debug("* * * Vertex not yet visited. Setting edge DISCOVERY and visited true. Adding opposite vertex '" + w.getName() + "' to the next level");
        					}
        					edge.setLabel("DISCOVERY");
        					edge.setVisited(true);
        					w.setVisited(true);
        					levels.get(i + 1).add(w);
        				} else {
        					if (logger.isDebugEnabled()) {
        						logger.debug("* * * Vertex already. Setting edge CROSS and visited true");
        					}
        					edge.setLabel("CROSS");
        					edge.setVisited(true);
        				}
        			} else {
        				if (logger.isDebugEnabled()) {
        					logger.debug("* * Looking at incident edge '" + edge.getName() + "' ... already visited. Ignoring");
        				}
        			}
        		}
        	}
        	i++;
        }

    }

    

    /*public ArrayList<ArrayList> getLevels() {
        logger.debug("Getting levels of Vertices");
        logger.debug("Starting at vertex '" + vertexCollection.get(0).getName() + "'");
        
        ArrayList<Edge> incidentEdges;
        Vertex w;
        ArrayList<ArrayList> levels = new ArrayList<ArrayList>();
        levels.add(new ArrayList<Vertex>());

        logger.debug("Setting vertex '" + vertexCollection.get(0).getName() + "' visited");
        levels.get(0).add(vertexCollection.get(0));
        vertexCollection.get(0).setVisited(true);
        
        int i = 0;
        while (!levels.get(i).isEmpty()) {
        	logger.debug("Current level (" + i + ") still has vertices in");
        	levels.add(new ArrayList<Vertex>());
        	logger.debug("Added new ArrayList<Vertex>");
        	for (int j = 0 ; j < ((ArrayList)levels.get(i)).size() ; j++) {
        		logger.debug("Looking at Vertex " + ((Vertex)levels.get(i).get(j)));
        		incidentEdges = ((Vertex)levels.get(i).get(j)).getIncidentEdges();
        		for (Edge e : incidentEdges) {
        			logger.debug("Looking at incident edge " + e.getName());
        			if (!e.isVisited()) {
        				w = getOpposite((Vertex)levels.get(i).get(j), e);
        				logger.debug("Edge not yet visited. End vertex is " + w.getName());
        				if (!w.isVisited()) {
        					logger.debug("Vertex not yet visited. Setting edge DISCOVERY and visited true");
        					e.setLabel("DISCOVERY");
        					e.setVisited(true);
        					w.setVisited(true);
        					logger.debug("Adding Vertex '" + w.getName() + "' to the next level");
        					levels.get(i + 1).add(w);
        				} else {
        					logger.debug("Vertex already visited. Setting edge CROSS");
        					e.setLabel("CROSS");
        					e.setVisited(true);
        				}
        			} else {
        				logger.debug("Edge already visited. Ignoring");
        			}
        		}
        	}
        	i++;
        }
        return levels;
    }*/
    

    public boolean isWeaklyConnected() {
        resetGraphVisitations();
        DFS(vertexCollection.get(0));
        for (Vertex vertex : vertexCollection) {
        	if (!vertex.isVisited()) {
        		return false;
        	}
        }
        return true;
    }
    

    public boolean isStronglyConnected() {
    	resetGraphVisitations();
    	DirectedDFS(vertexCollection.get(0));
        for (Vertex vertex : vertexCollection) {
        	if (!vertex.isVisited()) {
        		return false;
        	}
        }
        return true;
    }
    

    public boolean isFullyDirected() {
    	for (Edge edge : edgeCollection) {
    		if (!edge.isDirected()) {
    			return false;
    		}
    	}
    	return true;
    }
    

    public VertexCollection topologicalSort() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Doing Topological Sort");
    	}
    	
    	int inCounter[] = new int[vertexCollection.size()];
    	VertexCollection topologicalOrdering = new VertexCollection();
    	EdgeCollection outEdges;
    	Stack<Vertex> stack = new Stack<Vertex>();
    	Vertex u,w;
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("* Checking all vertices");
    	}
    	for (Vertex vertex : vertexCollection) {
    		if (logger.isDebugEnabled()) {
        		logger.debug("* Amount of in-edges to Vertex '" + vertex.getName() + "' is " + vertex.getInEdges().size());
    		}
    		inCounter[vertexCollection.indexOf(vertex)] = vertex.getInEdges().size();
    		if (inCounter[vertexCollection.indexOf(vertex)] == 0) {
        		if (logger.isDebugEnabled()) {
            		logger.debug("* * As the count is zero, pushing it onto the stack"); 
        		}
    			stack.push(vertex);
    		}
    	}
    	
    	while (!stack.isEmpty()) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("* There are still vertices on stack.");
    		}
    		u = stack.pop();
    		if (logger.isDebugEnabled()) {
    			logger.debug("* Popping vertex '" + u.getName() + "'. Adding to topological ordering. Getting its out edges");
    		}
    		topologicalOrdering.add(u);
    		
    		outEdges = u.getOutEdges();
    		for (Edge edge : outEdges) {
    			w = getOpposite(u, edge);
    			inCounter[vertexCollection.indexOf(w)]--;
    			if (logger.isDebugEnabled()) {
    				logger.debug("* * Opposite vertex on edge is '" + w.getName() + "'. Decrementing its inCounter to " + inCounter[vertexCollection.indexOf(w)]);
    			}
    			if (inCounter[vertexCollection.indexOf(w)] == 0) {
        			if (logger.isDebugEnabled()) {
        				logger.debug("* * * The inCounter on that vertex is now zero, so pushing to stack.");
        			}
    				stack.push(w);
    			}
    		}
    	}
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("Finished sorting");
    	}
    	return topologicalOrdering;
    }
    

	/** Object output methods */

    public String showAllStuff() {
        String out = new String();
        
        out  = "==========\n";
        out += "Graph elements:\n";
        out += "Vertices:\n";
        for (Vertex vertex : vertexCollection) {
        	out += vertex.toString() + "\n";
        }
        out += "\nEdges:\n";
        for (Edge edge : edgeCollection) {
        	out += edge.toString() + "\n";
        }
        out += "==========";

        return out ;
    }
        

    public String toString() {
        String out = new String();
        
        out = showAllStuff();
        
        return out;
    }
}