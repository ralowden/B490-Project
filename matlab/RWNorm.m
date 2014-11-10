function [ V, lambda ] = RWNorm( D,L )
L = mpower(D, -1) * L;
[V,lambda] = eig(L);    
lambda = diag(lambda);
end