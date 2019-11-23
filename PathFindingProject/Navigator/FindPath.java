package Navigator;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.*;

import edu.princeton.cs.introcs.StdDraw;

//import edu.princeton.cs.introcs.StdDraw;

public class FindPath {
	
	private static ObstacleMap points;
	private static Polygon2D obstacle;
	private static Polygon2D hull;
	private static boolean v = false;
	private static int delay = 500;
	
	private static boolean IsOnHull(Point2D p) {
		for(int i=0; i<hull.size(); i++) {
			if(hull.getIndex(i).equals(p)) return true; //tests if point is on hull
		}
		return false;
	}
	
	private static boolean Intersects(Point2D p1, Point2D p2) {
		Line2D line1 = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		for(int i=0; i<obstacle.size()-1; i++) {
			Point2D p3 = obstacle.getIndex(i);
			Point2D p4 = obstacle.getIndex(i+1);
			if((!p1.equals(p3) && !p1.equals(p4)) && (!p2.equals(p3) && !p2.equals(p4))) {
				Line2D line2 = new Line2D.Double(p3.getX(), p3.getY(), p4.getX(), p4.getY());
				if(line2.intersectsLine(line1)) return true;
			}
			
		}
		return false;
	}
	
	public static Polygon2D GetRouteToHull(Point2D p) {
		int ind = 0;
		Polygon2D route = new Polygon2D();
		route.addPoint(p);
		List<Point2D> tmp = new ArrayList<Point2D>(Arrays.asList(obstacle.asPointsArray()));
		List<Point2D> intersections = new ArrayList<Point2D>();
		
		while(!IsOnHull(p)){
			Double shortestdistance = Double.POSITIVE_INFINITY;
		for(int i=0; i<tmp.size(); i++) {
				if(p.Distance(tmp.get(i))<shortestdistance) {
					shortestdistance=p.Distance(tmp.get(i));
					ind = i;

				}
			}
		if(!Intersects(p, tmp.get(ind))) {
			route.addPoint(tmp.get(ind));
			p = tmp.get(ind);
			tmp.remove(ind);
			tmp.addAll(intersections);
			
		}
		else {
			intersections.add(tmp.get(ind));
			tmp.remove(ind);
		}
		
		};
		
		return route;
	}
	
	
	public static Polygon2D Route() {
		Polygon2D start = new Polygon2D(GetRouteToHull(points.sourcePoint()));
		Polygon2D end = new Polygon2D(GetRouteToHull(points.destinationPoint()));
		if(v == true) {
			drawRoute(start, Color.ORANGE);
			drawRoute(end, Color.ORANGE);
			resetcanvas();
		}
		Polygon2D route1 = new Polygon2D(start);
		Polygon2D route2 = new Polygon2D(start);
		int i=0;
		while(!hull.getIndex(i).equals(start.getIndex(start.size()-1))) i++; //get to hull point
		i++;
		while(!hull.getIndex(i).equals(end.getIndex(end.size()-1))){
			route1.addPoint(hull.getIndex(i));
			i++;
			if(i==hull.size()) i=0;
		}
		for(i=end.size()-1; i>=0; i--) route1.addPoint(end.getIndex(i)); //add path from hull to dest
		
		i=hull.size()-1;
		while(!hull.getIndex(i).equals(start.getIndex(start.size()-1))) i--; //get to hull point
		while(!hull.getIndex(i).equals(end.getIndex(end.size()-1))){
			route2.addPoint(hull.getIndex(i));
			i--;
			if(i==0) i=hull.size()-1;
		}
		for(i=end.size()-1; i>=0; i--) route2.addPoint(end.getIndex(i)); //add path from hull to dest
		
		if(v==true) {
			drawRoute(route1, Color.GREEN);
			resetcanvas();
			drawRoute(route2, Color.GREEN);
			resetcanvas();
		}
			
			route1 = optimizeRoute(route1);
			route2 = optimizeRoute(route2);
		
		if(v==true) {
			drawRoute(route1, Color.GREEN);
			resetcanvas();
			drawRoute(route2, Color.GREEN);
			resetcanvas();
		}
		
		
		
		if(getPolyDistance(route2)<getPolyDistance(route1)) return route2;
		return route1;
	}
	
	
	public static double getPolyDistance(Polygon2D poly) {
		double distance = 0;
		for(int i=0; i<poly.size()-1; i++) {
			distance+=poly.getIndex(i).Distance(poly.getIndex(i+1));
		}
		return distance;
	}
	
	
	public static void drawRoute(Polygon2D p, Color c) {
		StdDraw.setPenColor(c);
		Point2D ary[] = p.asPointsArray();
		for (int j=0; j<ary.length; j++) {
			if (j < ary.length-1)
				StdDraw.line(ary[j].getX(), ary[j].getY(), ary[j+1].getX(), ary[j+1].getY());
			if(v==true) {
				StdDraw.pause(delay);
			}
		}
	}
	
	
	public static Polygon2D optimizeRoute(Polygon2D poly) {
		Stack<Point2D> finalroutestart = new Stack<Point2D>();
		Stack<Point2D> finalrouteend = new Stack<Point2D>();
		
		finalroutestart.push(poly.getIndex(0));						//sets to source point
		finalrouteend.push(poly.getIndex(poly.size()-1));			//sets to dest point
		int i=1;
	
		while(!IsOnHull(finalroutestart.peek())) {
			if(Intersects(finalroutestart.peek(), poly.getIndex(i))) {	//if it intersects
				finalroutestart.push(poly.getIndex(i-1));				//add previous point
			}
			i++;
		}
		int k=poly.size()-2;											//reverse for destination
		while(!IsOnHull(finalrouteend.peek())) {		
			if(Intersects(finalrouteend.peek(), poly.getIndex(k))) {
				finalrouteend.push(poly.getIndex(k+1));					
			}
			k--;
		}
		
		Point2D ary[] = new Point2D[finalroutestart.size()];
		for(int j=ary.length-1; j>=0; j--) {
			ary[j] = finalroutestart.pop();				//converts stack to polygon
		}
		Polygon2D ply = new Polygon2D(ary);
		i--;
		while(!ply.getIndex(ply.size()-1).equals(finalrouteend.peek())) {
			ply.addPoint(poly.getIndex(i));				//adds hull until it reaches destination path
			i++;
		}
		finalrouteend.pop();
		while(!finalrouteend.empty()) {					//empties destination path into polygon
			ply.addPoint(finalrouteend.pop());
		}		
		return ply;
	}
	
	
	public static void resetcanvas() {
		StdDraw.clear();
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.008);
		StdDraw.point(points.sourcePoint().getX(), points.sourcePoint().getY());
		StdDraw.point(points.destinationPoint().getX(), points.destinationPoint().getY());
		obstacle.drawFilled();
		StdDraw.setPenColor(Color.RED);
		hull.draw();
		StdDraw.setPenRadius(0.006);
	}

	
	public static void main(String[] args) {
		
		
		points = new ObstacleMap(args[0]);
		
		if(args.length == 2) {
			if(args[1].equals("-v")) {
				v = true;
				obstacle = points.shape();
				hull = JarvisMarch.findConvexHull(points.shape());
				resetcanvas();
				Polygon2D route = Route();	
				drawRoute(route, Color.MAGENTA);
				double pathLength = getPolyDistance(route);
				System.out.println("Length of path found = " + pathLength);
				return;
			}
		}
		
		long startTime = 0;
		obstacle = points.shape();
		obstacle.drawFilled();
		startTime = System.currentTimeMillis();
		
		hull = JarvisMarch.findConvexHull(points.shape());
		Polygon2D route = Route();	
		drawRoute(route, Color.MAGENTA);
		
		double pathLength = getPolyDistance(route);
		final long runTime = System.currentTimeMillis() - startTime;
		System.out.println("Execution time (ms) = " + runTime);
		System.out.println("Length of path found = " + pathLength);
	}
}
