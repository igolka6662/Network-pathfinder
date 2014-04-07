package edu.pathfinder.utils;

import java.util.Comparator;
import java.util.Map;

public class ValueOnMapComparator implements Comparator<EdgeNode>{
	
	private Map<EdgeNode, Double> map;
	
	public ValueOnMapComparator(Map<EdgeNode, Double> map){
		this.map = map;
	}

	@Override
	public int compare(EdgeNode arg0, EdgeNode arg1) {
		if (map.get(arg0) >= map.get(arg1)){
			return -1;
		}
		else{
			return 1;
		}
	}

}
