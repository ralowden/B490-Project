function [ V, lambda ] = Unnorm( L )
[V,lambda] = eig(L);    
lambda = diag(lambda);
end

