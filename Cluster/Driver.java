package Cluster;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.math.*;
import java.text.DecimalFormat;

/*
 * The function to be run in Assignment 3. Instructions for running can be found in the README file.  
 */

public class Driver {
    
    /*****Initialized by parseArgs********/
    public static String inputFile;
    public static String d;
    public static int k;
    public static String abType; //which assignment-based algorithm to run
    public static String abInput; //which method of choosing initial clusters to run 
    public static String gDist; //which distance function to use in gonzalez algorithm 
    /*************************************/
    
    public static HCluster hc;
    public static ABCluster ab;
    public static ArrayList<NewPoint> centers;
    public static ArrayList<NewPoint> points = new ArrayList<NewPoint>();
    //Set this for different number of k-means++ runs 
    public static int kmeansRep = 40;
    
    public static void parseArgsHC(String[] args) {
	if(args[2].equals("single-link") || args[2].equals("complete-link") || args[2].equals("mean-link")) {
	    d = args[2];
	} else {
	    System.out.println("Error: d must be single-link, complete-link, or mean-link");
	    System.exit(0);
	} if(args.length == 3) {
	    k = 3; //Default k value
	} else if(args.length == 4) {
	    k = Math.max(1, Integer.parseInt(args[3])); //ensures at least 1 cluster
	} else {
	    System.out.println("Error! Too many arguments. Usage: <pathToInputFile> <hc | ab> <algorithm> + options (see README for list)");
	    System.exit(0);
	}
    }
    
    public static void parseArgsAB(String[] args) {
	/* g k (4 total) 
	 * k k (4 total) 
	 * l {default | g | k}  
	 */
	if(args[2].equals("g") || args[2].equals("k") || args[2].equals("l")) {
	    abType = args[2];
	} else {
	    System.out.println("Error: 3rd argument must be g, k, or l");
	    System.exit(0);
	}
	if(abType.equals("g") || abType.equals("k")) {
	    if(args.length == 3) k = 3; //default value of k
	    else if (args.length == 4) k = Math.max(1, Integer.parseInt(args[3])); //ensures at least 1 cluster
	    else {
		System.out.println("Error! Too many arguments. Usage: <pathToInputFile> ab <g | k> (<k>)");
		System.exit(0);
	    }
	} else { //must be lloyd's algorithm since already checked for g/k/l
	    if(args.length == 3) {
		//default values for lloyd's algorithm
		abInput = "d";
		k = 3; 
	    } else {
		if(args[3].equals("d") || args[3].equals("g") || args[3].equals("k")) {
		    abInput = args[3];
		} else {
		    System.out.println("Error: 4th argument must be g, k, or d");
		    System.exit(0);
		    
		} if(args.length == 4) k = 3;
		else if(args.length == 5) k = Math.max(1, Integer.parseInt(args[3])); //ensures at least 1 cluster
		else {
		    System.out.println("Error! Too many arguments. Usage: <pathToInputFile> ab l (<g | k | d>) (<k>)");
		    System.exit(0);
		}
	    }
	}
    }
    
