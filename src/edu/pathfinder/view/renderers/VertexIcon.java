package edu.pathfinder.view.renderers;

import javax.swing.Icon;

import org.apache.commons.collections15.Transformer;

import edu.pathfinder.graphmodel.impl.GraphElements;
import edu.pathfinder.graphmodel.impl.GraphElements.Vertex;

public class VertexIcon implements Transformer<GraphElements.Vertex, Icon>{
	
	public int getHeight()
    {
        return (20);
    }
    public int getWidth()
    {
        return (20);
    }

	@Override
	public Icon transform(Vertex v) {
		return null;
				//(new ImageIcon(v.getIconName()));
	}

}
