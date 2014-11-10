global nodesToAdd
nodesToAdd = 2;
global initialNodes;
initialNodes = 2;
global totalNodes;
totalNodes = 10;
[edges, totalEdges] = initializeGraph();
for i = [initialNodes+1 : totalNodes]
    [edges, totalEdges] = attach(i, edges, totalEdges);
end