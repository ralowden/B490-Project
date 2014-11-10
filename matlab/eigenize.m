load('GroupAverage_rsfMRI_matrix.mat')
disp('Loaded')
[D,L] = makeDL(edges);
disp('Unnorm')
[V,lambda] = Unnorm(L);
disp('Sym')
[V_s, lambda_s] = SymNorm(D,L);
disp('RW')
[V_rw, lambda_rw] = RWNorm(D,L);
disp('Starting unnormalized')
filename = '/Users/ralowden/Desktop/brain_V.xlsx';
xlswrite(filename,V);
disp('Starting sym')
filename = '/Users/ralowden/Desktop/brain_Vs.xlsx';
xlswrite(filename,V_s);
disp('Starting rw')
filename = '/Users/ralowden/Desktop/brain_Vrw.xlsx';
xlswrite(filename,V_rw);