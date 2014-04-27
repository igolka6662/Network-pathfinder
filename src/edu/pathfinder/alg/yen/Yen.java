package edu.pathfinder.alg.yen;

import java.util.ArrayList;
import java.util.List;

import edu.pathfinder.MultiAlgSolver;
import edu.pathfinder.alg.AlgSolver;
import edu.pathfinder.alg.deikstra.Deikstra;
import edu.pathfinder.criter.Strategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.graphmodel.impl.GraphElements;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.params.Path;
import edu.pathfinder.wf.WeightFunction;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.MultiGraph;

public class Yen implements MultiAlgSolver{

	private int countOfPaths = 5;
	
	@Override
	public List<Path> findPath(Graph graph, AlgSolver alg, WeightFunction wf,
			Strategy strategy, Constraint cn, Vertex A, Vertex Z) {
		Graph mainGraph = graph;
		List<Path> candidats = new ArrayList<Path>();
		List<Edge> deletedEdges = new ArrayList<Edge>();
		AlgSolver deikstra = new Deikstra();
		Path mainPath = deikstra.findPath(mainGraph, wf, strategy, cn, A, Z);
		candidats.add(mainPath);
		if (countOfPaths - 1 < mainGraph.getEdgeCount()){
			System.err.println("This graph don't have enought edges to find "+ countOfPaths + " paths");
		}
		while (candidats.size() != countOfPaths){
			Edge currentEdge = null;
			for (GraphElement ge : mainPath.getElements()){
				if (!ge.isNode() && !deletedEdges.contains(ge)){
					currentEdge = (Edge) ge;
				}
			}
			if (currentEdge == null) break;
			System.out.println("Current:"+currentEdge);
			deletedEdges.add(currentEdge);
			Graph newGraph = new DirectedSparseMultigraph<GraphElements.Vertex, GraphElements.Edge>(); //TODO Type of graph
			for (Object ob : mainGraph.getVertices()){
				newGraph.addVertex((Vertex) ob);
			}
			for (Object ob : mainGraph.getEdges()){
				if (!deletedEdges.contains((Edge) ob)){
						newGraph.addEdge((Edge) ob, mainGraph.getIncidentVertices(ob));
				}
			}
			Path candidat = deikstra.findPath(newGraph, wf, strategy, cn, A, Z);
			if (candidat!= null){
				candidats.add(candidat);
			}
		}
		
		return candidats;
	}

}
