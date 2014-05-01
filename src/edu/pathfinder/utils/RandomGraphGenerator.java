package edu.pathfinder.utils;

import java.util.ArrayList;
import java.util.List;

import edu.pathfinder.core.Localization;
import edu.pathfinder.graphmodel.impl.GraphElements;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class RandomGraphGenerator {
	
	public Graph generateGraph(boolean isDirected, int countOfNode, int maxEdgesBetween, int maxWeight, int maxCapacity){
		Graph graph = isDirected ? 
				new DirectedSparseMultigraph<Vertex, Edge>() :
					new  UndirectedSparseGraph<Vertex, Edge>();
		//List<String> nodesNames = new ArrayList<String>();
		List<Vertex> nodes = new ArrayList<Vertex>();
		String localNodesName = Localization.getInstance().getLocalizedString("NODE_FACTORY_NAME");
		for (int i=0; i<countOfNode; i++){
			Vertex current = new Vertex(localNodesName+i);
			nodes.add(current);
			graph.addVertex(current);
		}
		
		String localEdgeName = Localization.getInstance().getLocalizedString("LINK_FACTORY_NAME");
		int counter = 0;
//		for (int i=0; i<nodes.size(); i++){
//			for (int j=0; j<nodes.size(); j++){
//				if (i!=j){
//					graph.addEdge(new Edge(localEdgeName+counter, true, true, true), nodes.get(i), nodes.get(j));
//					counter++;
//				}
//			}
//		}
		for (Object verOb1 : graph.getVertices()){
			Vertex ver1 = (Vertex) verOb1;
			for (Object verOb2 : graph.getVertices()){
				Vertex ver2 = (Vertex) verOb2;
				if (!ver1.equals(ver2)){
					graph.addEdge(new Edge(localEdgeName+counter, true, true, true), ver1, ver2);
					counter++;
				}
			}
		}
		
		return graph;
	}
	
}
