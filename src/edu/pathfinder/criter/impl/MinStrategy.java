package edu.pathfinder.criter.impl;

import edu.pathfinder.criter.Strategy;

public class MinStrategy implements Strategy{

	@Override
	public double concatinate(Double ge1, Double ge2) {
		return Math.min(ge1, ge2);
	}

}
