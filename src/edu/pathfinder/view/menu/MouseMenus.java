package edu.pathfinder.view.menu;

import edu.pathfinder.core.Localization;
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
            this.add(new excludeCheckBoxEdge());
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
            super(Localization.getInstance().getLocalizedString("EDIT_EDGE_PROP"));
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
            this.setText(Localization.getInstance().getLocalizedString("WEIGHT") + e + " = " + e.getWeight());
        }
    }
    
    public static class CapacityDisplay extends JMenuItem implements EdgeMenuListener<GraphElements.Edge> {
        public void setEdgeAndView(GraphElements.Edge e, VisualizationViewer visComp) {
            this.setText(Localization.getInstance().getLocalizedString("CAPACITY") + e + " = " + e.getCapacity());
        }
    }
    
    public static class includeCheckBoxEdge extends JCheckBoxMenuItem implements EdgeMenuListener<GraphElements.Edge> {
        GraphElements.Edge edge;
        
        public includeCheckBoxEdge() {
            super(Localization.getInstance().getLocalizedString("INCLUDE"));
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	edge.setInclude(isSelected());
                }
                
            });
        }
		@Override
		public void setEdgeAndView(Edge e, VisualizationViewer visComp) {
			this.edge = e;
            this.setSelected(e.isInclude() && !edge.isExclude());
			
		}
        
    }
    
    public static class excludeCheckBoxEdge extends JCheckBoxMenuItem implements EdgeMenuListener<GraphElements.Edge> {
        GraphElements.Edge edge;
        
        public excludeCheckBoxEdge() {
            super(Localization.getInstance().getLocalizedString("EXCLUDE"));
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	edge.setExclude(isSelected() && !edge.isInclude());
                }
                
            });
        }
		@Override
		public void setEdgeAndView(Edge e, VisualizationViewer visComp) {
			this.edge = e;
            this.setSelected(e.isExclude());
		}
        
    }
    
    public static class VertexMenu extends JPopupMenu {
        public VertexMenu() {
            super("Vertex Menu");
            this.add(new DeleteVertexMenuItem<GraphElements.Vertex>());
            this.addSeparator();
            this.add(new includeCheckBox());
            this.add(new excludeCheckBox());
        }
    }
    
        
        public static class includeCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.Vertex> {
            GraphElements.Vertex v;
            
            public includeCheckBox() {
                super(Localization.getInstance().getLocalizedString("INCLUDE"));
                this.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        v.setInclude(isSelected());
                    }
                    
                });
            }
            public void setVertexAndView(GraphElements.Vertex v, VisualizationViewer visComp) {
                this.v = v;
                this.setSelected(v.isInclude() && !v.isExclude());
            }
            
        }
        
        public static class excludeCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<GraphElements.Vertex> {
            GraphElements.Vertex v;
            
            public excludeCheckBox() {
                super(Localization.getInstance().getLocalizedString("EXCLUDE"));
                this.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        v.setExclude(isSelected() && !v.isInclude());
                    }
                    
                });
            }
            public void setVertexAndView(GraphElements.Vertex v, VisualizationViewer visComp) {
                this.v = v;
                this.setSelected(v.isExclude());
            }
        }
}

