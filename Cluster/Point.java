package Cluster;

import java.lang.Math;

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

public class Point {

    //Global variables
    public double x;
    public double y;
    public String name;
    
    /*
     * Point constructor with name, x-coord, and y-coord
     */
    public Point(String name, double x, double y) {
	this.x = x;
	this.y = y;
	this.name = name;
    }
    
    /*
     * Point constructor with just the x- and y-coords 
     */
    public Point(double x, double y) {
	this("", x, y);
    }
    
    //Returns the x value of the calling point 
    public double getX() {
	return this.x;
    }
    //Returns the y value of the calling point 
    public double getY() {
	return this.y;
    }
    
    /* Calculates L_2 distance between two points
     * Input:
     * 		Points p1, p2
     * Returns distance between p1 and p2
     */
    public static double distanceTo(Point p1, Point p2) {
	return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
    
    /* Determines if this point is the same as other
     * Input: 
     * 		Point other: the point to be compared to this
     * Returns true if the x, y, and name fields are all equal. 
     */
    public boolean equals(Point other) {
	return (this.x == other.x) && (this.y == other.y) && (this.name.equals(other.name))
	    ;	}
    
    /*
     * Overrides Object's toString method
     */
    public String toString() {
	return this.name + ": (" + this.x + ", " + this.y + ")";
    }
    
    public String toStringName() {
	return this.name;
    }
    
}
