package edu.pathfinder.view;

import edu.pathfinder.params.Path;
import edu.pathfinder.utils.Tools;
import edu.pathfinder.view.menu.MouseMenus;
import edu.pathfinder.view.menu.PopupVertexEdgeMenuMousePlugin;
import edu.pathfinder.view.models.ParamsComboBoxModel;
import edu.pathfinder.view.renderers.MultiVertexRenderer;
import edu.pathfinder.view.renderers.VertexIcon;
import edu.pathfinder.wf.WeightFunction;
import edu.pathfinder.wf.impl.TestWeightFunction;
import edu.pathfinder.alg.ant.Ant;
import edu.pathfinder.core.Configuration;
import edu.pathfinder.criter.Strategy;
import edu.pathfinder.criter.impl.SumStrategy;
import edu.pathfinder.graphmodel.Constraint;
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

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.collections15.Transformer;

/**
 * Illustrates the use of custom edge and vertex classes in a graph editing application.
 * Demonstrates a new graph mouse plugin for bringing up popup menus for vertices and
 * edges.
 */
public class Viewer implements  ActionListener{
	
	private final static int FORM_HEIGHT = 800;
	private final static int FORM_WIDTH = 1650;
	private final static int PAINT_HEIGHT = 1000;
	private final static int PAINT_WIDTH = 600;
	private final static int JAVAFX_SCENE_HEIGHT = 1000;
	private final static int JAVAFX_SCENE_WIDTH = 1600;
	
	private final static int SDVIG = 600;
	
	public JComboBox comboBox1,comboBox2;
	public JButton getVertexes,antButton,deikstraButton;
	private String A = null;
	private String Z = null;
	Graph<GraphElements.Vertex, GraphElements.Edge> g;
	VisualizationViewer<GraphElements.Vertex,GraphElements.Edge> vv;
	
    
    public void doing(){
    	Configuration conf = new Configuration();
    	System.out.println(conf.getApplicationLanguage());
    	System.out.println(new File(".").getAbsolutePath()); // current directory
    	JFrame frame = new JFrame("Editing and Mouse Menu Demo");
    	frame.setPreferredSize(new Dimension(FORM_WIDTH, FORM_HEIGHT));
    	frame.setLayout(null);
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
      vv.setLocation(SDVIG+0,50);
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
      modeMenu.setLocation(SDVIG, 0);
      modeMenu.setText("Mouse settings");
      modeMenu.setIcon(null); // I'm using this in a main menu
      modeMenu.setPreferredSize(new Dimension(100,20)); // Change the size so I can see the text
      
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
      antButton = new JButton("Find path by Ant");
      //deikstra.addActionListener(new AntListener(g,vv,comboBox1,comboBox2));
      antButton.addActionListener(this);
      antButton.setSize(150,50);
      antButton.setLocation(SDVIG+200, 300);  
      antButton.setVisible(true);
      //
      
    //finding paths
      deikstraButton = new JButton("Find path by Deikstra");
      //deikstra.addActionListener(new AntListener(g,vv,comboBox1,comboBox2));
      deikstraButton.addActionListener(this);
      deikstraButton.setSize(150,50);
      deikstraButton.setLocation(SDVIG+200, 300);  
      deikstraButton.setVisible(true);
      //
      
    //finding paths
      getVertexes = new JButton("Get vertexes");
      getVertexes.setSize(150,50);
      getVertexes.addActionListener(this);
      getVertexes.setLocation(SDVIG+200, 300);  
      getVertexes.setVisible(true);
      //
      
      panel.add(getVertexes);
      panel.add(antButton);
      panel.add(deikstraButton);
      panel.setSize(PAINT_HEIGHT, 50);
      panel.setLocation(SDVIG+0,0);
      //frame.add(panel, BorderLayout.NORTH);
      frame.add(panel);
      
      //frame.add(deikstra);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(vv);
      frame.setSize(new Dimension(FORM_WIDTH,FORM_HEIGHT));
      frame.pack();
      frame.setVisible(true);
      
      /* JAVA FX start*/
      final JFXPanel fxPanel = new JFXPanel();
      fxPanel.setSize(JAVAFX_SCENE_WIDTH, JAVAFX_SCENE_HEIGHT);
      //fxPanel.setLocation(PAINT_WIDTH, PAINT_HEIGHT);
      //fxPanel.setLocation(PAINT_HEIGHT, PAINT_WIDTH);
      frame.add(fxPanel);
      //frame sets position on monitor
      
      Platform.runLater(new Runnable() {
          @Override
          public void run() {
              initFX(fxPanel);
          }
     });
      
      /* JAVA FX end*/
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
    	else if (e.getSource() == antButton){
    		List<GraphElement> elements = null;
    		
    		Ant antAlg = new Ant();
    		WeightFunction wf = new TestWeightFunction();
    		Strategy st = new SumStrategy();
    		Constraint cn = new Constraint(new ArrayList<GraphElement>(), false, null, null);
    		Path path = antAlg.findPath(g, wf, st, cn, Tools.getVertexByName(g, A), Tools.getVertexByName(g, Z));
    		elements = path.getElements();
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
    
    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }
    
    private static Scene createScene() {
        Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root, Color.BITMASK, 900);
        /* Tabs start */ 
        TabPane tabPane = new TabPane();
        
        BorderPane mainPane = new BorderPane();
        
        
        //Create Tabs
        Tab tabA = new Tab();
        tabA.setText("Tab A");
        //Add something in Tab
        Button tabA_button = new Button("Button@Tab A");
        tabA.setContent(tabA_button);
        tabPane.getTabs().add(tabA);
      
        Tab tabB = new Tab();
        tabB.setText("Tab B");
        //Add something in Tab
        StackPane tabB_stack = new StackPane();
        tabB_stack.setAlignment(Pos.CENTER);
        tabB_stack.getChildren().add(new Label("Label@Tab B"));
        tabB.setContent(tabB_stack);
        tabPane.getTabs().add(tabB);
        
        
        mainPane.setCenter(tabPane);
        
        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        
        /* Tabs end */
        
        /* Text area start*/
        final TextArea textArea = new TextArea("Text Sample");
        textArea.setStyle("-fx-text-fill: black;");
        textArea.setPrefSize(PAINT_HEIGHT, 80);
        textArea.setLayoutX(SDVIG);
        textArea.setLayoutY(650);
        /* Text area end */
        
        
        Text  text  =  new  Text();
        
        text.setX(650);
        text.setY(100);
        text.setFont(new Font(25));
        text.setText("Welcome JavaFX!");

        root.getChildren().add(text);
        root.getChildren().add(mainPane);
        root.getChildren().add(textArea);

        return (scene);
    }
    
}

