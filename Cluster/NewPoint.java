package Cluster;

import java.lang.Math;
import java.util.ArrayList;

/*
 * This is the POINT class. 
 * Fields:
 * 		double x: the x-coordinate of the point
 * 		double y: the y-coordinate of the point
 * 		String name: an optional name of the point (e.g. "a")
 * Methods:
 * 		getX(): returns a point's x field
 * 		getY(): returns a point's y field
 * 		static distanceTo(Point p1, Point p2): returns the L_2 distance between p1 and p2
 * 		equals(Point other): return true if "this" point has the same x, y, and name fields as other
 * 		toString(): converts a Point to a String
 */

public class NewPoint {
    
    //Global variables
    protected ArrayList<Double> coords = new ArrayList<Double>();
    public String name;
    
    public NewPoint() {
    	this.name = "";
    	this.coords = new ArrayList<Double>();
    }
    
    /*
     * Point constructor with name, x-coord, and y-coord
     */
    public NewPoint(String name, ArrayList<Double> coords) {
	this.coords = coords;
	this.name = name;
    }
    
    /*
     * Point constructor with just the x- and y-coords 
     */
    public NewPoint(ArrayList<Double> coords) {
	this("", coords);
    }
    
    /* Calculates L_2 distance between two points
     * Input:
     * 		Points p1, p2
     * Returns distance between p1 and p2
     */
    public static double distanceTo(NewPoint p1, NewPoint p2) {
	double sum = 0;
	for(int i = 0; i < p1.coords.size(); i++) {
	    sum += Math.pow((p1.coords.get(i) - p2.coords.get(i)), 2);
	}
	return Math.sqrt(sum);
    }
    
    /* Determines if this point is the same as other
     * Input: 
     * 		Point other: the point to be compared to this
     * Returns true if the x, y, and name fields are all equal. 
     */
    public boolean equals(NewPoint other) {
	boolean same = true;
	if(! this.name.equals(other.name)) return false;
	for(int i = 0; i < this.coords.size(); i++) {
	    if(this.coords.get(i) != other.coords.get(i)) {
		same = false;
		break;
	    }
	}
	return same;
    }
    
    /*
     * Overrides Object's toString method
     */
    public String toString() {
	return this.name;
    }
    
    public String toStringName() {
	return this.name;
    }

    public static NewPoint deepCopy(NewPoint p) {
    	NewPoint np = new NewPoint();
    	for(int i = 0; i < p.coords.size(); i++) {
    		np.coords.add(p.coords.get(i));
    	}
    	np.name = p.name;
    	return np;
    }
    
    public static void main(String args[]) {
	NewPoint p1 = new NewPoint(new ArrayList<Double>());
	NewPoint p2 = new NewPoint(new ArrayList<Double>());
	p1.coords.add(5.0);
	p1.coords.add(10.0);
	p1.coords.add(15.0);
	p2.coords.add(1.0);
	p2.coords.add(2.0);
	p2.coords.add(3.0);
	System.out.println("p1: " + p1);
	System.out.println("p2: " + p2);
	System.out.println("Distance: " + distanceTo(p1,p2));
    }
    
}
