package edu.pathfinder.graphmodel;

import java.util.List;
import java.util.Map;

public class Constraint {
	
	public Constraint(List<GraphElement> includes, boolean isOrdered, 
			Map<String,Object> minParams, Map<String,Object> maxParams){
		this.setIncludes(includes);
		this.setOrdered(isOrdered);
		this.setMinParams(minParams);
		this.setMaxParams(maxParams);
	}
	
	private List<GraphElement> includes;
	private boolean isOrdered;
	private Map<String,Object> minParams;
	private Map<String,Object> maxParams;
	
	public List<GraphElement> getIncludes() {
		return includes;
	}
	public void setIncludes(List<GraphElement> includes) {
		this.includes = includes;
	}

	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public Map<String,Object> getMinParams() {
		return minParams;
	}

	public void setMinParams(Map<String,Object> minParams) {
		this.minParams = minParams;
	}

	public Map<String,Object> getMaxParams() {
		return maxParams;
	}

	public void setMaxParams(Map<String,Object> maxParams) {
		this.maxParams = maxParams;
	}
	
	public Object getMaxParamByName(String name){
		return maxParams.get(name);
	}
	
	public Object getMinParamByName(String name){
		return maxParams.get(name);
	}

}
