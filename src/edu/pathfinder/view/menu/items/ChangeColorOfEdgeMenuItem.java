package edu.pathfinder.view.menu.items;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.apache.commons.collections15.Transformer;

import edu.pathfinder.core.Localization;
import edu.pathfinder.view.menu.EdgeMenuListener;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class ChangeColorOfEdgeMenuItem <E> extends JMenuItem implements EdgeMenuListener<E> {

	private E edge;
    private VisualizationViewer visComp;
    
    public ChangeColorOfEdgeMenuItem(){
    	super("Change color");
    	this.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
//                Transformer<E,Paint> vertexColor = new Transformer<E,Paint>() {
//                    public Paint transform(E edge) {
//                        return Color.GREEN;
//                    }
//                };
            	Transformer<E, Paint> edgePaint = new Transformer<E, Paint>() {
            	    public Paint transform(E s) {
            	    	if (s==edge)
            	    		return Color.RED;
            	    	else return Color.BLACK;
            	    }
            	};

            	Transformer<E, Stroke> edgeStroke = new Transformer<E, Stroke>() {
            	    float dash[] = { 10.0f };
            	    public Stroke transform(E s) {
            	        return new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
            	                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
            	    }
            	};


            	visComp.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
                visComp.repaint();
            }
        });
    	
    }
	
	@Override
	public void setEdgeAndView(E edge, VisualizationViewer visComp) {
		this.edge = edge;
        this.visComp = visComp;
        this.setText(Localization.getInstance().getLocalizedString("CHANGE_COLOR")+ " " + edge.toString());
	}


}

