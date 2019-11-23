// Drawing.java - Utility methods for drawing TSP tours
// EE324 Project 2
// C.McArdle, DCU, 2017
//
// THERE IS NO NEED TO EDIT THIS FILE
////////////////////////////////////////////

package TSPPackage;

import java.awt.Color;
import java.awt.Font;
import edu.princeton.cs.introcs.StdDraw;

public class Drawing {

	// Setup the drawing canvas and drawing styles
	public static void setupCanvas() {
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setXscale(-0.5, 3.5);
		StdDraw.setYscale(-2.2, 1.8);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.0035);
	}

	// Plot a tour using StdDraw
	// cities is array of city coordinates
	// tour contains the ordering of the cities along a tour
	public static void drawTour(City[] cities, int[] tour, double shortestTourLength, double tourLength) {
		StdDraw.clear(Color.LIGHT_GRAY);
		for (int i=0; i<tour.length-1; i++)
			StdDraw.line(cities[tour[i]].getX(), cities[tour[i]].getY(),
					cities[tour[i+1]].getX(), cities[tour[i+1]].getY());
		for (int i=0; i<tour.length; i++)
			drawNode(cities[tour[i]].getX(), cities[tour[i]].getY(), tour[i]);
		// print shortest tour length so far
		StdDraw.setFont(new Font("Arial", Font.PLAIN, 36));
		StdDraw.text(0.5, -1.7, String.format( "Best : %.3f", shortestTourLength));
		if (tourLength > 0)
			StdDraw.text(2.5, -1.7, String.format( "Current : %.3f", tourLength));
	}

	// Plot a labeled node, called by drawTour()
	private static void drawNode(double x, double y, int label) {
		final double circleRad = 0.12;
		StdDraw.setPenColor(new Color(255,250,240));
		StdDraw.filledCircle(x, y, circleRad);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.circle(x, y, circleRad);
		StdDraw.setFont(new Font("Arial", Font.PLAIN, 24));
		StdDraw.text(x, y-0.022, String.valueOf(label));
	}

}
