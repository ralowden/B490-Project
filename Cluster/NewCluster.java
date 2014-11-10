package Cluster;

import java.text.DecimalFormat;
import java.util.*;

/*
 * This is the CLUSTER class
 * Fields:
 * 		ArrayList<NewPoint> points: all of the points assigned to this cluster
 * 		NewPoint midpoint: the center of the cluster (may not be set, depending on which clustering algorithm is used)
 * Methods:
 * 		void merge(Cluster other): puts all of other's points into this arrayList of points
 * 		void addNewPoint(NewPoint p): adds p to this arrayList of points
 * 		static double singleLink(Cluster c1, Cluster c2): returns the distance between the two closest points in c1 and c2
 * 		static double completeLink(Cluster c1, Cluster c2): returns the distance between the two farthest points in c1 and c2
 * 		static double meanLink(Cluster c1, Cluster c2): returns the distance between the "mean" points of c1 and c2
 * 		static NewPoint average(ArrayList<NewPoint> list): returns the midpoint of a list of NewPoints
 * 		String toString(): converts a Cluster to a String
 */

public class NewCluster {
    //Global variables
    public static DecimalFormat newFormat = new DecimalFormat("####.##"); //Rounding to 2 decimal places
    public ArrayList<NewPoint> points; //ArrayList of points within the cluster
    private NewPoint midpoint;
    
    //Empty constructor for a cluster
    public NewCluster() {
	this.points = new ArrayList<NewPoint>();
	this.midpoint = new NewPoint();
    }
    
    //Constructor for a cluster with a predefined ArrayList of points
    public NewCluster(ArrayList<NewPoint> points) {
	this.points = points;
	this.midpoint = average(points);
    }
    
    //Constructor for a cluster with just 1 point 
    public NewCluster(NewPoint p) {
	this.points = new ArrayList<NewPoint>();
	this.midpoint = NewPoint.deepCopy(p);
	this.addPoint(p);
    }
    
    /* Combines input cluster with calling cluster
     * Input:
     * 		Cluster other: the cluster to be merged with this cluster
     */
    public void merge(NewCluster other) {
	this.points.addAll(other.points);
    }
    
    /* Adds a point to the cluster
     * Input:
     * 		NewPoint p: the point to be added to the cluster
     */
    public void addPoint(NewPoint p) {
    if(this.points.isEmpty()) {
    	this.midpoint = NewPoint.deepCopy(p);
    } else {
    	int numCoords = this.midpoint.coords.size();
    	for(int i = 0; i < numCoords; i++) {
    		Double newCoord = (this.midpoint.coords.get(i) * this.points.size() + p.coords.get(i))/(this.points.size() + 1);
    		this.midpoint.coords.set(i, newCoord);
    	}
    }
	this.points.add(p);
    }
    
    /* Calculates the distance between two clusters using the shortest link
     * Input:
     * 		Cluster c1, c2: clusters whose distance is to be calculated
     * Returns the minimum distance between c1 and c2
     */
    public static double singleLink(NewCluster c1, NewCluster c2) {
	double min = Integer.MAX_VALUE; 
	double dist;
	for(NewPoint p1 : c1.points) {
	    for(NewPoint p2 : c2.points) {
		dist = NewPoint.distanceTo(p1, p2);
		if(dist < min) min = dist;
	    }
	}
	return min;
    }
    
    /* Calculates the distance between two clusters using the longest link
     * Input:
     * 		Cluster c1, c2: clusters whose distance is to be calculated
     * Returns the maximum distance between c1 and c2
     */
    public static double completeLink(NewCluster c1, NewCluster c2) {
	double max = Integer.MIN_VALUE; 
	double dist;
	for(NewPoint p1 : c1.points) {
	    for(NewPoint p2 : c2.points) {
		dist = NewPoint.distanceTo(p1, p2);
		if(dist > max) max = dist;
	    }
	}
	return max;
    }
    
    /* Calculates the distance between two clusters using each cluster's midpoint
     * Input: 
     * 		Cluster c1, c2: clusters whose distance is to be calculated
     * Returns the mean distance between c1 and c2
     */
    public static double meanLink(NewCluster c1, NewCluster c2) {
	return NewPoint.distanceTo(c1.midpoint, c2.midpoint);
    }
    
    /* Computes the mean point of a list of points. Used for finding a cluster's "midpoint"
     * Input:
     * 		ArrayList<NewPoint> list: the list of points whose midpoint is to be calculated
     * Returns the midpoint of list
     */
    public static NewPoint average(ArrayList<NewPoint> list) {
	NewPoint np = new NewPoint();
	int numCoords = list.get(0).coords.size();
	System.out.println("Num coords: " + numCoords);
	System.out.println("List size: " + list.size());
	for(int i = 0; i < numCoords; i++) {
	    double newCoord = 0;
	    for(int j = 0; j < list.size(); j++) {
		newCoord += list.get(j).coords.get(i);
	    }
	    np.coords.add(newCoord/numCoords);
	}
	return np;
    }
    
    //Overrides Object's toString method
    public String toString() {
	return points.toString();
    }
    
    public String toStringName() {
	String str = "[";
	for(NewPoint p : this.points) {
	    str += p.name + ", ";
	} return str += "]";
	
    }
    
    //A debugging function: used to print only the names of the points in a cluster
    public void printName() {
	System.out.print("[");
	for(NewPoint p : this.points) {
	    System.out.print(p.name + ", ");
	}
	System.out.println("]");
    }

    public static void main(String args[]) {
    	NewCluster c1 = new NewCluster();
    	ArrayList<Double> p1 = new ArrayList<Double>();
    	p1.add(1.0);
    	p1.add(2.0);
    	p1.add(3.0);
    	ArrayList<Double> p2 = new ArrayList<Double>();
    	p2.add(4.0);
    	p2.add(5.0);
    	p2.add(6.0);
    	ArrayList<Double> p3 = new ArrayList<Double>();
    	p3.add(1.0);
    	p3.add(1.0);
    	p3.add(1.0);
    	c1.addPoint(new NewPoint(p1));
    	c1.addPoint(new NewPoint(p2));
    	c1.addPoint(new NewPoint(p3));
    	
    	NewCluster c2 = new NewCluster();
    	ArrayList<Double> p4 = new ArrayList<Double>();
    	p4.add(2.0);
    	p4.add(2.0);
    	p4.add(2.0);
    	ArrayList<Double> p5 = new ArrayList<Double>();
    	p5.add(2.0);
    	p5.add(1.0);
    	p5.add(2.0);
    	ArrayList<Double> p6 = new ArrayList<Double>();
    	p6.add(2.0);
    	p6.add(2.0);
    	p6.add(1.0);
    	c2.addPoint(new NewPoint(p4));
    	c2.addPoint(new NewPoint(p5));
    	c2.addPoint(new NewPoint(p6));
    	
    	System.out.println("Cluster: " + c1);
    	System.out.println("Midpoint: " + c1.midpoint);
    	System.out.println("Cluster 2: " + c2);
    	System.out.println("Midpoint 2: " + c2.midpoint);
    	System.out.println("DistanceTo = " + meanLink(c1, c2));
    }
    
}
