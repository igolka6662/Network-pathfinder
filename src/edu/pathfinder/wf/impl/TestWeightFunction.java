package edu.pathfinder.wf.impl;

import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.wf.WeightFunction;

public class TestWeightFunction implements WeightFunction{

	@Override
	public double getWeight(GraphElement element) {
		if (element.isNode())
			return 1;
		else
			return 0;
	}

}
