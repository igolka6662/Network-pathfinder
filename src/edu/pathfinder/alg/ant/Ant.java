package edu.pathfinder.alg.ant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
		candidats = new ArrayList<Path>();
		countOfIncludesInBP = 0;
		feramonOnEdges = new HashMap<Edge, Double>();
		init();
	}
	
	/* TODO dynamic*/
	private int eras = 5;
	private int iterCount = 6;
	
	private int alfa = 4;
	
	private int beta = 3;
	
	private List<Edge> visitedEdges;
	
	private List<Vertex> visitedNodes;
	
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
	
	private void generateRandomFeramon(Graph g, Map<Edge, Double> ferEdg, int interval){
		Random rand = new Random();
		for (Object edge : g.getEdges()){
			ferEdg.put((Edge) edge, rand.nextDouble()*interval);
		}
	}
	
	
	private void init(){
		//feramonOnEdges = new HashMap<Edge, Double>();
		visitedEdges = new ArrayList<Edge>();
		visitedNodes = new ArrayList<Vertex>();
		chance = new LinkedHashMap<EdgeNode, Double>();
		countOfIncludes = 0;
		countOfIncludesInIter = 0;
	}
	
	private void reset(){
		visitedEdges = new ArrayList<Edge>();
		visitedNodes = new ArrayList<Vertex>();
		chance = new LinkedHashMap<EdgeNode, Double>();
		countOfIncludesInBP = 0;
		countOfIncludesInIter = 0;
	}
	
	private void serPredefinedFeramon(Graph g,Map<Edge, Double> ferEdg, Double value){
		for (Object edge : g.getEdges()){
			ferEdg.put((Edge) edge, value);
		}
	}

	@Override
	public Path findPath(Graph g, WeightFunction wf,
			Strategy strategy, Constraint cn, Vertex A, Vertex Z) {
		countOfIncludes = cn.getIncludes().size();
		
		generateRandomFeramon(g, feramonOnEdges, 10);
		
		for (int era=0; era<eras;era++)
		{
			init();
			for (int iter=0; iter<iterCount ; iter++ ){
				Path candidat = findOnePath(g, wf, strategy, cn, A, Z);
				if (candidat!=null) candidats.add(candidat);
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
		
		/* Init block */
		
		Graph graph = g;
		WeightFunction weightFunction = wf;
		Strategy strat = strategy;
		Constraint constraints = cn;
		
		Path path = new Path();
		
		Vertex currentNode = A;
		EdgeNode current = null;
		boolean dontfind = false;
		
		visitedNodes.add(A);
		path.addElement(A);
		
		while (!(currentNode.equals(Z))){
			
			System.out.println("CurrentNode:"+currentNode.getName());
			System.out.println("Z:"+Z.getName());
			System.out.println(currentNode.equals(Z));
			
			Collection<Edge> notCheckedIncidentEdges = graph.getIncidentEdges(currentNode);
			//System.out.println("notCheckedIncidentEdges:"+notCheckedIncidentEdges);
			ArrayList<Edge> checkedIncidentEdges = new ArrayList<>();
			
			for (Object edge : notCheckedIncidentEdges){
				if (!visitedEdges.contains(edge)) checkedIncidentEdges.add((Edge) edge);
			}
			
			System.out.println("checkedIncidentEdges:"+checkedIncidentEdges);
			ArrayList<Vertex> notCheckedIncidentNodes = new ArrayList<Vertex>();
			
			for (Object edge : checkedIncidentEdges){
				notCheckedIncidentNodes.addAll(g.getIncidentVertices(edge));
			}
			
			System.out.println("notCheckedIncidentNodes:"+notCheckedIncidentNodes);
			
			ArrayList<Vertex> checkedIncidentNodes = new ArrayList<>();
			//check, maybe we visited it?
			for (Vertex node : notCheckedIncidentNodes){
				if (!visitedNodes.contains(node)) checkedIncidentNodes.add(node);
			}
			
			System.out.println("checkedIncidentNodes:"+checkedIncidentNodes);
			
			//if there is not incident nodes then end for this ant
			if (checkedIncidentNodes.size() == 0 || checkedIncidentEdges.size() == 0){
				System.out.println("Я заблудился и попал в тупик");
				dontfind = true;
				break;
			}
			
			/* Get next step start */
			
			Collection<Edge> incidentEdges = new ArrayList<Edge>();
			for (Vertex vertex : checkedIncidentNodes){
				incidentEdges.addAll(graph.getIncidentEdges(vertex)); //TODO проверить что он не затирает
			}
			
			List<EdgeNode> incidentParts = new ArrayList<EdgeNode>();
			for (Vertex vertex : checkedIncidentNodes){
				for (Object obj : graph.getIncidentEdges(vertex)){
					Edge edge = (Edge) obj;
					int include = 0;
					if (cn.getIncludes().contains(edge)) include++;
					if (cn.getIncludes().contains(vertex)) include++;
					System.out.println(vertex.getName() + " "+ edge.getName());
					incidentParts.add(new EdgeNode(vertex, edge, strategy.concatinate(wf.getWeight(vertex), wf.getWeight(edge)), include));
				}
			}
			
			Collections.sort(incidentParts);
			
			double total = 0.0;
			int temp = 0;
			for (EdgeNode en : incidentParts){
				double currentFeramon = 0.0;
				//System.out.println("Size:"+feramonOnEdges.size());
				currentFeramon = Math.pow((feramonOnEdges.get(en.getEdge())),alfa); //TODO only from edges???
				feramonOnEdges.put(en.getEdge(), currentFeramon);
				total = total + Math.pow(currentFeramon,alfa)*Math.pow((1/++temp),beta);
				System.out.println("Total:"+total);
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
			
			System.out.println("Min:"+minimal);
			
			int pos = 0;
			for (EdgeNode en: chance.keySet()){
				if (pos == minimal){
					current = en;
					break;
				}
				pos++;
			}
			
			System.out.println("Current:"+current.getEdge().getName()+current.getNode().getName());
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			path.addElement(current);
			visitedNodes.add(current.getNode());
			visitedEdges.add(current.getEdge());
			System.out.println("Before current:"+currentNode.getName());
			currentNode = current.getNode();
			
			if (currentNode.equals(Z)) dontfind = true;
			
			System.out.println("After current:"+currentNode.getName());
			/* End rolling next step */
			
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
			return null; //TODO Check for it
		}
		
		return path;
	}
	
	private double getAdditionFeramon(Path wasBest, Path currentBest){
		return wasBest.getWeight()/currentBest.getWeight();
	}
	
	
	
	
	
	

}
