package edu.pathfinder.alg.ant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import edu.pathfinder.alg.AlgSolver;
import edu.pathfinder.criter.Strategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.params.Path;
import edu.pathfinder.utils.EdgeNode;
import edu.pathfinder.wf.WeightFunction;
import edu.uci.ics.jung.graph.Graph;

public class Ant implements AlgSolver{
	
	public Ant(){
		feramonOnEdges = new HashMap<Edge, Double>();
		visitedEdges = new ArrayList<Edge>();
		chance = new HashMap<EdgeNode, Double>();
		candidats = new ArrayList<Path>();
		countOfIncludes = 0;
	}
	
	/* TODO dynamic*/
	private int eras = 5;
	private int iterCount = 4;
	
	private int alfa = 4;
	
	private int beta = 3;
	
	private List<Edge> visitedEdges;
	
	private Map<Edge, Double> feramonOnEdges;
	
	private Map<EdgeNode, Double> chance;
	
	private List<Path> candidats;
	
	private Path bestPath;
	
	private int countOfIncludes;
	
	private int countOfIncludesInIter;
	
	private int countOfIncludesInBP;
	
	private double firstIncreaseProc = 0.20;
	
	private double evaporation = 0.80; //20 proc
	
	private double currentAddition;
	
	private void generateRandomFeramon(Map<Edge, Double> ferEdg, int interval){
		Random rand = new Random();
		for (Edge edge : ferEdg.keySet()){
			ferEdg.put(edge, rand.nextDouble()*interval);
		}
	}
	
	
	private void init(){
		
	}
	
	private void reset(){
		
	}
	
	private void serPredefinedFeramon(Map<Edge, Double> ferEdg, Double value){
		for (Edge edge : ferEdg.keySet()){
			ferEdg.put(edge, value);
		}
	}

	@Override
	public Path findPath(Graph g, WeightFunction wf,
			Strategy strategy, Constraint cn, Vertex A, Vertex Z) {
		
		for (int era=0; era<eras;era++)
		{
			for (int iter=0; iter<iterCount ; iter++ ){
				candidats.add(findOnePath(g, wf, strategy, cn, A, Z));
				reset();
			}
		}
		
		if (candidats.size() == 0){
			return null;
		}
		
		Path bestPath = candidats.get(0);
		if (candidats.size() >1){
			for (int i=1; i<candidats.size(); i++){
				if (candidats.get(i).getWeight() < bestPath.getWeight()) bestPath = candidats.get(i);
			}
		}
		
		return bestPath;
	}
	
