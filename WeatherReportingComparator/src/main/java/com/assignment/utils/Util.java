package com.assignment.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Util {

	public FileInputStream fis;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	
	final DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd SSS");

	//get all values in a column
	public List<Object> getColumnData(String colName, String filename, String sheetName) throws IOException{
    	fis = new FileInputStream("resources/" + filename);
        workbook = new XSSFWorkbook(fis);
        List<Object> data = new ArrayList<Object>();
        sheet = workbook.getSheet(sheetName);
        int noOfColumns = sheet.getRow(0).getLastCellNum();
        String[] Headers = new String[noOfColumns];
        for (int j=0;j<noOfColumns;j++){
            Headers[j] = sheet.getRow(0).getCell(j).getStringCellValue();
            int totalrows = sheet.getLastRowNum(); 	
            if(Headers[j].equals(colName)) {
               	for(int i = 1; i<totalrows;i++) {
            		try {
            			if(sheet.getRow(i).getCell(j).getCellType() == CellType.STRING) {
            			if(sheet.getRow(i).getCell(j).getStringCellValue()!=null)
						data.add(sheet.getRow(i).getCell(j).getStringCellValue());
            			else break;
						} 
            			//Numeric
            			else if(sheet.getRow(i).getCell(j).getCellType() == CellType.NUMERIC || sheet.getRow(i).getCell(j).getCellType() == CellType.FORMULA) {
            				String.valueOf(data.add(sheet.getRow(i).getCell(j).getNumericCellValue()));
            			} 
            			else if(sheet.getRow(i).getCell(j).getCellType() == CellType.BLANK) {
               				String.valueOf(data.add(sheet.getRow(i).getCell(j).getNumericCellValue()));
            			} 
            			}catch (Exception e) {
            			// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("There are null values");
					}
            	}
            	break;
            }
         }
        workbook.close();
        fis.close();
    	return data;	           
	 }
	
	//get value of a cell in a column
	public Object getCellData(String colName, String filename, String sheetName, int rowIndex) throws IOException {
		List<Object> columnValues = getColumnData(colName, filename, sheetName);
		Object cellValue = null;
		for(int i=0; i<=columnValues.size(); i++) {
			if(i==rowIndex) {
			cellValue = columnValues.get(i);
			break;
			}
		}
		return cellValue;
	}
	
	//read data from csv
	public String[][] readCSV(int a, int b){
		
		 String  [][] data=new String [a][b];
	        File file=new File("resource//testData.csv");
	         
	        int row=0;
	        int col=0;
	        BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String line=null;
	        //String e=reader.readLine();
	         
	        try {
				while((line=reader.readLine())!=null && row<data.length){
				    StringTokenizer st=new StringTokenizer(line, ";");
				    
				    while(st.hasMoreTokens()){
				        data[col][row]=st.nextToken();
				         
				        col++;
				    }
				    col=0;
				    row++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data;
		}
}
