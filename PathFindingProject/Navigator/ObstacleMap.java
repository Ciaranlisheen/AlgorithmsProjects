package Navigator;
// ObstacleMap.java
// C.McArdle, DCU, 2017

import java.awt.Color;
import edu.princeton.cs.introcs.StdDraw;

public class ObstacleMap {

	private Point2D srcPoint;   // source point on map
	private Point2D destPoint;  // destination point on map
	private Polygon2D shape;    // obstacle shape on map

	// default constructor
	ObstacleMap() {	shape = new Polygon2D(); }
	
	// basic constructor
	ObstacleMap(Point2D src, Point2D dest) {
		this();
		srcPoint = new Point2D(src);
		destPoint = new Point2D(dest);
	}
	
	// basic constructor
	ObstacleMap(Polygon2D poly, Point2D src, Point2D dest) {
		shape = new Polygon2D(poly); // make a copy
		srcPoint = new Point2D(src);
		destPoint = new Point2D(dest);
	}
	
	// constructor, from input file data
	ObstacleMap(String fileName) {
		MapFileReader mapReader = new MapFileReader(fileName);
		srcPoint = mapReader.getSourcePoint();
		destPoint = mapReader.getDestinationPoint();
		shape = mapReader.getFirstPoly();
	}

	public Polygon2D shape() { return new Polygon2D(shape); }
	
	public Point2D sourcePoint() { return new Point2D(srcPoint); }
	
	public Point2D destinationPoint () { return new Point2D(destPoint);	}

	// TEST CLIENT //
	public static void main(String args[]) {
		
		ObstacleMap smf = new ObstacleMap("Test Map Files/TEST-MAP-1A.txt");
		System.out.println("Input polygon points:");
		System.out.println(smf.shape());

		// draw the shape ...
		smf.shape().drawFilled();
		
		//get initial point
		Point2D init = JarvisMarch.getInitialPoint(smf.shape);
		Polygon2D hull = JarvisMarch.findConvexHull(smf.shape);		
		
	}

}
