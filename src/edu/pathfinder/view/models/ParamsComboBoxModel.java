package edu.pathfinder.view.models;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;
import edu.uci.ics.jung.graph.Graph;

public class ParamsComboBoxModel extends AbstractListModel implements ComboBoxModel {
  private String[] nodes;

  String selection = null;
  
  public ParamsComboBoxModel(Graph g){
	  nodes = new String[g.getVertexCount()];
	  int iter = 0;
	  for (Object ob:g.getVertices()){
		  Vertex v = (Vertex) ob;
		  nodes[iter] = v.getName();
		  iter++;
	  }
  }

  public Object getElementAt(int index) {
    return nodes[index];
  }

  public int getSize() {
    return nodes.length;
  }

  public void setSelectedItem(Object anItem) {
    selection = (String) anItem; // to select and register an
  } // item from the pull-down list

  // Methods implemented from the interface ComboBoxModel
  public Object getSelectedItem() {
    return selection; // to add the selection to the combo box
  }
}