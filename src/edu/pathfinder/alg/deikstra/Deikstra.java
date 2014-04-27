package edu.pathfinder.alg.deikstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pathfinder.alg.AlgSolver;
import edu.pathfinder.criter.Strategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.params.Path;
import edu.pathfinder.utils.EdgeNode;
import edu.pathfinder.utils.Tools;
import edu.pathfinder.wf.WeightFunction;
import edu.uci.ics.jung.graph.Graph;

public class Deikstra implements AlgSolver{
	
	@Override
	public Path findPath(Graph graph, WeightFunction wf, Strategy strategy,
			Constraint cn, Vertex A, Vertex Z) {
		/* Init start */
		Map<Vertex, Double> labels = new HashMap<Vertex, Double>();
		fillTheMapWithInfValues(graph,labels);
		Map<Vertex, NodeEdge> cameFrom = new HashMap<Vertex, NodeEdge>();
		List<Vertex> unvisitedVertexes = new ArrayList<Vertex>();
		for (Object vertex : graph.getVertices()){
			unvisitedVertexes.add((Vertex) vertex);
		}
		
		Double currentLabel = 0.0;
		
		labels.put(A, currentLabel);
		cameFrom.put(A, null);
		
		/* Init end */
		
		Vertex currentVertex = A;
		
		unvisitedVertexes.remove(currentVertex);

		while (!unvisitedVertexes.isEmpty()){
			
			List<EdgeNode> incidentParts = Tools.getIncidentEdgeNodes(graph, currentVertex, wf);
			for (int i =0 ; i< incidentParts.size(); i++){
				//TODO may be you should use strategy instead of plus?
				if ((incidentParts.get(i).getWeight() + currentLabel) < labels.get(incidentParts.get(i).getNode())){
					labels.put(incidentParts.get(i).getNode(), incidentParts.get(i).getWeight() + currentLabel);
					cameFrom.put(incidentParts.get(i).getNode(), new NodeEdge(currentVertex, incidentParts.get(i).getEdge()));
				}
			}
			
			unvisitedVertexes.remove(currentVertex);
			
			if (unvisitedVertexes.isEmpty()) break;
			
			currentVertex = this.getMinLabelVertex(unvisitedVertexes, labels);
			currentLabel = labels.get(currentVertex);
		}
		
		return reconstructPath(cameFrom ,A ,Z);
	}
	
	private Path reconstructPath(Map<Vertex, NodeEdge> cameFrom, Vertex A, Vertex Z){
		Path path = new Path();
		Vertex current = Z;
		while (!current.equals(A)){
			NodeEdge ne = cameFrom.get(current);
			path.addElement(ne.edge);
			path.addElement(ne.node);
			current = ne.node;
		}
		return path;
	}
	
	private void fillTheMapWithInfValues(Graph g, Map<Vertex, Double> map){
		for (Object vertex : g.getVertices()){
			map.put((Vertex) vertex, Double.MAX_VALUE);
		}
	}
	
	private Vertex getMinLabelVertex(List<Vertex> unvisitedVertexes, Map<Vertex, Double> labels){
		int indexMin = -1;
		Double minValue = Double.MAX_VALUE;
		for (int i =0; i< unvisitedVertexes.size(); i++){
			if (labels.get(unvisitedVertexes.get(i)) < minValue){
				indexMin = i;
				minValue = labels.get(unvisitedVertexes.get(i));
			}
		}
		return unvisitedVertexes.get(indexMin);
	}
	
	private class NodeEdge{
		
		private Edge edge;
		private Vertex node;
		
		public NodeEdge(Vertex node, Edge edge){
			this.node = node;
			this.edge = edge;
		}
		
		
	}

}
