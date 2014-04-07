package edu.pathfinder.utils;

import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;

/**
 * Uses for calculate weight Edge plus Node in this order!!!
 * @author Administrator
 *
 */
public class EdgeNode {
	
	public EdgeNode(Vertex node, Edge edge){
		this.node = node;
		this.edge = edge;
		weight = 0.0;
	}
	
	public EdgeNode(Vertex node, Edge edge, Double weigth){
		this.node = node;
		this.edge = edge;
		this.weight = weight;
	}
	
	private Vertex node;
	private Edge edge;
	private Double weight;
	
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
	
	

}
