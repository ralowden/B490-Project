package adjmat;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;

public class CreateGraph {
    static String fileName;
    static String fileNameWoutExt;
    static int k;
    static TreeMap<Integer, ArrayList<Integer>> edges = new TreeMap<Integer, ArrayList<Integer>>();
    static ArrayList<Integer> nodes = new ArrayList<Integer>();
    
    public static void parseArgs(String[] args) {
	if(args.length != 1) {
	    System.out.println("Error! Wrong number of arguments. Usage: <pathToInputFile>");;
	    System.exit(0);
	} else {
	    fileName = args[0];
	    String[] split = fileName.split("\\.");
	    String split2[] = split[0].split("/");
	    fileNameWoutExt = split2[2];
	}
    }

    public static void loadInput() throws IOException {
	try {
	    BufferedReader reader = Files.newBufferedReader(Paths.get(fileName), 
							    Charset.forName("US-ASCII"));
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		String[] input = line.split("\\s+");
		int node1 = Integer.parseInt(input[0]);
		int node2 = Integer.parseInt(input[1]);
		Integer smallKey = node1 < node2? node1 : node2;
		Integer largeKey = node1 > node2? node1 : node2; 
		if(! nodes.contains(node1)) nodes.add(node1);
		if(! nodes.contains(node2)) nodes.add(node2);
		if(edges.containsKey(smallKey)) {
		    edges.get(smallKey).add(largeKey);
		} else {
		    ArrayList<Integer> temp = new ArrayList<Integer>();
		    temp.add(largeKey);
		    edges.put(smallKey, temp);
		}
	    }
	    Collections.sort(nodes);
	} catch(IOException e) {
	    System.out.println("IOException: " + e.getMessage());
	}
    }
    
    public static void main(String args[]) throws IOException {
	parseArgs(args);
	loadInput();
	WriteExcel.write(nodes, edges, fileNameWoutExt);

    }
}