    //Assumes THERE IS a name given...
    public static void loadInputHC() throws IOException {
	hc = new HCluster(d, k);
	try {
	    BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile), Charset.forName("US-ASCII"));
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		String[] input = line.split("\\s+");
		ArrayList<Double> coords = new ArrayList<Double>();
		for(int i = 1; i <= k; i++) {
		    coords.add(Double.parseDouble(input[i]));
		}
		hc.addCluster(new NewPoint(input[0], coords));
		System.out.println(coords);
		/*if(input.length == 3) {
		    hc.addCluster(new NewPoint(input[0], Double.parseDouble(input[1]), Double.parseDouble(input[2])));
		} else if(input.length == 2) {
		    hc.addCluster(new NewPoint(Double.parseDouble(input[0]), Double.parseDouble(input[1])));
		    } */
			      
	    } catch(IOException e) {
		System.out.println("IOException: " + e.getMessage());
	    }
	}
    }
    
    //Assumes THERE IS a name...
    public static void loadInputAB() throws IOException {
	ab = new ABCluster(abType, abInput, k);	
	try {
	    BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile), Charset.forName("US-ASCII"));
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		String[] input = line.split("\\s+");
		ArrayList<Double> coords = new ArrayList<Double>();
		for(int i = 1; i <= k; i++) {
		    coords.add(Double.parseDouble(input[i]));
		}
		points.add(new NewPoint(input[0], coords));
		ab.points.add(new NewPoint(input[0], coords));
		/*if(input.length == 3) {
		    points.add(new NewPoint(input[0], Double.parseDouble(input[1]), Double.parseDouble(input[2])));
		    ab.points.add(new NewPoint(input[0], Double.parseDouble(input[1]), Double.parseDouble(input[2])));
		} else if(input.length == 2) {
		    points.add(new NewPoint(Double.parseDouble(input[0]), Double.parseDouble(input[1])));
		    ab.points.add(new NewPoint(Double.parseDouble(input[0]), Double.parseDouble(input[1])));
		    }*/
		//Have now filled ab's points arrayList
	    }
	} catch(IOException e) {
	    System.out.println("IOException: " + e.getMessage());
	}
    }
    
    public static void runHC(String[] args) throws IOException {
	parseArgsHC(args);
	System.out.println("Input file: " + inputFile + "\nd: " + d + "\nk: " + k);
	System.out.println("About to call loadInputHC()");
	loadInputHC();
	System.out.println("hc: " + hc);
	System.out.println("\n***********Running Hierarchical Clustering***********\n");
	hc.cluster();
	System.out.println("hc: " + hc.toStringName()); 
    }
    
    public static void runAB(String[] args) throws IOException {
	parseArgsAB(args);
	printVars();
	//System.out.println("Input file: " + inputFile + "\nd: " + d + "\nk: " + k);
	loadInputAB();
	DecimalFormat newFormat = new DecimalFormat("####.##");
	System.out.println("ab: " + ab);
	System.out.println("\n***********Running Assignment-Based Clustering***********\n");
	
	//Running gonzalez
	if(abType.equals("g")) {
	    System.out.println("Starting gonzalez clustering");
	    System.out.println("===========================================================");
	    double[] costs = ab.gonzalez(ab.points.get(0));
	    System.out.println("3-center cost: " + costs[0]);
	    System.out.println("3-means cost: " + costs[1]);
	    System.out.println("ab: " + ab);
	}
	
	//Running k-means++
	else if(abType.equals("k")) {
	    System.out.println("Starting k-means++ clustering");
	    System.out.println("===========================================================");
	    double[] costArr = new double[kmeansRep];
	    Map<Double, Integer> cdf = new TreeMap<Double, Integer>();
	    for(int i = 0; i < kmeansRep; i++) {
		ABCluster copy = ab.copy();
		double cost = Double.valueOf(newFormat.format(copy.kmeanstt(copy.points.get(0))));
		System.out.println(copy.centers);
		System.out.println(cost);
		costArr[i] = Double.valueOf(newFormat.format(cost));
	    }
	    //Building cdf
	    for(int j = 0; j < costArr.length; j++) {
		Double key = costArr[j];
		if(cdf.containsKey(key)) {
		    Integer value = cdf.remove(key);
		    cdf.put(key, value + 1);
		    
		} else {
		    cdf.put(key, 1);
		}
	    } 
	    System.out.println(cdf);
	    
	    //Running lloyd	
	} else if(abType.equals("l")) {
	    System.out.println("Starting lloyd's algorithm with initial centers from " + abInput);
	    System.out.println("===========================================================");
	    
	    //Initializing centers based on abInput 
	    if(abInput.equals("g")) {
		ab.gonzalez(ab.points.get(0));
		ab.points = points;
		System.out.println("Initial Centers: " + ab.centers);
		double cost = ab.lloyd();
		System.out.println("Cost: " + cost);
	    } else if (abInput.equals("k")) {
		double[] costArr = new double[kmeansRep];
		Map<Double, Integer> cdf = new TreeMap<Double, Integer>();
		for(int i = 0; i < kmeansRep; i++) {
		    ABCluster copy = ab.copy();
		    copy.kmeanstt(copy.points.get(0));
		    copy.points = points;
		    System.out.println("Initial: ");
		    copy.printClusters();
		    double cost = copy.lloyd();
		    costArr[i] = Double.valueOf(newFormat.format(cost));
		    System.out.println("Final: ");
		    copy.printClusters();
		}
		//Building cdf
		for(int j = 0; j < costArr.length; j++) {
		    Double key = costArr[j];
		    if(cdf.containsKey(key)) {
			Integer value = cdf.remove(key);
			cdf.put(key, value + 1);
			
		    } else {
			cdf.put(key, 1);
		    }
		} 
		System.out.println(cdf);
	    } else {
		ab.centers.add(ab.points.get(0)); //Adding a to initCenters
		ab.centers.add(ab.points.get(1)); //Adding b to initCenters
		ab.centers.add(ab.points.get(2)); //Adding c to initCenters	
		System.out.println("Initial Centers: " + ab.centers);
		double cost = ab.lloyd();
		System.out.println("Cost: " + cost);
	    }
	}
	System.out.println("Final Centers: " + ab.centers);
	System.out.println("ab: " + ab); 
    }
    
    private static void printVars() {
	System.out.println("inputFile: " + inputFile);
	System.out.println("d: " + d);
	System.out.println("k: " + k);
	System.out.println("abType: " + abType);
	System.out.println("abInput: " + abInput);
	System.out.println("gDist: " + gDist);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
	for(int i = 0; i < args.length; i++) {
	    System.out.println(args[i]);
	}
	if(args.length < 3) {
	    System.out.println("Error! Usage: <pathToInputFile> <hc | ab> <algorithm> + options (see README for list)");
	    System.exit(0);
	} else {
	    inputFile = args[0];
	    if(args[1].equals("hc")) runHC(args);
	    else if(args[1].equals("ab")) runAB(args);
	    else System.out.println("Second argument must be hc (hierarchical clustering) or ab (assignment-based clustering)");
	}
    }
    
}
