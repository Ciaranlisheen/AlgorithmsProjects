// PrinterPath.java
// EE324 Project 2, 2017

package TSPPackage;

import java.awt.Color;
import java.awt.Font;

import edu.princeton.cs.introcs.StdDraw;

public class PrinterPath {

	public static City[] points;  	// the set of points (cities) to be visited by the printer,
	static City start = new City(1,1);
	static City fin = new City(0,0);						//Sets up Starting and Finishing point
								
	
	public static void main(String args[]) {
		loadPointsArray(); 								// Load the points array
		TSP tsp = new TSP(points);						//creates a TSP object with the points not including (1,1) and (0,0).
		findNNpath(tsp);								//finds the shortest route starting at (1,1)		
	}
	
	
	

	private static City[] findNNpath(TSP t) { 			//Finds the best NN path
		
		int bestNN = 0;
		double prevdist = Double.POSITIVE_INFINITY;
		boolean c = false;
		City tmp;
		
		City[] path = new City[points.length+2];		//needs two extra elements for start and end
		City thisstart = start;							//This start and fin points so i can flip orientation
		City thisfin = fin;								
		
		
		for(int b=0; b<=1; b++) {						//runs through entirety once in one direction then flips starting point
			if(b==1) {
				tmp = start;
				thisstart = fin;
				thisfin = tmp;
			}
				
			for(int i=0; i<points.length; i++) {
				path=indivNN(t, i, thisstart, thisfin);	//goes through all possible NN paths
	
				if(getdistance(path)<prevdist) {		//compares distance to previous shortest distance each time
					if(b==1) c=true;					//if a shorter distance is found going backwards sets this through
					prevdist=getdistance(path);	
					bestNN = i;						//stores index of shortest NN path
				};
			}	
		}
		
		if(c==false) {									//resets start and finish point if found going first direction
			tmp = start;
			thisstart = fin;
			thisfin = tmp;
		}
		
		path=indivNN(t, bestNN, thisstart, thisfin);	//resets path to shortest using best nn index
		if(c==true) reverse(path);						//flips path if it was found backwards
		System.out.println(getdistance(path));			//prints the distance
		setupCanvas();
		drawpath(path);									//draws path
		
		return path;
	}
	
		
	private static City[] indivNN(TSP t, int ind, City start, City fin) {	//calculates the nn path and puts it in array with the start and end point
		
		int[] tmppath = new int[points.length+1];		//needs extra element as nn adds first element again to end
		tmppath = t.computeNNTour(ind);
		City[] path = new City[points.length+2];
		path[0]=start;							//adds starting city to path
		path[path.length-1]=fin;				//adds finishing point
			for(int j=1; j<path.length-1; j++) {		
				path[j]=points[tmppath[j-1]];			//adds path through points
			}
		
		return path;
	}
	
	
	private static double getdistance(City[] c) {//gets distance of route
		double dist=0;
		for(int j=0; j<c.length-1; j++) {
			dist+=c[j].distanceTo(c[j+1]);		//adds distant from on point to the next through all points		
		}	
		return dist;
	}
	
	
	private static void drawpath(City[] c) {	// Draws and labels route
		int[] indexes = new int[c.length];
		indexes[0] = -1;
		indexes[c.length-1] = -1;
		
		for(int j=1; j<c.length; j++) {	
			for(int i=0; i<points.length; i++) {		//finds the original index of the points
				if(c[j]==points[i]) indexes[j]=i;
			}
			drawEdge(c[j-1], c[j], indexes[j-1], indexes[j]);		
		}
	}

	
	private static void reverse(City[] c) {		//for reversing path if shortest path is found starting at (0,0)
		City tmp = c[0];
		for(int i=0; i<c.length/2; i++) {
			tmp = c[i];
			c[i]=c[c.length-i-1];
			c[c.length-i-1] = tmp;
		}
	}

	
	
	
	
	
	
	
	//=====================================================================
	// HELPER METHODS - *** DO NOT EDIT BELOW THIS LINE ***
	//=====================================================================

	//=====================================================================
	// setupCanvas() - call this before drawing. Call again to clear drawing
	//=====================================================================
	private static void setupCanvas() {
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setXscale(-0.01, 1.01);
		StdDraw.setYscale(-0.01, 1.01);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.0035);
		StdDraw.clear(Color.LIGHT_GRAY);
	}

	//=====================================================================
	// drawEdge(..) - draw an edge (line segment) between two points
	//=====================================================================
	private static void drawEdge(City p1, City p2, int i, int j) {
		StdDraw.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		final double circleRad = 0.005;
		StdDraw.setPenColor(new Color(255,250,240));
		StdDraw.filledCircle(p1.getX(), p1.getY(), circleRad);
		StdDraw.filledCircle(p2.getX(), p2.getY(), circleRad);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.circle(p1.getX(), p1.getY(), circleRad);
		StdDraw.circle(p2.getX(), p2.getY(), circleRad);
		/////////////////// OPTIONAL CODE TO NUMBER THE POINTS
		if (i >= 0) {
			StdDraw.setFont(new Font("Arial", Font.PLAIN, 24));
			StdDraw.text(p1.getX()-0.01, p1.getY()-0.02, String.valueOf(i));
			StdDraw.text(p2.getX()-0.01, p2.getY()-0.02, String.valueOf(j));
		}
	}

	//=====================================================================
	// loadPointsArray() - calls this to load the points[] array 
	//=====================================================================
	private static void loadPointsArray() {
		// Coordinates of points to be visited - DO NOT EDIT!
		points = new City[20];
		points[0]   = new City(0.16042780758244723,0.6772862039426466);
		points[1]   = new City(0.30028794882086696,0.6937403454880247);
		points[2]   = new City(0.16042780758244723,0.6073561390245033);
		points[3]   = new City(0.3578774197965479,0.7431027408043881);
		points[4]   = new City(0.35376388848239376,0.5579937306771306);
		points[5]   = new City(0.1851090068695051,0.5045177910156039);
		points[6]   = new City(0.4031262805410047,0.4839501344448332);
		points[7]   = new City(0.46071575151668565,0.7636704087772917);
		points[8]   = new City(0.09049773777767549,0.3646576611793173);
		points[9]   = new City(0.35376388848239376,0.3628847238076255);
		points[10]  = new City(0.5553270206085152,0.5250854638751361);
		points[11]  = new City(0.6705059625598772,0.4510418676428388);
		points[12]  = new City(0.29617441750671286,0.2618193131704178);
		points[13]  = new City(0.37433154505316424,0.1672080440785882);
		points[14]  = new City(0.1851090068695051,0.2289110463684233);
		points[15]  = new City(0.4800082247822017,0.1536621856239663);
		points[16]  = new City(0.7939119671395471,0.4757230555277634);
		points[17]  = new City(0.8103661086849254,0.7965786772081625);
		points[18]  = new City(0.809090909090909,0.6004042597014496);
		points[19]  = new City(0.674206170156438,0.6669839253814806);
	}
}
