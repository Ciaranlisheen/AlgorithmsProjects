// City.java - 2D coordinates of a city (or point)
// EE324 Project 2
// C.McArdle, DCU, 2017
//
// THERE IS NO NEED TO EDIT THIS FILE
////////////////////////////////////////////

package TSPPackage;

public class City {

	// Cities coordinates
	private double x;
	private double y;

	// Constructor
	public City(double x, double y) { this.x = x; this.y = y; } // constructor
	
	// Accessor methods
	public double getX() { return x; }
	public double getY() { return y; }
	
	// Distance between this city and another
	public double distanceTo(City c) {
		return Math.sqrt((c.x-x)*(c.x-x) + (c.y-y)*(c.y-y));
	}
	
	@Override
	public String toString() { return "(" + x + "," + y + ")"; }

}
