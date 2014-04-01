package edu.pathfinder.view;

import edu.pathfinder.view.menu.MouseMenus;
import edu.pathfinder.view.menu.PopupVertexEdgeMenuMousePlugin;
import edu.pathfinder.view.models.ParamsComboBoxModel;
import edu.pathfinder.view.renderers.MultiVertexRenderer;
import edu.pathfinder.view.renderers.VertexIcon;
import edu.pathfinder.core.Configuration;
import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.graphmodel.impl.GraphElements;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.apache.commons.collections15.Transformer;

/**
 * Illustrates the use of custom edge and vertex classes in a graph editing application.
 * Demonstrates a new graph mouse plugin for bringing up popup menus for vertices and
 * edges.
 */
public class Viewer implements  ActionListener{
	
	private final int FORM_HEIGHT = 600;
	private final int FORM_WIDTH = 1000;
	private final int PAINT_HEIGHT = 600;
	private final int PAINT_WIDTH = 1000;
	
	public JComboBox comboBox1,comboBox2;
	public JButton getVertexes,deikstra,deikstraButton;
	private String A = null;
	private String Z = null;
	Graph<GraphElements.Vertex, GraphElements.Edge> g;
	VisualizationViewer<GraphElements.Vertex,GraphElements.Edge> vv;
	
    
    public void doing(){
    	Configuration conf = new Configuration();
    	System.out.println(conf.getApplicationLanguage());
    	System.out.println(new File(".").getAbsolutePath()); // current directory
    	JFrame frame = new JFrame("Editing and Mouse Menu Demo");
//      SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge> g = 
//              new SparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge>();
//      SelectGraphDialog dialog = new SelectGraphDialog(frame);
//      dialog.setVisible(true);
     g = 
  		  new DirectedSparseMultigraph<GraphElements.Vertex, GraphElements.Edge>();
      //DirectedSparseMultigraph
      // Layout<V, E>, VisualizationViewer<V,E>
//      Map<GraphElements.MyVertex,Point2D> vertexLocations = new HashMap<GraphElements.MyVertex, Point2D>();
      Layout<GraphElements.Vertex, GraphElements.Edge> layout = new StaticLayout(g);
      layout.setSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
       vv = 
              new VisualizationViewer<GraphElements.Vertex,GraphElements.Edge>(layout);
      //vv.setPreferredSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
      /* Add node renderer mine */
      vv.getRenderer().setVertexRenderer(new MultiVertexRenderer<GraphElements.Vertex, GraphElements.Edge>());
      vv.getRenderContext().setVertexIconTransformer(new VertexIcon());
      /* End add node renderer mine*/
       
       
      vv.setSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
      // Show vertex and edge labels
      vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
      vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
      vv.setBackground(Color.lightGray);
      //vv.setSize(new Dimension(FORM_WIDTH,FORM_HEIGHT));
      // Create a graph mouse and add it to the visualization viewer
      EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(), 
               GraphElements.MyVertexFactory.getInstance(),
              GraphElements.MyEdgeFactory.getInstance()); 
      // Set some defaults for the Edges...
      GraphElements.MyEdgeFactory.setDefaultCapacity(192.0);
      GraphElements.MyEdgeFactory.setDefaultWeight(5.0);
      // Trying out our new popup menu mouse plugin...
      PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
      // Add some popup menus for the edges and vertices to our mouse plugin.
      JPopupMenu edgeMenu = new MouseMenus.EdgeMenu(frame);
      JPopupMenu vertexMenu = new MouseMenus.VertexMenu();
      myPlugin.setEdgePopup(edgeMenu);
      myPlugin.setVertexPopup(vertexMenu);
      gm.remove(gm.getPopupEditingPlugin());  // Removes the existing popup editing plugin
      
      gm.add(myPlugin);   // Add our new plugin to the mouse
      
