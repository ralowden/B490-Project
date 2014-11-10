package adjmat;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Random;

/*
Goal:
attach will determine a SINGLE node's connection to all other nodes in graph
only update totalEdges once gone through all nodes in nodes list. 

Update so that don't have to have arraylist of nodes 

Later abstract out initial node count 
 */


public class PrefAttachment {
    private int totalEdges, totalNodes, nodesToAdd;
    private TreeMap<Integer, ArrayList<Integer>> edges;

    public PrefAttachment() {
	this.totalEdges = 0;
	this.edges = new TreeMap<Integer, ArrayList<Integer>>();
    }

    private void parseArgs(String args[]) {
	this.totalNodes = Integer.parseInt(args[0]);
	this.nodesToAdd = Integer.parseInt(args[1]);
    }

    private void initializeGraph() {
	for(int i = 0; i < this.nodesToAdd; i++) {
	    this.edges.put(i, new ArrayList<Integer>());
	    for(int j = 0; j < this.nodesToAdd; j++) {
		if(i != j) edges.get(i).add(j);
	    }
	}
	totalEdges += combination(nodesToAdd, 2);
    }  

    public int combination(int n, int r) {
	return fact(n)/fact(n-r);
    }

    public int fact(int n) {
	int f = 1;
	while(n > 0) {
	    f *= n;
	    n--;
	}
	return f;
    }
    
    public void attach(Integer newNode) {
	Random rand = new Random();
	double chance;
	int randint, m = 0;
	ArrayList<Integer> nodes = new ArrayList<Integer>();
	nodes.addAll(edges.keySet());
	edges.put(newNode, new ArrayList<Integer>());
	while(m < this.nodesToAdd) {
	    randint = rand.nextInt(nodes.size());
	    chance = rand.nextDouble();
	    int node = nodes.remove(randint);
	    double d = (double) edges.get(node).size()/totalEdges;
	    if(d > chance) {
		System.out.println(newNode + "--" + node);
		edges.get(newNode).add(node);
		edges.get(node).add(newNode);
		m++;
	    } else {
		nodes.add(randint, node);
	    }
	}
	this.totalEdges += 2*this.nodesToAdd; 
    }

    public String toString() {
	String str = "Edges: " + this.edges;
	return str;
    }

    public static void main(String args[]) {
	PrefAttachment pa = new PrefAttachment();
	pa.parseArgs(args);
	pa.initializeGraph();
	for(int i = pa.nodesToAdd; i < pa.totalNodes; i++) {
	    pa.attach(i);
	}
	ArrayList<Integer> nodes = new ArrayList<Integer>();
	nodes.addAll(pa.edges.keySet());
	WriteExcel.write(nodes, pa.edges, String.valueOf(pa.totalNodes));
	//System.out.println(pa);
    }

}