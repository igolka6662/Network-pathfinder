package edu.pathfinder.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.wf.WeightFunction;
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
	
	public static List<Vertex> getIncidentVertexesForVertex(Graph g, Vertex vertex){
		List<Edge> incidentEdges = (List<Edge>) g.getIncidentEdges(vertex);
		List<Vertex> incidentNodes = new ArrayList<Vertex>();
		for (Object edge : incidentEdges){
			incidentNodes.addAll(g.getIncidentVertices(edge));
		}
		return incidentNodes;
	}
	
	public static List<EdgeNode> getIncidentEdgeNodes(Graph g, Vertex vertex, WeightFunction wf){
		List<EdgeNode> en = new ArrayList<EdgeNode>();
		Collection incidentEdges = g.getIncidentEdges(vertex);
		for (Object edj : incidentEdges){
			for (Object obj : g.getIncidentVertices(edj)){
				Edge edge = (Edge) edj;
				Vertex ver = (Vertex) obj;
				if (ver.equals(vertex)) continue;
				en.add(new EdgeNode(ver, edge, wf.getWeight(ver) + wf.getWeight(edge)));
			}
		}
		return en;
	}
	
	
	public static int getMinEdgeNodeIndex(List<EdgeNode> incidentParts){
		int min = -1;
		Double minWeight = Double.MAX_VALUE;
		for (int i =0 ; i< incidentParts.size(); i++){
			EdgeNode current = incidentParts.get(i);
			if (current.getWeight()<minWeight){
				min = i;
				minWeight = current.getWeight();
			}
		}
		return min;
	}
	
	
//	public static Edge getEdgeByEndPoints(Graph g, Vertex A, Vertex Z){
//		Edge edge = null;
//		
//		return edge;
//	}
}
