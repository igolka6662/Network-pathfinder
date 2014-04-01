package edu.pathfinder.criter.impl;

import edu.pathfinder.criter.Strategy;

public class SumStrategy implements Strategy{

	@Override
	public double concatinate(Double ge1, Double ge2) {
		return ge1+ge2;
	}

}
