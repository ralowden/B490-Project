package Cluster;

import java.util.*;

/* An HCluster keeps track of and manipulates all clusters in the sample space
 * Fields:
 * 		ArrayList<NewCluster> clusters: all of the clusters in the sample space 
 * 		String d: the distance function used for clustering
 * 		int k: the number of clusters to be formed 
 * Methods:
 * 		void addCluster(NewCluster c): adds c to this clusters
 * 		void addCluster(NewPoint p): creats a new cluster from p and adds it to this clusters; used for loading input in Driver.java
 * 		void cluster(): finds "k" clusters from the sample space using hierarchical clustering
 * 		String toString(): converts an HCluster to a String
 */
public class HCluster {

	//Global variables
	public ArrayList<NewCluster> clusters;
	public String d; //distance function used for clustering
	public int k;
	
	//Constructor that takes a predefined arrayList of clusters and 
	//arbitrary k and d values 
	public HCluster(ArrayList<NewCluster> clusters, String d, int k) {
		this.clusters = clusters;
		this.d = d;
		this.k = k;
	}
	
	//Empty constructor; defaults to k-value of 3 and single-link distance function
	public HCluster() {
		this.clusters = new ArrayList<NewCluster>();
		this.d = "single-link"; 
		this.k = 3; 
	}
	
	//Constructor that takes just d and k
	public HCluster(String d, int k) {
		this(new ArrayList<NewCluster>(), d, k);
	}
	
	//Adds cluster c to the arrayList of clusters
	public void addCluster(NewCluster c) {
		this.clusters.add(c);
	}
	
	//Creates a new cluster from point p and adds it to 
	//the arrayList of clusters
	public void addCluster(NewPoint p) {
		this.clusters.add(new NewCluster(p));
	}

	/* The main clustering algorithm. 
	 * Finds "k" clusters through hierarchical clustering. The distance function used is either single-link, complete-link,
	 * or mean-link depending on what "d" is set as. 
	 */
    public void cluster() {
	while(this.clusters.size() > this.k) {
	    double minDist = Double.MAX_VALUE;
	    double dist = 0;
	    NewCluster toMerge1 = null;
	    NewCluster toMerge2 = null;
	    //Logic for finding which clusters to merge
	    for(NewCluster c1 : this.clusters) {
		for(NewCluster c2 : this.clusters) {
		    if(! c1.equals(c2)) {
			//Choosing correct distance function
			if(d.equals("single-link")) dist = NewCluster.singleLink(c1, c2);
			else if(d.equals("complete-link")) dist = NewCluster.completeLink(c1, c2);
			else if(d.equals("mean-link")) dist = NewCluster.meanLink(c1,c2);
			if(dist < minDist) {
			    minDist = dist;
			    toMerge1 = c1;
			    toMerge2 = c2;
			}	
		    }
		}
	    }
	    toMerge1.merge(toMerge2);
	    this.clusters.remove(toMerge2);
	}
    }
    
    //Overrides Object's toString function 
    public String toString() {
	int i = 1;
	String str = "{\n";
	for(NewCluster c : this.clusters) {
	    str += i + ": " + c.toString() + "\n";
	    i++;
	}
	return str + "}";
    }
    
    public String toStringName() {
	int i = 1;
	String str = "{\n";
	for(NewCluster c : this.clusters) {
	    str += i + ": " + c.toStringName() + "\n";
	    i++;
	}
	return str + "}";
    }
    
}
