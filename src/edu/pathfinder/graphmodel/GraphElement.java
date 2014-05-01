package edu.pathfinder.graphmodel;

public interface GraphElement {
	public boolean isInclude();
	public boolean isExclude();
	public boolean isNode();
	public String getName();
	public boolean isVisited();
}
