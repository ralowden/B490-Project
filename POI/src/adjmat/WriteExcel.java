package adjmat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class WriteExcel {

    static ArrayList<Row> rows = new ArrayList<Row>();

    public static void write(ArrayList<Integer> nodes, TreeMap<Integer, ArrayList<Integer>> edges, String fileName) {
	//Blank workbook
	XSSFWorkbook workbook = new XSSFWorkbook(); 
	
	//Create a blank sheet
	XSSFSheet sheet = workbook.createSheet("Adjacency Matrix");

	//Create rows 
	for(int i = 0; i < nodes.size() + 1; i++) {
	    Row row = sheet.createRow(i);
	    rows.add(row);
	}

	//Create headings
	for(int i = 1; i < nodes.size() + 1; i++) {
	    Integer key = nodes.get(i-1);
	    Cell cellc = rows.get(i).createCell(0);
	    Cell cellr = rows.get(0).createCell(i);
	    cellc.setCellValue(key);
	    cellr.setCellValue(key);
	}


	//Iterate over data & write to sheet
	int i, j;
	for(i = 1; i < nodes.size() + 1; i++) 
	    {
		int key = nodes.get(i-1);
		for(j = i; j < nodes.size() + 1; j++) 
		    {
			int value = nodes.get(j-1);
			Cell cell = rows.get(i).createCell(j);
			if(i == j) 
			    {
				cell.setCellValue(0);
			    }
			else {
			    Cell cell2 = rows.get(j).createCell(i);
			    cell2.setCellValue(1);
			    cell.setCellValue(1);
			    if(edges.containsKey(key) && edges.get(key).contains(value)) {
				cell.setCellValue(1);
				cell2.setCellValue(1);
			    } else {
				cell.setCellValue(0);
				cell2.setCellValue(0);
				}
			}
		    }
	    }
 
	try {
	    //Write the workbook in file system
	    FileOutputStream out = new FileOutputStream(new File("excel/" + fileName + ".xlsx"));
	    workbook.write(out);
	    out.close();
	    
	    System.out.println(fileName + " written successfully on disk.");
	    
	} catch (Exception e)  {
	    e.printStackTrace();
	}
    }
}