      vv.setGraphMouse(gm);

      
      //JFrame frame = new JFrame("Editing and Mouse Menu Demo");
      
      
      // Let's add a menu for changing mouse modes
      JMenuBar menuBar = new JMenuBar();
      JMenu modeMenu = gm.getModeMenu();
      modeMenu.setText("Mouse Mode");
      modeMenu.setIcon(null); // I'm using this in a main menu
      modeMenu.setPreferredSize(new Dimension(80,20)); // Change the size so I can see the text
      
      menuBar.add(modeMenu);
      frame.setJMenuBar(menuBar);
      gm.setMode(ModalGraphMouse.Mode.EDITING); // Start off in editing mode
      //
      JPanel panel = new JPanel();
      comboBox1 = new JComboBox();
      comboBox1.setEditable(false);
      comboBox1.addActionListener(this);

      comboBox2 = new JComboBox();
      comboBox2.setEditable(false);
      comboBox2.addActionListener(this);
      panel.add(comboBox1);
      panel.add(comboBox2);
      
      
      
      
      //finding paths
      deikstra = new JButton("Find path by Ant");
      //deikstra.addActionListener(new AntListener(g,vv,comboBox1,comboBox2));
      deikstra.addActionListener(this);
      deikstra.setSize(150,50);
      deikstra.setLocation(200, 300);  
      deikstra.setVisible(true);
      //
      
    //finding paths
      deikstraButton = new JButton("Find path by Deikstra");
      //deikstra.addActionListener(new AntListener(g,vv,comboBox1,comboBox2));
      deikstraButton.addActionListener(this);
      deikstraButton.setSize(150,50);
      deikstraButton.setLocation(200, 300);  
      deikstraButton.setVisible(true);
      //
      
    //finding paths
      getVertexes = new JButton("Get vertexes");
      getVertexes.setSize(150,50);
      getVertexes.addActionListener(this);
      getVertexes.setLocation(200, 300);  
      getVertexes.setVisible(true);
      //
      
      panel.add(getVertexes);
      panel.add(deikstra);
      panel.add(deikstraButton);
      frame.add(panel, BorderLayout.NORTH);
      
      //frame.add(deikstra);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(vv);
      frame.setSize(new Dimension(FORM_WIDTH,FORM_HEIGHT));
      frame.pack();
      frame.setVisible(true);    
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == comboBox1){
    		JComboBox cb = (JComboBox) e.getSource();
          	A = new String((String) cb.getSelectedItem());
    	}
    	else if (e.getSource() == comboBox2){
    		JComboBox cb = (JComboBox) e.getSource();
          	Z = new String((String) cb.getSelectedItem());
    	}
    	else if (e.getSource() == getVertexes){
    		ParamsComboBoxModel model = new ParamsComboBoxModel(g);
    		comboBox1.setModel(model);
    		comboBox2.setModel(model);
    	}
    	else if (e.getSource() == deikstra){
    		ArrayList<GraphElement> elements = null;
    		final List<GraphElement> el = elements;
       	Transformer<Edge, Paint> edgePaint = new Transformer<Edge, Paint>() {
    	    public Paint transform(Edge s) {
    	    	if (el.contains(s))
    	    		return Color.GREEN;
    	    	else return Color.BLACK;
    	    }
    	};
    	
    	Transformer<Vertex,Paint> vertexColor = new Transformer<Vertex,Paint>() {
    	  public Paint transform(Vertex vertex) {
    		  if (el.contains(vertex))
    	    		return Color.GREEN;
    	    	else return Color.RED;
    	  }
    	};
    	
    	vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
    	vv.getRenderContext().setVertexDrawPaintTransformer(vertexColor);
        vv.repaint();
    	}
    	
    	else if (e.getSource() == deikstraButton){
    		System.out.println("Deikstra start");
    		//Deikstra alg = new Deikstra(g,A,Z);
    		//System.out.println(alg.solve());
    		System.out.println("Deikstra end");
    	}
    	
    }
    
}

