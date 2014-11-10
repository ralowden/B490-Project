package Cluster;

import java.text.DecimalFormat;
import java.util.*;

/*
 * This is the CLUSTER class
 * Fields:
 * 		ArrayList<Point> points: all of the points assigned to this cluster
 * 		Point midpoint: the center of the cluster (may not be set, depending on which clustering algorithm is used)
 * Methods:
 * 		void merge(Cluster other): puts all of other's points into this arrayList of points
 * 		void addPoint(Point p): adds p to this arrayList of points
 * 		static double singleLink(Cluster c1, Cluster c2): returns the distance between the two closest points in c1 and c2
 * 		static double completeLink(Cluster c1, Cluster c2): returns the distance between the two farthest points in c1 and c2
 * 		static double meanLink(Cluster c1, Cluster c2): returns the distance between the "mean" points of c1 and c2
 * 		static Point average(ArrayList<Point> list): returns the midpoint of a list of Points
 * 		String toString(): converts a Cluster to a String
 */

public class Cluster {
    //Global variables
    public static DecimalFormat newFormat = new DecimalFormat("####.##"); //Rounding to 2 decimal places
    public ArrayList<Point> points; //ArrayList of points within the cluster
    private Point midpoint;
    
    //Empty constructor for a cluster
    public Cluster() {
	this.points = new ArrayList<Point>();
	this.midpoint = null;
    }
    
    //Constructor for a cluster with a predefined ArrayList of points
    public Cluster(ArrayList<Point> points) {
	this.points = points;
	this.midpoint = null;
    }
    
    //Constructor for a cluster with just 1 point 
    public Cluster(Point p) {
	this.points = new ArrayList<Point>();
	this.midpoint = null;
	this.addPoint(p);
    }
    
    /* Combines input cluster with calling cluster
     * Input:
     * 		Cluster other: the cluster to be merged with this cluster
     */
    public void merge(Cluster other) {
	this.points.addAll(other.points);
    }
    
    /* Adds a point to the cluster
     * Input:
     * 		Point p: the point to be added to the cluster
     */
    public void addPoint(Point p) {
	this.points.add(p);
    }
    
    /* Calculates the distance between two clusters using the shortest link
     * Input:
     * 		Cluster c1, c2: clusters whose distance is to be calculated
     * Returns the minimum distance between c1 and c2
     */
    public static double singleLink(Cluster c1, Cluster c2) {
	double min = Integer.MAX_VALUE; 
	double dist;
	for(Point p1 : c1.points) {
	    for(Point p2 : c2.points) {
		dist = Point.distanceTo(p1, p2);
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
    public static double completeLink(Cluster c1, Cluster c2) {
	double max = Integer.MIN_VALUE; 
	double dist;
	for(Point p1 : c1.points) {
	    for(Point p2 : c2.points) {
		dist = Point.distanceTo(p1, p2);
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
    public static double meanLink(Cluster c1, Cluster c2) {	
	c1.midpoint = average(c1.points);
	c2.midpoint = average(c2.points);
	return Point.distanceTo(c1.midpoint, c2.midpoint);
    }
    
    /* Computes the mean point of a list of points. Used for finding a cluster's "midpoint"
     * Input:
     * 		ArrayList<Point> list: the list of points whose midpoint is to be calculated
     * Returns the midpoint of list
     */
    public static Point average(ArrayList<Point> list) {
	double x_Av = 0, y_Av = 0;
	for(int i = 0; i < list.size(); i++) {
	    x_Av += list.get(i).getX();
	    y_Av += list.get(i).getY();
	}
	return new Point(Double.valueOf(newFormat.format(x_Av/list.size())), Double.valueOf(newFormat.format(y_Av/list.size())));
    }
    
    //Overrides Object's toString method
    public String toString() {
	return points.toString();
    }
    
    public String toStringName() {
	String str = "[";
	for(Point p : this.points) {
	    str += p.name + ", ";
	} return str += "]";
	
    }
    
    //A debugging function: used to print only the names of the points in a cluster
    public void printName() {
	System.out.print("[");
	for(Point p : this.points) {
	    System.out.print(p.name + ", ");
	}
	System.out.println("]");
    }
    
}
