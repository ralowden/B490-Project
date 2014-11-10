#import xlsxwriter
import sys
import string
from igraph import *

"""#Create file from user input
if len(sys.argv) < 2:
    sys.exit("Error: need filename as input")

fileName = sys.argv[1] 
file = open(fileName)
fileNameWithoutExt = fileName.split(".")
newFile = fileNameWithoutExt[0] + ".xlsx"

edges = {}
values = []

for line in file:
    entries = string.split(line)
    key = int(entries[0])
    value = int(entries[1])

    if key in edges:
        edges[key].append(value)
    else:
        edges[key] = [value]
    if not value in values:
        values.append(value)

keys = sorted(edges.keys())
values = sorted(values)
for value in values:
    if not value in keys:
        keys.append(value)
keys = sorted(keys)
print keys


#Create a workbook and add a worksheet
workbook = xlsxwriter.Workbook(newFile);
worksheet = workbook.add_worksheet()

#Fill headings of adjacency matrix
LUT = {}
for i in range(1, len(keys) + 1):
    worksheet.write(i, 0, keys[i-1])
    worksheet.write(0, i, keys[i-1])
    LUT[i] = keys[i-1]

#Fill adjacency matrix
for col in range(1, len(keys) + 1):
    key = LUT[col]
    for row in range(1, len(keys) + 1):
        value = LUT[row]
        if value in edges[key]:
            worksheet.write(row, col, 1)
        else:
            worksheet.write(row, col, 0)
    

workbook.close()
"""
