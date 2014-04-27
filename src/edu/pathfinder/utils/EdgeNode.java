package edu.pathfinder.utils;

import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;

/**
 * Uses for calculate weight Edge plus Node in this order!!!
 * @author Administrator
 *
 */
public class EdgeNode implements Comparable<EdgeNode>{
	
	public EdgeNode(Vertex node, Edge edge){
		this.node = node;
		this.edge = edge;
		weight = 0.0;
	}
	
	public EdgeNode(Vertex node, Edge edge, Double weigth){
		this.node = node;
		this.edge = edge;
		this.weight = weigth;
		this.setInclude(0);
	}
	
	public EdgeNode(Vertex node, Edge edge, Double weigth, int include){
		this.node = node;
		this.edge = edge;
		this.weight = weigth;
		this.setInclude(include);
	}
	
	private Vertex node;
	private Edge edge;
	private Double weight;
	private int include; //2 both are include, 1 or edge or node, 0 nether
	
	public Vertex getNode() {
		return node;
	}
	public void setNode(Vertex node) {
		this.node = node;
	}
	public Edge getEdge() {
		return edge;
	}
	public void setEdge(Edge edge) {
		this.edge = edge;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public int getInclude() {
		return include;
	}

	public void setInclude(int include) {
		this.include = include;
	}
	
	public void incInclude() {
		this.include++;
	}

	@Override
	public int compareTo(EdgeNode edg2) {
		EdgeNode edg1 = this;
		if (edg1.getInclude() > edg2.getInclude()) return 1;
		else if (edg1.getInclude() < edg2.getInclude()) return -1;
		else{
			if (edg1.getWeight() > edg2.getWeight()) return 1;
			else if (edg1.getWeight() < edg2.getWeight()) return -1;
		}
		return 0;
	}
	
	

}
