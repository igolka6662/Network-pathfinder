package edu.pathfinder.criter.impl;

import edu.pathfinder.criter.Strategy;

public class MaxStrategy implements Strategy{

	@Override
	public double concatinate(Double ge1, Double ge2) {
		return Math.max(ge1, ge2);
	}
	
}
