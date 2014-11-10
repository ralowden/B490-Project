package Cluster;

import java.text.DecimalFormat;
import java.util.*;

/* An ABCluster keeps track of all clusters in the sample space
 * Fields:
 * 		Map<NewPoint, Cluster> clusters: A mapping from a center Point to a Cluster of points
 * 		ArrayList<NewPoint> centers: all centers housed within the ABCluster; will be the same as clusters.keyList()
 * 		ArrayList<NewPoint> points: a list of all points within the sample space; mutually exclusive with centers. 
 * 		int k: the number of clusters to be formed
 * 		String abType: which assignment-based algorithm to run
 * 		String abInput: which method of choosing initial clusters to run 
 * Methods:
 * 		void addCluster(NewPoint p): creates a new cluster from p and adds it to this clusters; used for loading input in Driver.java
 * 		void addCenter(NewPoint p): adds p to this centers and removes it from this.points
 * 		double centerDist(NewPoint p): the phi_c function; returns the minimum distance between p and all NewPoints in this.centers
 * 		static boolean isDifferent(ArrayList<NewPoint> c1, ArrayList<NewPoint> c2): determines if c1 and c2 contain the same points 
 * 		double[] gonzalez(NewPoint initCenter): finds "k" clusters from the sample space using initCenter as starting center; returns 3-center and 3-means cost
 * 		double kmeanstt(NewPoint initCenter): finds "k" clusters from sample space using initCenter as starting center; returns 3-means cost
 * 		double lloyd(): finds "k" clusters from sample space; returns 3-means cost
 * 		ABCluster copy() : creates a deep copy of the calling ABCluster
 * 		String toString()
 */
public class ABCluster {
	public static DecimalFormat newFormat = new DecimalFormat("####.##"); //Rounding to 2 decimal places
	
	//Global variables
	public Map<NewPoint, NewCluster> clusters; //Center = point, cluster = list of points 
	public ArrayList<NewPoint> centers;
	public ArrayList<NewPoint> points;
	public int k;
	public String abType; //which assignment-based algorithm to run
	public String abInput; //which method of choosing initial clusters to run 
	
	//Constructor that takes a predefined arrayList of clusters and 
	//arbitrary k and d values 
	public ABCluster(Map<NewPoint, NewCluster> clusters, ArrayList<NewPoint> centers, ArrayList<NewPoint> points, 
			         String abType, String abInput, int k) {
		this.clusters = clusters;
		this.centers = centers;
		this.points = points;
		this.k = k;
		this.abType = abType;
		this.abInput = abInput;
	}
	
	//Empty constructor; defaults to:
	// k = 3
	// abType = g
	// abInput = null;
	public ABCluster() {
		this(new HashMap<NewPoint, NewCluster>(), new ArrayList<NewPoint>(), new ArrayList<NewPoint>(), "g", null, 3);
	}
	
	//Constructor that takes just d and k
	public ABCluster(String abType, String abInput, int k) {
		this(new HashMap<NewPoint, NewCluster>(), new ArrayList<NewPoint>(), new ArrayList<NewPoint>(), abType, abInput, k);
	}

	//Creates a new cluster and makes p the center 
	public void addCluster(NewPoint p) {
		this.clusters.put(p, new NewCluster());
	}
	
	//Adds p to list of centers
	public void addCenter(NewPoint p) {
		this.centers.add(p);
		if(points.contains(p))
		this.points.remove(p);
	}
    
    public NewPoint getPoint(int i){
	System.out.println(this.points.get(i));
	return this.points.get(i);
    }
	
	/* Same function as phi_c; finds the minimum distance between p and this.clusters
	 * Input:
	 * 		NewPoint p: the point whose distance to this.clusters will be calculated
	 * Returns the distance from p to this.clusters
	 */
	public double centerDist(NewPoint p) {
		return NewPoint.distanceTo(findClosestCenter(p), p);
	}
	
	/* The argmin of phi_c; finds teh center who is closest to p
	 * Input:
	 * 		NewPoint p: the point whose closest center needs to be found
	 * Returns the closest center to p from this.centers
	 */
	public NewPoint findClosestCenter(NewPoint p) {
		double minDist = Double.MAX_VALUE;
		NewPoint closest = null;
		
		//Find closest center
		for(NewPoint center : this.centers) {
			double d = NewPoint.distanceTo(center, p);
			if(d < minDist) {
				closest = center;
				minDist = d;
			}
		}
		return closest;
	}
	
	/* Determines if c1 and c2 contain different points based on the points' fields
	 * Input:
	 * 		ArrayList<NewPoint> c1, c2: the list of points to be deemed the same or different
	 * Returns true if the same point (as determined by x- and y-coordinate and name) is contained in both c1 and c2 
	 * 
	 */
	public static boolean isDifferent(ArrayList<NewPoint> c1, ArrayList<NewPoint> c2) {
		boolean giveUp = true;  
		int numSame = 0;
		if(c1.size() == c2.size()) {
			for(NewPoint p1 : c1) {
				giveUp = true; 
				for(NewPoint p2 : c2) {
					if(p1.equals(p2)) {
						giveUp = false;
						break;
					} 
				} if(giveUp) return giveUp;
			} return giveUp;
		}
		return true;
	}
	
