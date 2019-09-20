/**
* ReadExcel reads in an input excel spreadsheet and retrieves the contents of the cells
* @author Shreyaa Raghavan and Elizabeth Song
* @version 4.08.19
*/

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.lang.*;

import jxl.*;
import jxl.read.biff.BiffException;

public class ReadExcel {

    private String inputFile;		
    double[][] data = null;
    private static double[][] dataset;
    
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public double[][] read() throws IOException  
    {
        File inputWorkbook = new File(inputFile);
        Workbook w;

       try {
            w = Workbook.getWorkbook(inputWorkbook);
            
            // Get the first sheet
            Sheet sheet = w.getSheet(1);
            data = new double[sheet.getRows()][sheet.getColumns()];
            
            // Loop over all the columns and rows
            for (int j = 0; j <sheet.getColumns(); j++) 
            {
                for (int i = 1; i < sheet.getRows(); i++) 
                {
                    Cell cell = sheet.getCell(j, i);
                    data[i][j] = (double)Integer.parseInt(cell.getContents());
                }
            }
		} 
    	catch (BiffException e) 
        {
            e.printStackTrace();
        }
    return data;
    }
    
	public static void main(String[] args) throws IOException 
    {
        ReadExcel test = new ReadExcel();
        test.setInputFile("Absenteeism_at_work.xls");
        dataset = test.read();
        System.out.println(Arrays.deepToString(dataset));
    }



}