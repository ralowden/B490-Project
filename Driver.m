%A = xlsread('C:Users\ralowden\Desktop\3980edges.xlsx'); %For Windows
A = xlsread('/Users/ralowden/Desktop/3980edges.xlsx');   %For Mac
n = size(A,1);
for i = 2:n
    col = A(i,:);
    rows = col(2:n);
    temp(i-1) = sum(rows);
end
A = A(2:end, 2:end);
D = diag(temp);

%)_______________Unnormalized___________________
L = D - A;
[V,lambda] = eig(L);    
lambda = diag(lambda);

%____________Symmetric normalization_____________ 
%From paper: L_sym = D^(-.5)LD(-.5)
L_sym = mpower(D, -1/2) * L * mpower(D, -1/2);
[V_sym,lambda_sym] = eig(L_sym);
lambda_sym = diag(lambda_sym);

%___________Random-walk normalization____________ 
%From paper: L_rw = D^(-1)L
L_rw = mpower(D, -1) * L;
[V_rw,lambda_rw] = eig(L_rw);
lambda_rw = diag(lambda_rw);

clear col i n rows temp

%U = V(1:end, 2:(1+k));