package edu.pathfinder.alg;

import java.util.List;

import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.uci.ics.jung.graph.Graph;

public interface AlgSolver {
	public List<GraphElement> findPath(Graph graph, Vertex A, Vertex Z);
}
