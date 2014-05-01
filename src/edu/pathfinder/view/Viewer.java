package edu.pathfinder.view;

import edu.pathfinder.params.Path;
import edu.pathfinder.utils.RandomGraphGenerator;
import edu.pathfinder.utils.Tools;
import edu.pathfinder.view.menu.MouseMenus;
import edu.pathfinder.view.menu.PopupVertexEdgeMenuMousePlugin;
import edu.pathfinder.view.models.ParamsComboBoxModel;
import edu.pathfinder.view.renderers.MultiVertexRenderer;
import edu.pathfinder.view.renderers.VertexIcon;
import edu.pathfinder.wf.WeightFunction;
import edu.pathfinder.wf.impl.TestWeightFunction;
import edu.pathfinder.alg.ant.Ant;
import edu.pathfinder.alg.astar.AStar;
import edu.pathfinder.alg.deikstra.Deikstra;
import edu.pathfinder.alg.yen.Yen;
import edu.pathfinder.core.Configuration;
import edu.pathfinder.core.Localization;
import edu.pathfinder.core.Log;
import edu.pathfinder.criter.Strategy;
import edu.pathfinder.criter.impl.SumStrategy;
import edu.pathfinder.graphmodel.Constraint;
import edu.pathfinder.graphmodel.GraphElement;
import edu.pathfinder.graphmodel.impl.GraphElements;
import edu.pathfinder.graphmodel.impl.GraphElements.Edge;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	
	private final static int FORM_HEIGHT = 1000;
	private final static int FORM_WIDTH = 1650;
	private final static int PAINT_HEIGHT = 1000;
	private final static int PAINT_WIDTH = 600;
	private final static int JAVAFX_SCENE_HEIGHT = 1000;
	private final static int JAVAFX_SCENE_WIDTH = 1600;
	private static Localization local;
	private static boolean isRandom = true;
	
	private final static int SDVIG = 600;
	
	public JComboBox comboBox1,comboBox2;
	public JButton getVertexes,antButton,deikstraButton;
	private String A = null;
	private String Z = null;
	Graph<Vertex, Edge> g;
	private static VisualizationViewer<Vertex,Edge> vv;
	//private static BasicVisualizationServer<Vertex,Edge> vv;
	
	public Viewer(){
    	local = Localization.getInstance();
	}
    
    public void doing(){
    	
    	JFrame frame = new JFrame(local.getLocalizedString("APP_NAME"));
    	frame.setPreferredSize(new Dimension(FORM_WIDTH, FORM_HEIGHT));
    	frame.setLayout(null);
    	
//    	if (isRandom){
//    		RandomGraphGenerator r = new RandomGraphGenerator();
//    		g = r.generateGraph(true, 4, 2, 50, 30);
//    		Layout<Vertex, Edge> layout = new CircleLayout(g);
//    		layout.setSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
//            //vv = new BasicVisualizationServer<Vertex,Edge>(layout);
//    		vv = new VisualizationViewer<Vertex,Edge>(layout);
//            //vv.setPreferredSize(new Dimension(350,350));  
//            vv.setSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
//            vv.setLocation(SDVIG+0,50);
//            vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
//            vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
//            vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
//    	}
//    	else{
//    		g = new DirectedSparseMultigraph<Vertex, Edge>();
//    		Layout<Vertex, Edge> layout = new StaticLayout(g);
//  	      layout.setSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
//  	       vv = new VisualizationViewer<Vertex,Edge>(layout);
//  	      //vv.setPreferredSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
//  	      /* Add node renderer mine */
//  	      vv.getRenderer().setVertexRenderer(new MultiVertexRenderer<Vertex, Edge>());
//  	      vv.getRenderContext().setVertexIconTransformer(new VertexIcon());
//  	      /* End add node renderer mine*/
//  	       
//  	      vv.setSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
//  	      vv.setLocation(SDVIG+0,50);
//  	      // Show vertex and edge labels
//  	      vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
//  	      vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
//  	      vv.setBackground(Color.lightGray);
//  	      //vv.setSize(new Dimension(FORM_WIDTH,FORM_HEIGHT));
//  	      // Create a graph mouse and add it to the visualization viewer
//  	      EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(), 
//  	               GraphElements.MyVertexFactory.getInstance(),
//  	              GraphElements.MyEdgeFactory.getInstance()); 
//  	      // Set some defaults for the Edges...
//  	      GraphElements.MyEdgeFactory.setDefaultCapacity(192.0);
//  	      GraphElements.MyEdgeFactory.setDefaultWeight(5.0);
//  	      // Trying out our new popup menu mouse plugin...
//  	      PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
//  	      // Add some popup menus for the edges and vertices to our mouse plugin.
//  	      JPopupMenu edgeMenu = new MouseMenus.EdgeMenu(frame);
//  	      JPopupMenu vertexMenu = new MouseMenus.VertexMenu();
//  	      myPlugin.setEdgePopup(edgeMenu);
//  	      myPlugin.setVertexPopup(vertexMenu);
//  	      gm.remove(gm.getPopupEditingPlugin());  // Removes the existing popup editing plugin
//  	      
//  	      gm.add(myPlugin);   // Add our new plugin to the mouse
//  	      
//  	      vv.setGraphMouse(gm);
//    	}
    	
    	
    	
		Layout<Vertex, Edge> layout = null;
		if (isRandom){
			RandomGraphGenerator r = new RandomGraphGenerator();
			g = r.generateGraph(true, 4, 2, 50, 30);
			layout = new CircleLayout(g);//new StaticLayout(g);
		}
		else{
			g = new DirectedSparseMultigraph<Vertex, Edge>();
			layout = new StaticLayout(g);
		}
	      layout.setSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
	       vv = new VisualizationViewer<Vertex,Edge>(layout);
	      //vv.setPreferredSize(new Dimension(PAINT_HEIGHT,PAINT_WIDTH));
	      /* Add node renderer mine */
	      vv.getRenderer().setVertexRenderer(new MultiVertexRenderer<Vertex, Edge>());
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
    	
    	
     
//     System.out.println(g.toString());
//     System.out.println("************");
//     for (Object ver : g.getVertices()){
//    	 Vertex ver1 = (Vertex) ver;
//    	 System.out.println("Vertex:"+ver1.getName());
//    	 for (Object edg : g.getIncidentEdges(ver1)){
//    		 Edge edg1 = (Edge) edg;
//    		 System.out.print(edg1.getName()+ " ");
//    	 }
//    	 System.out.println();
//     }
//     System.out.println("************");
      

      
      // Let's add a menu for changing mouse modes
      JMenuBar menuBar = new JMenuBar();
      JMenu modeMenu = gm.getModeMenu();
      modeMenu.setLocation(SDVIG, 0);
      modeMenu.setText(local.getLocalizedString("MOUSE_SETTINGS"));
      modeMenu.setIcon(null); // I'm using this in a main menu
      modeMenu.setPreferredSize(new Dimension(200,20)); // Change the size so I can see the text
      
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
      getVertexes = new JButton(local.getLocalizedString("GET_VERTIXES"));
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
    		
    		Deikstra d = new Deikstra();
    		//AStar a = new AStar();
    		
    		List<GraphElement> elements = null;
    		Yen y = new Yen();
    		
    		//Ant antAlg = new Ant();
    		WeightFunction wf = new TestWeightFunction();
    		Strategy st = new SumStrategy();
    		Constraint cn = new Constraint(new ArrayList<GraphElement>(), false, null, null);
    		List<Path> candidats= y.findPath(g, d, wf, st, cn, Tools.getVertexByName(g, A), Tools.getVertexByName(g, Z));
    		for (Path p : candidats){
    			System.out.println("Put:"+p);
    		}
    		long start = Runtime.getRuntime().freeMemory();
    		//System.out.println(a.findPath(g, wf, st, cn, Tools.getVertexByName(g, A), Tools.getVertexByName(g, Z)));
    		//System.out.println(d.findPath(g, wf, st, cn, Tools.getVertexByName(g, A), Tools.getVertexByName(g, Z)));
    		long end = Runtime.getRuntime().freeMemory();
    		long memoTaken = start - end;
    		System.out.println("Memory took:"+memoTaken);
    		System.out.println(Runtime.getRuntime().totalMemory());
    		System.out.println(Runtime.getRuntime().maxMemory());
    		Runtime r = Runtime.getRuntime();
    		System.out.println(1 - r.freeMemory() / r.totalMemory());
    		/*Path path = antAlg.findPath(g, wf, st, cn, Tools.getVertexByName(g, A), Tools.getVertexByName(g, Z));
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
        vv.repaint(); */
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
        /***!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * Settings tab start
         !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        Tab settingsTab = new Tab();
        settingsTab.setClosable(false);
        settingsTab.setText(local.getLocalizedString("SETTINGS_TAB"));
        
        final Button startSearchButton = new Button("Button@Tab A");
        //settingsTab.setContent(tabA_button);
        
        ChoiceBox aPoint= new ChoiceBox();
        
        final ObservableList<String> listOfApoints =
                FXCollections.observableArrayList(
                "Node1",
                "Node2",
                "Node3");
        aPoint.setItems(listOfApoints);
        aPoint.getSelectionModel().selectedIndexProperty().addListener(
        		new ChangeListener<Number>(){
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						startSearchButton.setText(listOfApoints.get((Integer) arg2));
					}
        		}
        );
        
        Label aPointLabel = new Label("A point");
        aPointLabel.setGraphic(aPoint);
        
        ChoiceBox zPoint= new ChoiceBox();
        
        final ObservableList<String> listOfZpoints =
                FXCollections.observableArrayList(
                "Node4",
                "Node5",
                "Node6");
        zPoint.setItems(listOfZpoints);
        zPoint.getSelectionModel().selectedIndexProperty().addListener(
        		new ChangeListener<Number>(){
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						startSearchButton.setText(listOfZpoints.get((Integer) arg2));
					}
        		}
        );
        
        
        Label zPointLabel = new Label("Z point");
        zPointLabel.setGraphic(zPoint);
        
        
        ChoiceBox mode= new ChoiceBox();
        
        final ObservableList<String> listOfModes =
                FXCollections.observableArrayList(
                local.getLocalizedString("BY_HAND_MODE"),
                local.getLocalizedString("FROM_FILE_MODE"),
                local.getLocalizedString("RANDOM_MODE"));
        mode.setItems(listOfModes);
        mode.getSelectionModel().selectedIndexProperty().addListener(
        		new ChangeListener<Number>(){
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						startSearchButton.setText(listOfModes.get((Integer) arg2));
					}
        		}
        );
        mode.setValue(listOfModes.get(0));
        
        Label modeLabel = new Label("modeLable");
        modeLabel.setGraphic(mode);
        
        ChoiceBox graphTypeChoise= new ChoiceBox();
        
        final ObservableList<String> listOfTypes =
                FXCollections.observableArrayList(
                local.getLocalizedString("UNDIRECTED_GRAPH"),
                local.getLocalizedString("DIRECTED_GRAPH"));
        graphTypeChoise.setItems(listOfTypes);
        graphTypeChoise.getSelectionModel().selectedIndexProperty().addListener(
        		new ChangeListener<Number>(){
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						startSearchButton.setText(listOfTypes.get((Integer) arg2));
					}
        		}
        );
        graphTypeChoise.setValue(listOfTypes.get(0));
        
        Label graphTypeChoiseLabel = new Label("graphTypeChoise");
        graphTypeChoiseLabel.setGraphic(graphTypeChoise);
        
        
//        ChoiceBox includes= new ChoiceBox();
//        final ObservableList<String> listOfIncludes =
//                FXCollections.observableArrayList(
//                "Node4",
//                "Node5",
//                "Node6");
//        includes.setItems(listOfIncludes);
//        includes.getSelectionModel().selectedIndexProperty().addListener(
//        		new ChangeListener<Number>(){
//					@Override
//					public void changed(ObservableValue<? extends Number> arg0,
//							Number arg1, Number arg2) {
//						startSearchButton.setText(listOfIncludes.get((Integer) arg2));
//					}
//        		}
//        );
//        
//        
//        Label includesLabel = new Label("includes point");
//        includesLabel.setGraphic(includes);
//        
//        ChoiceBox excludes= new ChoiceBox();
//        
//        final ObservableList<String> listOfExcludes =
//                FXCollections.observableArrayList(
//                "Node1",
//                "Node2",
//                "Node3");
//        excludes.setItems(listOfExcludes);
//        excludes.getSelectionModel().selectedIndexProperty().addListener(
//        		new ChangeListener<Number>(){
//					@Override
//					public void changed(ObservableValue<? extends Number> arg0,
//							Number arg1, Number arg2) {
//						startSearchButton.setText(listOfExcludes.get((Integer) arg2));
//					}
//        		}
//        );
//        
//        Label excludesLabel = new Label("A point");
//        excludesLabel.setGraphic(excludes);
        
        TextField minWeightFiled = new TextField();
        minWeightFiled.setText("min Weight");
        Label minWeightLabel = new Label("Min weight Label");
        minWeightLabel.setGraphic(minWeightFiled);
        
        TextField maxWeightFiled = new TextField();
        maxWeightFiled.setText("max Weight");
        Label maxWeightLabel = new Label("Max weight Label");
        maxWeightLabel.setGraphic(maxWeightFiled);
        //maxWeightLabel.setLabelFor(maxWeightFiled);//(Pos.BOTTOM_LEFT);
        
        CheckBox isWriteHistory = new CheckBox();
        isWriteHistory.setText("Write history");
        isWriteHistory.setSelected(false);
        
        CheckBox isOrderedIncludes = new CheckBox();
        isOrderedIncludes.setText("Is ordered includes");
        isOrderedIncludes.setSelected(false);
        
        isWriteHistory.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                    //icon.setImage(new_val ? image : null);
            }
        });
        
        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");
        ListView<String> listView = new ListView<String>(names);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        GridPane settingsGrid = new GridPane();
        settingsGrid.setHgap(10);
        settingsGrid.setVgap(12);
        settingsGrid.add(aPointLabel, 0, 0); settingsGrid.add(zPointLabel, 2, 0);
        settingsGrid.add(minWeightLabel, 0, 1); settingsGrid.add(maxWeightLabel, 2, 1);
        settingsGrid.add(modeLabel, 0, 2); settingsGrid.add(graphTypeChoiseLabel, 2, 2);
        settingsGrid.add(isWriteHistory, 0, 3); settingsGrid.add(isOrderedIncludes, 2, 3);
        settingsGrid.add(startSearchButton, 1, 4);
        settingsTab.setContent(settingsGrid);
        
        tabPane.getTabs().add(settingsTab);
        /***!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * Settings tab end
         !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        Tab graphicsTab = new Tab();
        graphicsTab.setClosable(false);
        graphicsTab.setText(local.getLocalizedString("GRAPHICS_TAB"));
        //Add something in Tab
        StackPane tabB_stack = new StackPane();
        tabB_stack.setAlignment(Pos.CENTER);
        tabB_stack.getChildren().add(new Label("Label@Tab B"));
        graphicsTab.setContent(tabB_stack);
        
        
        /* Pie start*/
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Used memory");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.LEFT);
        
        chart.setPrefHeight(400);
        chart.setMinHeight(400);
        chart.setMaxHeight(400);

        chart.setPrefWidth(400);
        chart.setMinWidth(400);
        chart.setMaxWidth(400);

        chart.setPrefSize(400, 400);
        chart.setMinSize(400, 400);
        chart.setMaxSize(400, 400);
        
        //((Group) scene.getRoot()).getChildren().add(chart);
        graphicsTab.setContent(chart);
        /* Pie end */
        
        /* Line chart start*/
        //defining the axes
        final NumberAxis xCountOfElements = new NumberAxis();
        final NumberAxis xCountOfIncludes = new NumberAxis();
        final NumberAxis xCountOfExcludes = new NumberAxis();
        final NumberAxis yTime = new NumberAxis();
        xCountOfElements.setLabel("Count of elements");
        xCountOfIncludes.setLabel("Count of includes");
        xCountOfExcludes.setLabel("Count of excludes");
        yTime.setLabel("Work time");
        //creating the chart
        final LineChart<Number,Number> timeToCountOfElementsXY = 
                new LineChart<Number,Number>(xCountOfElements,yTime);
                
        timeToCountOfElementsXY.setTitle("timeToCountOfElementsXY");
        //defining a series
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Dependicy between count of elements and time");
        //populating the series with data
        series1.getData().add(new XYChart.Data(1, 23));
        series1.getData().add(new XYChart.Data(2, 14));
        series1.getData().add(new XYChart.Data(3, 15));
        series1.getData().add(new XYChart.Data(4, 24));
        series1.getData().add(new XYChart.Data(5, 34));
        series1.getData().add(new XYChart.Data(6, 36));
        series1.getData().add(new XYChart.Data(7, 22));
        series1.getData().add(new XYChart.Data(8, 45));
        series1.getData().add(new XYChart.Data(9, 43));
        series1.getData().add(new XYChart.Data(10, 17));
        series1.getData().add(new XYChart.Data(11, 29));
        series1.getData().add(new XYChart.Data(12, 25));
        
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("My portfolio");
        //populating the series with data
        series2.getData().add(new XYChart.Data(1, 203));
        series2.getData().add(new XYChart.Data(2, 1));
        series2.getData().add(new XYChart.Data(3, 135));
        series2.getData().add(new XYChart.Data(4, 20));

        
        timeToCountOfElementsXY.setPrefHeight(400);
        timeToCountOfElementsXY.setMinHeight(400);
        timeToCountOfElementsXY.setMaxHeight(400);

        timeToCountOfElementsXY.setPrefWidth(400);
        timeToCountOfElementsXY.setMinWidth(400);
        timeToCountOfElementsXY.setMaxWidth(400);

        timeToCountOfElementsXY.setPrefSize(400, 400);
        timeToCountOfElementsXY.setMinSize(400, 400);
        timeToCountOfElementsXY.setMaxSize(400, 400);
        
        //lineChart.setsetX(250);
        //lineChart.setScaleY(250);
        
        timeToCountOfElementsXY.getData().add(series1);
        timeToCountOfElementsXY.getData().add(series2);
        
        //graphicsTab.setContent(timeToCountOfElementsXY);
        /* Line chart end */
        
      //creating the chart
        final LineChart<Number,Number> timeToCountOfIncludesXY = 
                new LineChart<Number,Number>(xCountOfIncludes,yTime);
                
        timeToCountOfIncludesXY.setTitle("timeToCountOfIncludesXY");
       
        timeToCountOfIncludesXY.setPrefHeight(400);
        timeToCountOfIncludesXY.setMinHeight(400);
        timeToCountOfIncludesXY.setMaxHeight(400);

        timeToCountOfIncludesXY.setPrefWidth(400);
        timeToCountOfIncludesXY.setMinWidth(400);
        timeToCountOfIncludesXY.setMaxWidth(400);

        timeToCountOfIncludesXY.setPrefSize(400, 400);
        timeToCountOfIncludesXY.setMinSize(400, 400);
        timeToCountOfIncludesXY.setMaxSize(400, 400);
        
        timeToCountOfIncludesXY.getData().add(series1);
        timeToCountOfIncludesXY.getData().add(series2);
        
        //graphicsTab.setContent(timeToCountOfElementsXY);
        
        
        final LineChart<Number,Number> timeToCountOfExcludesXY = 
                new LineChart<Number,Number>(xCountOfExcludes,yTime);
                
        timeToCountOfExcludesXY.setTitle("timeToCountOfExcludesXY");
       
        timeToCountOfExcludesXY.setPrefHeight(400);
        timeToCountOfExcludesXY.setMinHeight(400);
        timeToCountOfExcludesXY.setMaxHeight(400);

        timeToCountOfExcludesXY.setPrefWidth(400);
        timeToCountOfExcludesXY.setMinWidth(400);
        timeToCountOfExcludesXY.setMaxWidth(400);

        timeToCountOfExcludesXY.setPrefSize(400, 400);
        timeToCountOfExcludesXY.setMinSize(400, 400);
        timeToCountOfExcludesXY.setMaxSize(400, 400);
        
        //lineChart.setsetX(250);
        //lineChart.setScaleY(250);
        
        timeToCountOfExcludesXY.getData().add(series1);
        timeToCountOfExcludesXY.getData().add(series2);
        
        GridPane graphicsGrid = new GridPane();
        graphicsGrid.setHgap(10);
        graphicsGrid.setVgap(12);
//        VBox vbButtons1 = new VBox();
//        vbButtons1.setSpacing(10);
//        vbButtons1.setPadding(new Insets(0, 20, 10, 20)); 
//        vbButtons1.getChildren().addAll(chart,lineChart);
        graphicsGrid.add(timeToCountOfElementsXY, 0, 0);
        graphicsGrid.add(timeToCountOfIncludesXY, 0, 1);
        graphicsGrid.add(timeToCountOfExcludesXY, 1, 0);
        graphicsGrid.add(chart, 1, 1);
        graphicsTab.setContent(graphicsGrid);
        
        tabPane.getTabs().add(graphicsTab);
        
        
        
        
        mainPane.setCenter(tabPane);
        
        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        
        /* Tabs end */
        
        /* Text area start*/
        final TextArea textArea = Log.getInstance().getTextArea();//new TextArea("Text Sample");
        textArea.setStyle("-fx-text-fill: black;");
        textArea.setPrefSize(PAINT_HEIGHT, 80);
        textArea.setLayoutX(SDVIG);
        textArea.setLayoutY(650);
        /* Text area end */
        
        
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
            	if (oldValue.intValue()==0 && newValue.intValue() == 1){
            		vv.hide();
            	}
            	else if (oldValue.intValue()==1 && newValue.intValue() == 0){
            		vv.show();
            	}
            	textArea.appendText("oldValue "+oldValue+" newValue "+newValue+"\n");
            	Log.getInstance().error(newValue.toString());
            }
        }); 
        
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

