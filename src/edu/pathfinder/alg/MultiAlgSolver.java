package edu.pathfinder.alg;

import java.util.List;

import edu.pathfinder.criter.Strategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.params.Path;
import edu.pathfinder.wf.WeightFunction;
import edu.uci.ics.jung.graph.Graph;

public interface MultiAlgSolver {
	public List<Path> findPath(Graph graph, AlgSolver alg, WeightFunction wf, Strategy strategy, Constraint cn, Vertex A, Vertex Z);
}
