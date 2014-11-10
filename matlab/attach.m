function [edges, totalEdges] = attach(newNode, edges, totalEdges)
global nodesToAdd
m = 0;
while m < nodesToAdd
    randint = randi(newNode-1);
    chance = rand(1);
    d = outdegree(randint,edges)/totalEdges;
    if d > chance & edges(newNode, randint) ~= 1
        edges(newNode, randint) = 1;
        edges(randint, newNode) = 1;
        m = m + 1;
    end
end
totalEdges = totalEdges + 2*nodesToAdd;
end

        