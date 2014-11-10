function [ D,L ] = makeDL(edges)
n = size(edges,1);
for i = 1:n
    col = edges(i,:);
    rows = col(1:n);
    temp(i) = sum(rows);
end
D = diag(temp);
L = D - edges;
end