package Navigator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;

public class JarvisMarch {

	
	public static Polygon2D findConvexHull(Polygon2D polygon) {
		
		Point2D init = getInitialPoint(polygon);
		Point2D candidate = polygon.getIndex(0);
		List<Point2D> hull = new ArrayList<Point2D>();
		hull.add(init);
		
		do {
			for(int x=0; x<polygon.size(); x++) {
				if(isCounterClockwise(hull.get(hull.size()-1), candidate, polygon.getIndex(x))) {
					candidate = polygon.getIndex(x);
				}
			}
			hull.add(candidate);
		}while (candidate.equals(init) != true);
		
		Polygon2D hullpoly = new Polygon2D();
		for(int j=0; j<hull.size(); j++) {
			hullpoly.addPoint(hull.get(j));
		}
		return hullpoly;	
	}
	
	public static Point2D getInitialPoint(Polygon2D polygon) {
		Point2D ary[] = polygon.asPointsArray();
		List<Point2D> initlis = new ArrayList<Point2D>();
		Point2D init = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		initlis.add(init);
		
		for(int i=0; i<polygon.size(); i++) {
			if(ary[i].getY()<initlis.get(0).getY()) {
				initlis = new ArrayList<Point2D>();
				initlis.add(ary[i]);
			}
			if(ary[i].getY()==initlis.get(0).getY()) {
				initlis.add(ary[i]);
			}
			while(initlis.size()>1) {
				if(initlis.get(0).getX()<initlis.get(1).getX()) {
					initlis.remove(1);
				}
				else {
					initlis.remove(0);
					
				}
			}
			
		}
		init = initlis.get(0);
		return init;
	}
	
	public static boolean isCounterClockwise(Point2D p1, Point2D p2, Point2D p3) {
		
		double x0 = p1.getX();
		double y0 = p1.getY();
		double x1 = p2.getX();
		double y1 = p2.getY();
		double x2 = p3.getX();
		double y2 = p3.getY();
		
		double k = (x1-x0)*(y2-y0)-(y1-y0)*(x2-x0);
		
		if(k>0) return true;
		else if(k<0) return false;
		
		
		//following runs if colinear or is the same point
		double dist1 = (x1 - x0)*(x1 - x0) + (y1-y0)*(y1-y0);
		double dist2 = (x2 - x0)*(x2 - x0) + (y2-y0)*(y2-y0);
	
		if(dist2>dist1) return true;
		return false;
	}
	
	public static void main(String args[]) {
		
		
		
	}
	
}
