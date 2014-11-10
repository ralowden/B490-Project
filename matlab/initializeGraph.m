function [edges, totalEdges] = initializeGraph()
global nodesToAdd
global totalNodes
global initialNodes
edges = zeros(totalNodes);
for i = 1:initialNodes
    for j = 1:initialNodes
        if i ~= j
            edges(i,j) = 1;
        end
    end
end
totalEdges = combination(initialNodes, nodesToAdd);
    
    