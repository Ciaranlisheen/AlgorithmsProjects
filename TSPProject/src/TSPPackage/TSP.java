// TSP.java
// EE324 Project 2, 2017

package TSPPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;

public class TSP {

	private int N; 						// number of cities
	private City[] cities;				// array of cities
	private double[][] distMatrix;		// distance between each pair of cities
	private int[] shortestTour; 		// solution path to TSP
	private double shortestDistance;	// solution length
	private double longestDistance;		// worst-case tour length


	// Constructor - Setup a TSP problem for a given set of cities
	public TSP(City[] cities) {
		N = cities.length;
		this.cities = cities.clone(); // copy array
		// Construct the distance matrix
		distMatrix = new double[N][N];
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
				distMatrix[i][j] = cities[i].distanceTo(cities[j]);
		shortestTour = new int[N+1]; // length N+1, to include same point as start/end point
		shortestDistance = Double.POSITIVE_INFINITY;
		longestDistance = Double.NEGATIVE_INFINITY;
	}

	
	// Find the optimal TSP tour, returning it as the sequence of city numbers along the tour.
	// City #0 is taken (arbitrarily) as the starting/ending city (first number in returned array
	// is always 0). If animate parameter is true, each solution being considered is drawn to screen.
	public int[] computeOptimalTour(boolean animate) {
		// construct all permutations of {1,2,..,N-1}, between starting/ending city #0
		ArrayList<int[]> perms = Permutation.permute(cities.length - 1);
		// find shortest tours starting/ending with city #0
		for (int[] p : perms) { // for each permutation
			double distance = distMatrix[0][p[0]];  // distance from city #0 to 2nd city
			for (int i=0; i<p.length-1; i++) {
				distance += distMatrix[p[i]][p[i+1]];
			}
			distance += distMatrix[p[p.length-1]][0];  // distance from 2nd last city back to city #0
			// update best tour so far
			if (distance < shortestDistance) {
				shortestDistance = distance;
				shortestTour[0] = 0; // start/end city
				for (int i=1; i<shortestTour.length-1; i++)
					shortestTour[i] = p[i-1];
			}
			// update worst-case tour length
			if (distance > longestDistance)
				longestDistance = distance;

			if (animate) {
				// plot current tour
				int[] tour = new int[N+1];
				tour[0] = 0; // city #0
				for (int i=0; i<N-1; i++)
					tour[i+1] = p[i];
				Drawing.drawTour(cities, tour, shortestDistance, distance);
				StdDraw.text(1.6,1.5, Arrays.toString(tour));
				StdDraw.pause(500); // (milliseconds) - can change this to speed-up/slow-down animation
			}
		}
		return shortestTour;
	}


	
	// Find tour using the Nearest-Neighbour method
	public int[] computeNNTour(int startingCity) {
		
		List<City> path = new ArrayList<City>();								//final path list
		List<City> lis = new ArrayList<City>();									//list of cities
		path.add(cities[startingCity]);											//puts first city in path
		double dist;															//a distance variable
		int[] ary = new int[N+1];												//array for path indexes
		int nn = 0;																	//nearest neighbours value
		double totaldist =0;
		
		for(int i=0; i<N; i++) if(i != startingCity) lis.add(cities[i]);		//adds all cities except starting city to array
		
		for(int j=0; j<N-1; j++) {												//path loop
			dist = Double.POSITIVE_INFINITY;
			if(lis.size()>0) {	
				for(int k=0; k<lis.size(); k++) {								//lis loop
					if(path.get(j).distanceTo(lis.get(k)) <= dist) {
						nn=k;													//tests and sets distance if shorter
						dist = path.get(j).distanceTo(lis.get(k));
					}
				}
				path.add(lis.get(nn));	
				totaldist+=path.get(path.size()-2).distanceTo(path.get(path.size()-1));//updates lists and distance
				lis.remove(nn);
			}
		}
		path.add(cities[startingCity]);											//adds starting city at end
		totaldist+=path.get(N-1).distanceTo(path.get(0));						//adds final distance
		longestDistance= totaldist;

		int k = 0;
		for(int i=0; i<path.size(); i++) {									//gets cities indexes into return array
			for(int j=0; j<cities.length; j++) {
				if(path.get(i).equals(cities[j])) {
					ary[k]=j;
					k++;
				}
			}
		}
		return ary;
	}



	public double tourLength() { return shortestDistance; }


	////////////////////////////
	// TSP APPLICATION CLIENT //
	////////////////////////////
	public static void main(String args[]) {

		// coordinates of some test cities
		City[] cities = new City[8];
		cities[0] = new City(2.2, 1.0);
		cities[1] = new City(3.0, 0.0);
		cities[2] = new City(0.8, -1.0);
		cities[3] = new City(0.0, 0.0);
		cities[4] = new City(1.0, 1.0);
		cities[5] = new City(2.0, -1.0);
		cities[6] = new City(1.2, 0.2);
		cities[7] = new City(1.8, -0.2);
		

		// setup drawing canvas
		   Drawing.setupCanvas();

		// make a TSP problem and solve it
		TSP tsp = new TSP(cities);
		
		// compute optimal tour
		int[] shortest_tour = tsp.computeOptimalTour(false); // set to true to draw each step
		
		
		// compute nearest neighbour tour
		int[] nn = tsp.computeNNTour(7);
		
		
		// print solution path
		//System.out.println(Arrays.toString(shortest_tour));
		//System.out.println("Path Length: " + tsp.shortestDistance);

		// draw the optimal solution path
		//Drawing.drawTour(cities, shortest_tour, tsp.shortestDistance, tsp.shortestDistance);
		//StdDraw.text(1.6,1.5, Arrays.toString(shortest_tour));
		
		// draw the nearest neighbour solution path
		Drawing.drawTour(cities, nn, tsp.shortestDistance, tsp.longestDistance);
		StdDraw.text(1.6,1.5, Arrays.toString(nn));
	}
}
