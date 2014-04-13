package edu.pathfinder.utils;

import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.uci.ics.jung.graph.Graph;

public class Tools {
	
	public Tools(){
		
	}
	
	public static Vertex getVertexByName(Graph g, String name){
		for (Object obj : g.getVertices()){
			Vertex ver = (Vertex) obj;
			if (name.equals(ver.getName())) return ver;
		}
		return null;
	}
}
