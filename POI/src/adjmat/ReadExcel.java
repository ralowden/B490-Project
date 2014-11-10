//package com.howtodoinjava.demo.poi;
package adjmat;

import Cluster.*;

import java.io.*;

//import java.io.File;
//import java.io.FileInputStream;
//import java.util.Iterator;
//import java.util.ArrayList;
import java.util.*;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
    
    /*****Initialized by parseArgs********/
    public static String inputFile;
    public static String d;
    public static int k = 16;
    public static String abType; //which assignment-based algorithm to run
    public static String abInput; //which method of choosing initial clusters to run 
    public static String gDist; //which distance function to use in gonzalez algorithm 
    /*************************************/

    public static HashMap<NewPoint,NewCluster> clusters;
    public static ArrayList<NewPoint> centers = new ArrayList<NewPoint>();
    public static ArrayList<NewPoint> points = new ArrayList<NewPoint>();
    //Set this for different number of k-means++ runs 
    public static int kmeansRep = 40;
    public static int totalNodes;
    
    public static NewPoint findClosestCenter(NewPoint p, ArrayList<NewPoint> centers) {
	double minDist = Double.MAX_VALUE;
	NewPoint closest = null;
	
	//Find closest center
	for(NewPoint center : centers) {
	    double d = NewPoint.distanceTo(center, p);
	    if(d < minDist) {
		closest = center;
		minDist = d;
	    }
	}
	return closest;
    }
    
    

    public static double lloyd() {
	DecimalFormat newFormat = new DecimalFormat("####.##"); //Rounding to 2 decimal places
	ArrayList<NewPoint> tempCenters = centers;
	centers = new ArrayList<NewPoint>();
	
	for(int i = 0; i < 10; i++) {
	    //Reset clusters & centers
	    clusters = new HashMap<NewPoint,NewCluster>();
	    centers = tempCenters;
	    tempCenters = new ArrayList<NewPoint>();
	    for(NewPoint p : centers) {
		clusters.put(p, new NewCluster());
	    }
	    System.out.println("Clusters: " + clusters);
	    
	    //Assigning points to clusters
	    for(NewPoint p : points) {
		NewPoint closest = findClosestCenter(p,centers);
		clusters.get(closest).addPoint(p);
	    }
	    System.out.println(clusters);
	    //Finding new centers
	    for(NewPoint p : clusters.keySet()) {
		System.out.println("New Point: " + p);
		NewCluster c = clusters.get(p);
		System.out.println("New Cluster: " + c);
		if(c.points.size() == 0) tempCenters.add(p);
		else { 
		    System.out.println(c.points);
		    tempCenters.add(NewCluster.average(c.points));
		}
	    }
	}
	
	//Calculating cost
	double cost = 0.0;
	for(NewPoint p : points) {
	    NewPoint closest = findClosestCenter(p,centers);
	    cost += Math.pow(NewPoint.distanceTo(closest, p), 2);
	}	
	return Double.valueOf(newFormat.format(cost));
    }
    
  
    public static void main(String[] args) throws IOException{
	try
	    {
		//
		FileInputStream file = new FileInputStream(new File("POI/src/data/edges638.xlsx"));
		
		//Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		//Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		//HCluster hc = new HCluster("mean-link", Integer.parseInt(args[1]));
		int pointname = 1;
		
		//Fill point cluster 
		for(Row row : sheet) {
		    ArrayList<Double> coords = new ArrayList<Double>();
		    for(int j = 1; j <= 16; j++) {
			Cell cell = row.getCell(j);
			coords.add(cell.getNumericCellValue());
		    }
		    points.add(new NewPoint(String.valueOf(pointname), coords));	       
		    pointname++;
		}
		file.close();
	    } 
	catch (Exception e) 
	    {
		e.printStackTrace();
	    }
	

	//Create initial Cluster
	int initCenters[] = {6,19,203,253,264,292,324,325,372,382,390,402,404,407,421,600};
	for(int i = 0; i < initCenters.length; i++) {
	    NewPoint p = points.get(initCenters[i]-1);
	    System.out.println(p);
	    centers.add(p);
	}
	for(NewPoint p : centers) {
	    if(points.contains(p)) points.remove(p);
	}

	double cost = lloyd();
	System.out.println(clusters);
    }
}
