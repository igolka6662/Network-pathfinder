package edu.pathfinder;

import edu.pathfinder.core.Configuration;
import edu.pathfinder.core.Log;
import edu.pathfinder.view.Viewer;

public class Runner {
	
	public static void main(String args[]){
		Configuration.getInstance(); //to init some properties
		Log.getInstance().debug("Welcome to network pathfinder");
		Viewer view = new Viewer();
		view.doing();
	}

}
