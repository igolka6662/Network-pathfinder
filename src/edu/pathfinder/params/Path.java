package edu.pathfinder.params;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.utils.EdgeNode;

/*
 * Comparator uses to sorting for exemple by name, weight and etc...
 * Comparable uses to compare this object with another one same type.
 * 
 * */


public class Path implements Comparator<Path>,Comparable<Path>{
	
	public Path(){
		elements = new ArrayList<GraphElement>();
		setWeight(0.0);
	}
	
	private List<GraphElement> elements;
	private double weight;
	
	@Override
	public int compare(Path o1, Path o2) {
		if (o1.getWeight() > o2.getWeight())
			return 1;
		else if (o1.getWeight() < o2.getWeight())
			return -1;
		return 0;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public List<GraphElement> getElements() {
		return elements;
	}

	public void setElements(List<GraphElement> elements) {
		this.elements = elements;
	}
	
	public void addElement(GraphElement ge){
		elements.add(ge);
	}
	
	public void addElement(EdgeNode ge){
		elements.add(ge.getEdge());
		elements.add(ge.getNode());
		weight += ge.getWeight();
	}
	
	public void addElement(GraphElement ge, Double weight){
		elements.add(ge);
		this.weight = weight;
	}
	
	public void deleteElement(GraphElement ge){
		elements.remove(ge);
	}

	@Override
	public int compareTo(Path arg) {
		if (this.getWeight() > arg.getWeight())
			return 1;
		else if (this.getWeight() < arg.getWeight())
			return -1;
		return 0;
	}
	
	

}
