package edu.pathfinder.alg.astar;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import edu.pathfinder.alg.AlgSolver;
import edu.pathfinder.criter.Strategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.params.Path;
import edu.pathfinder.utils.EdgeNode;
import edu.pathfinder.utils.Tools;
import edu.pathfinder.wf.WeightFunction;
import edu.uci.ics.jung.graph.Graph;

/**
 * f(x) = g(x) + h(x)
 * @author Administrator
 *
 */
public class AStar implements AlgSolver{

	@Override
	public Path findPath(Graph graph, WeightFunction wf, Strategy strategy,
			Constraint cn, Vertex A, Vertex Z) {
		
		List<Vertex> closedSet = new ArrayList<Vertex>();
		Map<Vertex, Double> openset = new HashMap<Vertex, Double>();
		Map<Vertex, NodeEdge> cameFrom = new HashMap<Vertex, NodeEdge>();
		
		openset.put(A, calcEvristicValue(A, Z));
		cameFrom.put(A, null);
		
		Vertex currentVertex;
		Double currentDistance = 0.0;
		
		while (!openset.isEmpty()){
			
			currentVertex = getMinLabelVertex(openset);
			currentDistance = openset.get(currentVertex);
			
			System.out.println("Cur vertex's name:"+currentVertex.getName());
			if (currentVertex.equals(Z)){
				return reconstructBestPath(A,Z,cameFrom);
			}
			
			for (EdgeNode en : Tools.getIncidentEdgeNodes(graph, currentVertex, wf)){
				System.out.println("En:"+en.getEdge().getName()+" "+en.getNode().getName());
				if (closedSet.contains(en.getNode())) continue;
				Double tentative_g_score = currentDistance + en.getWeight();
				boolean tentative_is_better = false;
				if (!openset.keySet().contains(en.getNode())){
					tentative_is_better = true;
				}
				else{
					if (tentative_g_score < en.getWeight()){
						tentative_is_better = true;
					}
					else{
						tentative_is_better = false;
					}
				}
				
				if (tentative_is_better){
					cameFrom.put(en.getNode(), new NodeEdge(currentVertex, en.getEdge()));
					openset.put(en.getNode(), tentative_g_score + calcEvristicValue(en.getNode(), Z));
				}
				
				System.out.println("tentative_is_better:"+tentative_is_better+" "+tentative_g_score);
			}
			
			openset.remove(currentVertex);
			closedSet.add(currentVertex);
		}
		
		return null;
	}
	
	private Vertex getMinLabelVertex(Map<Vertex, Double> labels){
		Vertex minVertex = null;
		Double minValue = Double.MAX_VALUE;
		
		for (Map.Entry<Vertex, Double> entry : labels.entrySet())
		{
			if (entry.getValue() < minValue){
				minVertex = entry.getKey();
				minValue = entry.getValue();
			}
		}
		
		return minVertex;
	}
	
	private Path reconstructBestPath(Vertex start, Vertex end, Map<Vertex, NodeEdge> cameFrom){
		Path path = new Path();
		Vertex currentNode = end;
		while (currentNode != null){
			path.addElement(currentNode);
			System.out.println("Cur in path:"+currentNode.getName());
			try{
				path.addElement(cameFrom.get(currentNode).edge);
				currentNode = cameFrom.get(currentNode).node;
			}
			catch(NullPointerException e){
				break;
			}

		}
		return path;
	}
	
	private Double calcEvristicValue(GraphElement ge1, GraphElement ge2){
		return 0.0;
	}

	private class PriorityVertex implements Comparable<PriorityVertex>{
		public PriorityVertex(){
			vertex = null;
			g = 0.0;
			h = 0.0;
			f = 0.0;
			cameFrom = null;
		}
		
		public PriorityVertex(Vertex vertex){
			this.vertex = vertex;
			g = 0.0;
			h = 0.0;
			f = 0.0;
			cameFrom = null;
		}
		
		private Vertex vertex;
		private PriorityVertex cameFrom;
		private Double g;
		private Double h;
		private Double f;
		
		@Override
		public int compareTo(PriorityVertex vertex2) {
			PriorityVertex vertex1 = this;
			if (vertex1.f > vertex2.f) return 1;
			else if (vertex1.f < vertex2.f) return -1;
			return 0;
		}
		
		@Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final PriorityVertex other = (PriorityVertex) obj;
            
            if (this.f != other.f) return false;
            if (this.g != other.g) return false;
            if (this.h != other.h) return false;
            
            return true;
        }
		
//		public Vertex getVertex() {
//			return vertex;
//		}
//		public void setVertex(Vertex vertex) {
//			this.vertex = vertex;
//		}
//
//		public Double getG() {
//			return g;
//		}
//
//		public void setG(Double g) {
//			this.g = g;
//		}
//		
//		public Double getH() {
//			return g;
//		}
//
//		public void setH(Double h) {
//			this.h = h;
//		}
//
//		public Double getF() {
//			return f;
//		}
//
//		public void setF(Double f) {
//			this.f = f;
//		}
//		
//		public void calcF(){
//			setF(h+g);
//		}

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
