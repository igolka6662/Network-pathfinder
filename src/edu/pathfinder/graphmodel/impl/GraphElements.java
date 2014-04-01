package edu.pathfinder.graphmodel.impl;

/*
 * GraphElements.java
 *
 * Created on March 21, 2007, 9:57 AM
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */


import org.apache.commons.collections15.Factory;

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
        private boolean packetSwitchCapable;
        private boolean tdmSwitchCapable;
        private String iconName;
        
        public Vertex(String name) {
            this.name = name;
            this.setInclude(false);
        }
        
        public Vertex(String name, boolean include) {
            this.name = name;
            this.setInclude(include);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPacketSwitchCapable() {
            return packetSwitchCapable;
        }

        public void setPacketSwitchCapable(boolean packetSwitchCapable) {
            this.packetSwitchCapable = packetSwitchCapable;
        }

        public boolean isTdmSwitchCapable() {
            return tdmSwitchCapable;
        }

        public void setTdmSwitchCapable(boolean tdmSwitchCapable) {
            this.tdmSwitchCapable = tdmSwitchCapable;
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
    }
    
    public static class Edge implements GraphElement{
        private double capacity;
        private double weight;
        private boolean include;
        private String name;

        public Edge(String name) {
            this.name = name;
            this.setInclude(false);
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
    }
    
    // Single factory for creating Vertices...
    public static class MyVertexFactory implements Factory<Vertex> {
        private static int nodeCount = 0;
        private static boolean defaultPSC = false;
        private static boolean defaultTDM = true;
        private static String defaultIcon = "vertex.png";
        private static MyVertexFactory instance = new MyVertexFactory();
        
        private MyVertexFactory() {            
        }
        
        public static MyVertexFactory getInstance() {
            return instance;
        }
        
        public GraphElements.Vertex create() {
            String name = "Node" + nodeCount++;
            Vertex v = new Vertex(name);
            v.setIconName(defaultIcon);
            v.setPacketSwitchCapable(defaultPSC);
            v.setTdmSwitchCapable(defaultTDM);
            return v;
        }        

        public static boolean isDefaultPSC() {
            return defaultPSC;
        }

        public static void setDefaultPSC(boolean aDefaultPSC) {
            defaultPSC = aDefaultPSC;
        }

        public static boolean isDefaultTDM() {
            return defaultTDM;
        }

        public static void setDefaultTDM(boolean aDefaultTDM) {
            defaultTDM = aDefaultTDM;
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
            String name = "Link" + linkCount++;
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