	/* The gonzalez algorithm
	 * Input:
	 * 		NewPoint initCenter: the initial center point to kick-off the algorithm; currently is set to "a" in Driver.java
	 * Returns an array of doubles; 
	 * 		index 0: 3-center cost
	 * 		index 1: 3-means cost 
	 */
	public double[] gonzalez(NewPoint initCenter) {
		this.addCenter(initCenter);
		this.addCluster(initCenter);

		//Finding k centers
		while(this.centers.size() < 3) {
			NewPoint newCenter = null;
			double maxDist = Double.MIN_VALUE;

			for(NewPoint p : this.points) {
				double d = this.centerDist(p);
				if(d > maxDist && ! this.centers.contains(p)) {
					newCenter = p;
					maxDist = d;
				}
			}
			this.addCenter(newCenter);
			this.addCluster(newCenter);
		}
		double meanCost = 0.0;
		double maxDist = Double.MIN_VALUE;
		double dist;
		//Assigning points to centers
		for(NewPoint p : this.points) {
			NewPoint closest = findClosestCenter(p);
			dist = NewPoint.distanceTo(closest, p);
			meanCost += Math.pow(dist, 2);
			if(dist > maxDist) {
				maxDist = dist;
			}
			this.clusters.get(closest).addPoint(p);
		}
		double[] costs = {maxDist, meanCost};
		return costs;
	}
	
	/* The kmeanstt algorithm
	 * Input:
	 * 		NewPoint initCenter: the initial center point to kick-off the algorithm; currently is set to "a" in Driver.java
	 * Returns 3-means cost 
	 */
	public double kmeanstt(NewPoint initCenter) {
		this.addCenter(initCenter);
		this.addCluster(initCenter);

		Random rand = new Random();

		//Finding k centers
		while(this.centers.size() < 3) {
			//Must be reset every time finding new center
			NewPoint newCenter = null;
			double totalProb = 0.0, cumulativeProb = 0.0;
			Map<NewPoint, Double> probabilities = new HashMap<NewPoint, Double>();
			
			//Building cumulative distribution function up to query point randDbl 
			for(NewPoint p : this.points) {
				NewPoint closestCenter = findClosestCenter(p);
				double d = Math.pow(NewPoint.distanceTo(closestCenter, p), 2); //Distance to closest center squared
				probabilities.put(p, d);  
				totalProb += d; //Used for normalizing at end
			}
			Double randDbl = rand.nextDouble(); 
			//Finding new center based on cumulative density 
			for(NewPoint key : probabilities.keySet()) {
				Double value = probabilities.get(key);
				cumulativeProb += value/totalProb; //Normalizing 
				if(cumulativeProb > randDbl) {
					newCenter = key;
					break;
				}
			}
			this.addCenter(newCenter);
			this.addCluster(newCenter);
		}
		double cost = 0.0;
		//Assigning points to centers
		for(NewPoint p : this.points) {
			NewPoint closest = findClosestCenter(p);
			cost += Math.pow(NewPoint.distanceTo(closest, p), 2);
			this.clusters.get(closest).addPoint(p);
		}	
		return cost;
	}
	
	/* The kmeanstt algorithm
	 * Returns 3-means cost 
	 * 
	 * NOTE: the initial centers are filled in in Driver.java as determined by user input 
	 */
	public double lloyd() {
		ArrayList<NewPoint> tempCenters = this.centers;
		this.centers = new ArrayList<NewPoint>();
		
		while(isDifferent(this.centers, tempCenters)) {
			//Reset clusters & centers
			this.clusters = new HashMap<NewPoint,NewCluster>();
			this.centers = tempCenters;
			tempCenters = new ArrayList<NewPoint>();
			for(NewPoint p : this.centers) {
				this.clusters.put(p, new NewCluster());
			}
			
			//Assigning points to clusters
			for(NewPoint p : this.points) {
				NewPoint closest = findClosestCenter(p);
				this.clusters.get(closest).addPoint(p);
			}
			//Finding new centers
			for(NewPoint p : this.clusters.keySet()) {
				NewCluster c = this.clusters.get(p);
				if(c.points.size() == 0) tempCenters.add(p);
				else tempCenters.add(NewCluster.average(c.points));
			}
		}
		
		//Calculating cost
		double cost = 0.0;
		for(NewPoint p : this.points) {
			NewPoint closest = findClosestCenter(p);
			cost += Math.pow(NewPoint.distanceTo(closest, p), 2);
		}	
		return Double.valueOf(newFormat.format(cost));
	}
	
	//Debugging method: prints each cluster using Cluster's printName function
	public void printClusters() {
		System.out.print("{");
		for(NewCluster c : this.clusters.values()) {
			c.printName();
		}
		System.out.println("}");
	}
	
	// Creates a deep copy of this ABCluster 
	public ABCluster copy(){
		Map<NewPoint, NewCluster> clusterCopy = new HashMap<NewPoint,NewCluster>();
		ArrayList<NewPoint> centersCopy = new ArrayList<NewPoint>();
		ArrayList<NewPoint> pointsCopy = new ArrayList<NewPoint>();
		for(NewPoint key : this.clusters.keySet()) {
			clusterCopy.put(key, this.clusters.get(key));
		}
		for(NewPoint p : this.centers) {
			centersCopy.add(p);
		}
		for(NewPoint p : this.points) {
			pointsCopy.add(p);
		}
		return new ABCluster(clusterCopy, centersCopy, pointsCopy, this.abType, this.abInput, this.k);
	}

	//Overrides Object's toString method
	public String toString() {  
		return "{\n      Points: " + this.points + "\n      " + this.clusters.toString() + "\n    }";
	}
}