function [ V, lambda ] = SymNorm( D,L )
L = mpower(D, -1/2) * L * mpower(D, -1/2);
[V,lambda] = eig(L);    
lambda = diag(lambda);
end