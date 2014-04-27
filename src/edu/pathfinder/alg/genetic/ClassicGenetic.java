package edu.pathfinder.alg.genetic;

import java.util.List;

import edu.pathfinder.alg.AlgSolver;
import edu.pathfinder.criter.Strategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.params.Path;
import edu.pathfinder.wf.WeightFunction;
import edu.uci.ics.jung.graph.Graph;

public class ClassicGenetic implements AlgSolver{
	
	boolean targetReached = false;
	int populationSize = 10;
	int countOfMutants = 5;

	@Override
	public Path findPath(Graph graph, WeightFunction wf, Strategy strategy,
			Constraint cn, Vertex A, Vertex Z) {
		Path bestPath = new Path();
		List<Path> population = generateStartPopulation(graph, populationSize);
		while (!targetReached){
			population = cross(population);
			// for best 5 paths do mutation
		}
		return bestPath;
	}
	
	private List<Path> generateStartPopulation(Graph g, int size){
		return null;
	}
	
	private List<Path> cross(List<Path> population){
		return null;
	}
	
	private Path mutation(Path path){
		return null;
	}
	
	private List<Path> selection(List<Path> candidats){
		return null;
	}
	
	private int fitnes(Path path){
		return 1;
	}
	
	

}
