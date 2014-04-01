package edu.pathfinder.view.menu;

import edu.pathfinder.graphmodel.impl.GraphElements;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.view.menu.items.ChangeColorOfEdgeMenuItem;
import edu.pathfinder.view.menu.items.DeleteEdgeMenuItem;
import edu.pathfinder.view.menu.items.DeleteVertexMenuItem;
import edu.uci.ics.jung.visualization.VisualizationViewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class MouseMenus {
    
    public static class EdgeMenu extends JPopupMenu {        
        // private JFrame frame; 
        public EdgeMenu(final JFrame frame) {
            super("Edge Menu");
            // this.frame = frame;
            this.add(new ChangeColorOfEdgeMenuItem<GraphElements.Edge>());
            this.add(new DeleteEdgeMenuItem<GraphElements.Edge>());
            this.add(new includeCheckBoxEdge());
            this.addSeparator();
            this.add(new WeightDisplay());
            this.add(new CapacityDisplay());
            this.addSeparator();
            this.add(new EdgePropItem(frame));           
        }
        
    }
    
    public static class EdgePropItem extends JMenuItem implements EdgeMenuListener<GraphElements.Edge>,
            MenuPointListener {
        GraphElements.Edge edge;
        VisualizationViewer visComp;
        Point2D point;
        
        public void setEdgeAndView(GraphElements.Edge edge, VisualizationViewer visComp) {
            this.edge = edge;
            this.visComp = visComp;
        }

        public void setPoint(Point2D point) {
            this.point = point;
        }
        
        public  EdgePropItem(final JFrame frame) {            
            super("Edit Edge Properties...");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    EdgePropertyDialog dialog = new EdgePropertyDialog(frame, edge);
                    dialog.setLocation((int)point.getX()+ frame.getX(), (int)point.getY()+ frame.getY());
                    dialog.setVisible(true);
                }
                
            });
        }
        
    }
    public static class WeightDisplay extends JMenuItem implements EdgeMenuListener<GraphElements.Edge> {
        public void setEdgeAndView(GraphElements.Edge e, VisualizationViewer visComp) {
            this.setText("Weight " + e + " = " + e.getWeight());
        }
    }
    
    public static class CapacityDisplay extends JMenuItem implements EdgeMenuListener<GraphElements.Edge> {
        public void setEdgeAndView(GraphElements.Edge e, VisualizationViewer visComp) {
            this.setText("Capacity " + e + " = " + e.getCapacity());
        }
    }
    
    public static class includeCheckBoxEdge extends JCheckBoxMenuItem implements EdgeMenuListener<GraphElements.Edge> {
        GraphElements.Edge edge;
        
        public includeCheckBoxEdge() {
            super("Include");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	edge.setInclude(isSelected());
                }
                
            });
        }
		@Override
		public void setEdgeAndView(Edge e, VisualizationViewer visComp) {
			this.edge = e;
            this.setSelected(e.isInclude());
			
		}
        
    }
    
    public static class VertexMenu extends JPopupMenu {
        public VertexMenu() {
            super("Vertex Menu");
            this.add(new DeleteVertexMenuItem<GraphElements.Vertex>());
            this.addSeparator();
            this.add(new includeCheckBox());
//            this.add(new pscCheckBox());
//            this.add(new tdmCheckBox());
        }
    }
    
    public static class pscCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.Vertex> {
        GraphElements.Vertex v;
        
        public pscCheckBox() {
            super("PSC Capable");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    v.setPacketSwitchCapable(isSelected());
                }
                
            });
        }
        public void setVertexAndView(GraphElements.Vertex v, VisualizationViewer visComp) {
            this.v = v;
            this.setSelected(v.isPacketSwitchCapable());
        }
        
    }
    
        public static class tdmCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.Vertex> {
        GraphElements.Vertex v;
        
        public tdmCheckBox() {
            super("TDM Capable");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    v.setTdmSwitchCapable(isSelected());
                }
                
            });
        }
        public void setVertexAndView(GraphElements.Vertex v, VisualizationViewer visComp) {
            this.v = v;
            this.setSelected(v.isTdmSwitchCapable());
        }
        
    }
        
        public static class includeCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.Vertex> {
            GraphElements.Vertex v;
            
            public includeCheckBox() {
                super("Include");
                this.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        v.setInclude(isSelected());
                    }
                    
                });
            }
            public void setVertexAndView(GraphElements.Vertex v, VisualizationViewer visComp) {
                this.v = v;
                this.setSelected(v.isInclude());
            }
            
        }
    
}

