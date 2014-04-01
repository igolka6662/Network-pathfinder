package edu.pathfinder.wf;

import edu.pathfinder.graphmodel.GraphElement;

public interface WeightFunction {
	public double getWeight(GraphElement element);
}
