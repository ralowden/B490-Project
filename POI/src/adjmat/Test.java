package adjmat;

import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    static ArrayList<Integer> nodes = new ArrayList<Integer>();
    static HashMap<Integer, ArrayList<Integer>> edges = new HashMap<Integer, ArrayList<Integer>>();

    public static void main(String args[]) {
	nodes.add(1);
	nodes.add(2);
	nodes.add(3);
	nodes.add(4);
	ArrayList<Integer> n1 = new ArrayList<Integer>();
	n1.add(2);
	n1.add(4);
	ArrayList<Integer> n2 = new ArrayList<Integer>();
	n2.add(3);
	n2.add(4);
	edges.put(1,n1);
	edges.put(2,n2);
	System.out.println(nodes);
	System.out.println(edges);
	WriteExcel.write(nodes,edges,"Test");
    }
}