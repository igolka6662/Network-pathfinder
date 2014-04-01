package edu.pathfinder.alg.ant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.pathfinder.alg.AlgSolver;
import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.uci.ics.jung.graph.Graph;

public class Ant implements AlgSolver{
	
	public Ant(){
		feramonOnEdges = new HashMap<Edge, Double>();
	}
	
	/* TODO dynamic*/
	private int eras = 0;
	private int iterCount = 0;
	
	private Map<Edge, Double> feramonOnEdges;

	@Override
	public List<GraphElement> findPath(Graph graph, Vertex A, Vertex Z) {
		generateRandomFeramon(feramonOnEdges, 10);
		
		return null;
	}
	
	private void generateRandomFeramon(Map<Edge, Double> ferEdg, int interval){
		Random rand = new Random();
		for (Edge edge : ferEdg.keySet()){
			ferEdg.put(edge, rand.nextDouble()*interval);
		}
	}
	
	private void serPredefinedFeramon(Map<Edge, Double> ferEdg, Double value){
		for (Edge edge : ferEdg.keySet()){
			ferEdg.put(edge, value);
		}
	}
	
	
	
	

}
