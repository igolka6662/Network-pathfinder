package edu.pathfinder.alg;

import java.util.List;

import edu.pathfinder.criter.Strategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.params.Path;
import edu.pathfinder.wf.WeightFunction;
import edu.uci.ics.jung.graph.Graph;

public interface AlgSolver {
	public Path findPath(Graph graph, WeightFunction wf, Strategy strategy, Constraint cn, Vertex A, Vertex Z);
}
