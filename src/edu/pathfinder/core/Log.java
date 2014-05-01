package edu.pathfinder.core;

import javafx.scene.control.TextArea;

public class Log {
	
	private static Log instance;
	private static TextArea textArea;
	
	private Log(){
		textArea = new TextArea();
	}
	
	public static Log getInstance(){
		if (instance == null){
			instance = new Log();
		}
		return instance;
	}

	public TextArea getTextArea() {
		return textArea;
	}
	
	public void debug(String msg){
		textArea.appendText(msg+"\n");
	}
	
	public void error(String msg){
		textArea.appendText("Error: "+msg+"\n");
	}

	
}
