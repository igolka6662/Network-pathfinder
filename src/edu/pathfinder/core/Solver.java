package edu.pathfinder.core;

import edu.pathfinder.alg.AlgSolver;
import edu.pathfinder.criter.Strategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.pathfinder.params.Path;
import edu.pathfinder.wf.WeightFunction;
import edu.uci.ics.jung.graph.Graph;

public class Solver {
	
	private Graph graph;
	private WeightFunction wf;
	private Strategy strategy;
	private Constraint cn;
	private Vertex A;
	private Vertex Z;
	private AlgSolver alg;
	
	
	/* What for? */
	public Solver(Graph graph, AlgSolver alg, WeightFunction wf, Strategy strategy, Constraint cn, Vertex A, Vertex Z){
		this.graph = graph;
		this.wf = wf;
		this.strategy = strategy;
		this.cn = cn;
		this.A = A;
		this.Z = Z;
		this.alg = alg;
	}
	
	public Path solve(){
		return new Path();
	}

}
