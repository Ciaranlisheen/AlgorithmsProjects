package Navigator;
// Point2D.java
// C.McArdle, DCU, 2017

import edu.princeton.cs.introcs.StdDraw;

public class Point2D {

	private final double x;
	private final double y;

	public Point2D(double x, double y) { // constructor
		this.x = x;
		this.y = y;
	}

	public Point2D(Point2D p) { // copy constructor
		if (p == null)  System.out.println("Point2D(): null point!");
		x = p.x;
		y = p.y;
	}

	public double getX() { return x; }

	public double getY() { return y; }

  	@Override
	public String toString() { return ("(" + x + "," + y + ")"); }

	@Override
	public boolean equals(Object p) {
		if (p == null) return false;
		return (((Point2D)p).x == x) && (((Point2D)p).y == y);
	}
	
	public double Distance(Point2D p) {//##########################################i added
		return Math.sqrt((p.getX()-x)*(p.getX()-x) + (p.getY()-y)*(p.getY()-y));
	}
	
	public void draw() { StdDraw.point(x, y); }

}
