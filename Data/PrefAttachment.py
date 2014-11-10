import sys
import collections
import random

global totalNodes
global nodesToAdd


#Returns filled edges 
def initializeGraph() :
    edges = collections.defaultdict(list)
    for i in range(0,nodesToAdd):
        for j in range(0,nodesToAdd):
            if not i == j:
                edges[i].append(j)
    totalEdges = combination(nodesToAdd,2)
    return edges, totalEdges

def combination(n, r):
    return fact(n)/fact(n-r)

def fact(n):
    f = 1
    while n > 0:
        f *= n
        n -= 1
    return f

def attach(newNode, edges, totalEdges):
    m = 0
    nodes = edges.keys()
    while m < nodesToAdd:
        randint = random.randint(0,len(nodes)-1)
        chance = random.random()
        node = nodes[randint]
        d = float(len(edges[node]))/float(totalEdges)
        if d > chance: 
            nodes.remove(node)
            edges[node].append(newNode)
            edges[newNode].append(node)
            m += 1
            #print node
            #print d 
    totalEdges += 2*nodesToAdd
    return edges, totalEdges

"""  
    
    public void attach(Integer newNode) {
	Random rand = new Random();
	double chance;
	int randint, m = 0;
	ArrayList<Integer> nodes = new ArrayList<Integer>();
	nodes.addAll(edges.keySet());
	edges.put(newNode, new ArrayList<Integer>());
	while(m < 2) {
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
	this.totalEdges += 4; 
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

}"""

def testPrint():
    print totalNodes
    print nodesToAdd

if __name__ == "__main__":
    totalNodes = int(sys.argv[1])
    nodesToAdd = int(sys.argv[2])
    #testPrint()
    edges, totalEdges = initializeGraph()
    for i in range(nodesToAdd, totalNodes):
        edges, totalEdges = attach(i, edges, totalEdges)
    #print edges
    fileName = "%d.txt" %totalNodes
    file = open(fileName, 'w+')
    for key in edges:
        for value in edges[key]:
            if value > key:
                toWrite = "%d %d\n" %(key, value)
                file.write(toWrite)

        
