package uk.ac.cardiff.comsc.common.graph.adjacencymatrix;

import org.apache.log4j.*;

public class Test {

	static Logger logger = Logger.getLogger(Test.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			logger.info("Running Test");
			
			/* Graph g = new Graph();
	        
	        g.addVertex("Silver");
	        g.addVertex("Black");
	        g.addVertex("Red");
	        g.addVertex("Blue");
	        
	        g.addEdge(g.getVertex("Silver"),g.getVertex("Black"), "Edge1");
	        g.addDirectedEdge(g.getVertex("Silver"),g.getVertex("Red"), "Edge2");
	        g.addEdge(g.getVertex("Red"),g.getVertex("Blue"), "Edge3");
	        
	        System.out.println(g.showAllStuff());*/
			
			Graph g = new Graph();
	        
	        g.addVertex("A");
	        g.addVertex("B");
	        g.addVertex("C");
	        g.addVertex("D");
	        g.addVertex("E");
	        g.addVertex("F");
	        g.addVertex("G");
	        g.addVertex("H");
	        g.addVertex("I");
	        
	        g.addDirectedEdge(g.getVertex("A"),g.getVertex("C"), "Edge1");
	        g.addDirectedEdge(g.getVertex("A"),g.getVertex("D"), "Edge2");
	        g.addDirectedEdge(g.getVertex("B"),g.getVertex("D"), "Edge3");
	        g.addDirectedEdge(g.getVertex("B"),g.getVertex("F"), "Edge4");
	        g.addDirectedEdge(g.getVertex("C"),g.getVertex("D"), "Edge5");
	        g.addDirectedEdge(g.getVertex("D"),g.getVertex("F"), "Edge6");
	        
	        g.addDirectedEdge(g.getVertex("C"),g.getVertex("E"), "Edge7");
	        g.addDirectedEdge(g.getVertex("E"),g.getVertex("G"), "Edge8");
	        g.addDirectedEdge(g.getVertex("F"),g.getVertex("G"), "Edge9");
	        
	        g.addDirectedEdge(g.getVertex("C"),g.getVertex("H"), "EdgeA");
	        g.addDirectedEdge(g.getVertex("H"),g.getVertex("I"), "EdgeB");
	        g.addDirectedEdge(g.getVertex("F"),g.getVertex("I"), "EdgeC");
	        g.addDirectedEdge(g.getVertex("G"),g.getVertex("I"), "EdgeD");
	        
	        System.out.println(g.showAllStuff());
	        	        
	        System.out.println("=======================");
	        System.out.println("Doing DFS:");
	        g.DFS(g.getVertex("A"));
	        //System.out.println(g.showAllStuff());
	        System.out.println();
	        
	        g.resetGraphVisitations();
	        
	        System.out.println("=======================");
	        System.out.println("Doing DirectedDFS:");
	        g.DirectedDFS(g.getVertex("A"));
	        //System.out.println(g.showAllStuff());
	        System.out.println();

	        g.resetGraphVisitations();
	        
	        System.out.println("=======================");
	        System.out.println("Doing BFS:");
	        g.BFS(g.getVertex("A"));
	        System.out.println(g.showAllStuff());
	        System.out.println();
	        
	        g.resetGraphVisitations();
	        
	        System.out.println("=======================");
	        System.out.println("Doing Directed BFS:");
	        g.DirectedBFS(g.getVertex("A"));
	        System.out.println(g.showAllStuff());
	        System.out.println();
	        
	        g.resetGraphVisitations();
	        
	        System.out.println("=======================");
	        System.out.println("Doing Weakly Connected:");        
	        if (g.isWeaklyConnected()) {
	            System.out.println("Graph IS weakly connected");
	        } else {
	            System.out.println("Graph is NOT weakly connected");
	        }  
	        System.out.println();

	        g.resetGraphVisitations();
	        
	        System.out.println("=======================");
	        System.out.println("Doing Strongly Connected:");
	        if (g.isStronglyConnected()) {
	            System.out.println("Graph IS strongly connected");
	        } else {
	            System.out.println("Graph is NOT strongly connected");
	        }  
	        System.out.println();

	        
	        System.out.println("=======================");
	        System.out.println("Topological Order:");
	        
	        VertexCollection ord = g.topologicalSort();
	        for (Vertex v : ord) {
	            System.out.println(v.toString());
	        }
	        System.out.println();


	        System.out.println("=======================");
	        System.out.println("Making graph directed:");
	        g.makeGraphFullyDirected("in-input-order");
	        System.out.println("Done!");
	        System.out.println();

	        System.out.println(g.showAllStuff());
	        
	        System.out.println("=======================");
	        System.out.println("Topological Order:");
	        
	        /*Vertex ord2[] = g.topologicalSort();
	        for (int i = 0 ; i < ord2.length ; i++) {
	            System.out.println(i + ": " + ord2[i].toString());
	        }
	        System.out.println();*/

	        for (Vertex v : g.topologicalSort()) {
	            System.out.println(v.toString());
	        }

	}

}