	private Path findOnePath(Graph g, WeightFunction wf,
			Strategy strategy, Constraint cn, Vertex A, Vertex Z){
		generateRandomFeramon(feramonOnEdges, 10);
		
		/* Init block */
		
		Graph graph = g;
		WeightFunction weightFunction = wf;
		Strategy strat = strategy;
		Constraint constraints = cn;
		
		Path path = new Path();
		
		boolean flagStart = true;
		
		Vertex currentNode = null;
		
		if (!flagStart){
			currentNode = A;
			flagStart=true;
		}
		boolean dontfind = false;
		double pathLength = 0;

		while (!(currentNode.equals(Z))){
			
			//TODO get incident nodes. Nodes or Nodes and Edges
			Collection<Vertex> notCheckedIncidentNodes = graph.getIncidentVertices(currentNode);
			
			ArrayList<Vertex> checkedIncidentNodes = new ArrayList<>();
			//check, maybe we visited it?
			for (Vertex node : notCheckedIncidentNodes){
				if (visitedEdges.contains(node)) checkedIncidentNodes.add(node);
			}
			//if there is not incident nodes then end for this ant
			if (checkedIncidentNodes.size() == 0){
				System.out.println("Я заблудился и попал в тупик");
				dontfind = true;
				break;
			}
			
			/* Get next step start */
			
			Collection<Edge> incidentEdges = new ArrayList<Edge>();
			for (Vertex vertex : checkedIncidentNodes){
				incidentEdges.addAll(graph.getIncidentEdges(vertex));
			}
			
			List<EdgeNode> incidentParts = new ArrayList<EdgeNode>();
			for (Vertex vertex : checkedIncidentNodes){
				for (Object obj : graph.getIncidentEdges(vertex)){
					Edge edge = (Edge) obj;
					incidentParts.add(new EdgeNode(vertex, edge, strategy.concatinate(wf.getWeight(vertex), wf.getWeight(edge))));
				}
			}
			
			//TODO sorting is required???
			
			double total = 0.0;
			int temp = 0;
			for (EdgeNode en : incidentParts){
				double currentFeramon = 0.0;
				currentFeramon = Math.pow((feramonOnEdges.get(en.getEdge())),alfa); //TODO only from edges???
				feramonOnEdges.put(en.getEdge(), currentFeramon);
				total = total + Math.pow(currentFeramon,alfa)*Math.pow((1/temp++),beta);
			}
			
			temp = 1;
			for (EdgeNode en : incidentParts){
				chance.put(en, (Math.pow(feramonOnEdges.get(en.getEdge()), alfa)*Math.pow((1 / temp++),beta)) / total);
			}
			
			
			/* Start rolling next step */
			
			Object[] ver = chance.values().toArray();
			for (int i=1;i<ver.length;i++) ver[i] =(Double)ver[i]+(Double)ver[i-1];
			
			int iter = 0;
			for (EdgeNode en : chance.keySet()){
				chance.put(en,(Double) ver[iter++]); //TODO CHECK increment
			}
			
			double max = Collections.max(chance.values());
			
			
			double rol = 0;
			Random rand = new Random();
			rol = rand.nextDouble()*max;
			//System.out.println("Rol:"+rol);
			double min = max+1;
			int minimal = 0;
			for(int i=0;i<ver.length;i++){
				if ((Double)ver[i]>rol && (Double)ver[i]<=min){
					min = (Double)ver[i];
					minimal = i;
				}
			}
			
			path.addElement(currentNode);
			currentNode.setVisited(true);
			
			currentNode = (Vertex) chance.keySet().toArray()[minimal];
			
			/* End rolling next step */
			
			
			/* get next step end */
			
		}
		if (dontfind){
			path.addElement(currentNode);
			currentNode.setVisited(true);
			boolean pribavka = false;
			boolean first = false;
			if (countOfIncludesInIter == countOfIncludes){
				if (bestPath == null){
					first = true;
					pribavka = true;
					if (!(candidats.contains(path))){
						candidats.add(path);
					}
					bestPath = path;
				}
				
				else if (bestPath.getWeight() > path.getWeight()){
					first = false;
					pribavka = true;
					currentAddition = getAdditionFeramon(bestPath, path);
					if (!(candidats.contains(path))){
						candidats.add(path);
					}
					bestPath = path;
				}
			}
			if (pribavka && first){
				for (Object obj : graph.getEdges()){
					Edge ed = (Edge) obj;
					if (visitedEdges.contains(ed)) feramonOnEdges.put(ed, feramonOnEdges.get(ed)*firstIncreaseProc);
					else feramonOnEdges.put(ed, feramonOnEdges.get(ed)*evaporation);
				}
			}
			else if (pribavka && !first){
				for (Object obj : graph.getEdges()){
					Edge ed = (Edge) obj;
					if (visitedEdges.contains(ed)) feramonOnEdges.put(ed, feramonOnEdges.get(ed)+currentAddition);
					else feramonOnEdges.put(ed, feramonOnEdges.get(ed)*evaporation);
				}
			}
			else{
				for (Object obj : graph.getEdges()){
					Edge ed = (Edge) obj;
					feramonOnEdges.put(ed, feramonOnEdges.get(ed)*evaporation);
				}
			}
		}
		
		return path;
	}
	
	private double getAdditionFeramon(Path wasBest, Path currentBest){
		return wasBest.getWeight()/currentBest.getWeight();
	}
	
	
	
	
	
	

}
