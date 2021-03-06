package edu.pathfinder.graphmodel.impl;

/*
 * GraphElements.java
 *
 * Created on March 21, 2007, 9:57 AM
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */


import java.util.Random;

import org.apache.commons.collections15.Factory;

import edu.pathfinder.core.Localization;
import edu.pathfinder.graphmodel.GraphElement;

/**
 *
 * @author Dr. Greg M. Bernstein
 */
public class GraphElements {
    
    /** Creates a new instance of GraphElements */
    public GraphElements() {
    }
    
    public static class Vertex implements GraphElement{
        private String name;
        private boolean include;
        private boolean exclude;
        private String iconName;
        private boolean visited;
        
        public Vertex(String name) {
            this.name = name;
            this.setInclude(false);
            visited = false;
            setExclude(false);
        }
        
        public Vertex(String name, boolean include) {
            this.name = name;
            this.setInclude(include);
            setExclude(false);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

		public boolean isInclude() {
			return include;
		}

		public void setInclude(boolean include) {
			this.include = include;
		}

		public String getIconName() {
			return iconName;
		}

		public void setIconName(String iconName) {
			this.iconName = iconName;
		}

		@Override
		public boolean isNode() {
			return true;
		}
		
		public void setVisited(boolean val){
			this.visited = val;
		}

		@Override
		public boolean isVisited() {
			return visited;
		}
		
		@Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Vertex other = (Vertex) obj;
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
                return false;
            }
            return true;
        }

		@Override
		public boolean isExclude() {
			return exclude;
		}

		public void setExclude(boolean exclude) {
			this.exclude = exclude;
		}
    }
    
    public static class Edge implements GraphElement{
        private double capacity;
        private double weight;
        private boolean include;
        private boolean exclude;
        private String name;
        private boolean visited;
        private Double defaultWeight = 100.0;
        private Double defaultCapacity = 100.0;
        private Double maxWeight = 256.0;
        private Double maxCapacity = 256.0;
        private int includeRandomChance = 5;
        private int excludeRandomChance = 5;

        public Edge(String name) {
            this.name = name;
            this.setInclude(false);
            this.setExclude(false);
            visited = false;
        }
        
        public Edge(String name, boolean isRandomParams, boolean isRandomInclude, boolean isRandomExclude ) {
            this.name = name;
            
            visited = false;
            Random rand = new Random();
            if (isRandomParams){
            	this.setWeight(rand.nextDouble() * maxWeight);
            	this.setCapacity(rand.nextDouble() * maxCapacity);
            }
            else if (!isRandomParams){
            	this.setWeight(defaultWeight);
            	this.setCapacity(defaultCapacity);
            }
            if (isRandomInclude){
            	if (rand.nextInt() * includeRandomChance == 1){
            		this.setInclude(true); 
            	}
            	else{
            		this.setInclude(false);
            	}
            }
            else if (!isRandomInclude){
            	this.setInclude(false);
            }
            if (isRandomExclude){
            	if (rand.nextInt() * excludeRandomChance == 1){
            		this.setExclude(true); 
            	}
            	else{
            		this.setInclude(false);
            	}
            }
            else if (!isRandomExclude){
            	this.setExclude(false);
            }
        }
        
        public Edge(String name,boolean include) {
            this.name = name;
            this.setInclude(include);
        }
        
        public double getCapacity() {
            return capacity;
        }

        public void setCapacity(double capacity) {
            this.capacity = capacity;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }             
        
        public String toString() {
            return name;
        }

		public boolean isInclude() {
			return include;
		}

		public void setInclude(boolean include) {
			this.include = include;
		}

		@Override
		public boolean isNode() {
			return false;
		}
		
		public void setVisited(boolean val){
			this.visited = val;
		}

		@Override
		public boolean isVisited() {
			return visited;
		}
		
		@Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Edge other = (Edge) obj;
            
            if (this.getWeight() != other.getWeight()) return false;
            if (this.getName() != other.getName()) return false;
            
            return true;
        }

		public boolean isExclude() {
			return exclude;
		}

		public void setExclude(boolean exclude) {
			this.exclude = exclude;
		}
		
		
    }
    
    // Single factory for creating Vertices...
    public static class MyVertexFactory implements Factory<Vertex> {
        private static int nodeCount = 0;
        private static String defaultIcon = "vertex.png";
        private static MyVertexFactory instance = new MyVertexFactory();
        
        private MyVertexFactory() {            
        }
        
        public static MyVertexFactory getInstance() {
            return instance;
        }
        
        public GraphElements.Vertex create() {
            String name = Localization.getInstance().getLocalizedString("NODE_FACTORY_NAME") + nodeCount++;
            Vertex v = new Vertex(name);
            v.setIconName(defaultIcon);
            return v;
        }        
    }
    
    // Singleton factory for creating Edges...
    public static class MyEdgeFactory implements Factory<Edge> {
        private static int linkCount = 0;
        private static double defaultWeight;
        private static double defaultCapacity;

        private static MyEdgeFactory instance = new MyEdgeFactory();
        
        private MyEdgeFactory() {            
        }
        
        public static MyEdgeFactory getInstance() {
            return instance;
        }
        
        public GraphElements.Edge create() {
            String name = Localization.getInstance().getLocalizedString("LINK_FACTORY_NAME") + linkCount++;
            Edge link = new Edge(name);
            link.setWeight(defaultWeight);
            link.setCapacity(defaultCapacity);
            return link;
        }    

        public static double getDefaultWeight() {
            return defaultWeight;
        }

        public static void setDefaultWeight(double aDefaultWeight) {
            defaultWeight = aDefaultWeight;
        }

        public static double getDefaultCapacity() {
            return defaultCapacity;
        }

        public static void setDefaultCapacity(double aDefaultCapacity) {
            defaultCapacity = aDefaultCapacity;
        }
        
    }

}

