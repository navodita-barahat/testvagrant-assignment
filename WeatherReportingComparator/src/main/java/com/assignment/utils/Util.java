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
    	//System.out.println("Testing123");
    	fis = new FileInputStream("resources\\" + filename);
        workbook = new XSSFWorkbook(fis);
        List<Object> data = new ArrayList<Object>();
        //int col_Num = 0;
        sheet = workbook.getSheet(sheetName);
        int noOfColumns = sheet.getRow(0).getLastCellNum();
        //System.out.println(noOfColumns);
        String[] Headers = new String[noOfColumns];
        for (int j=0;j<noOfColumns;j++){
            Headers[j] = sheet.getRow(0).getCell(j).getStringCellValue();
            int totalrows = sheet.getLastRowNum(); 	
          //  System.out.println(totalrows);
            if(Headers[j].equals(colName)) {
            	//if(sheet.rowIterator().hasNext())
            	for(int i = 1; i<totalrows;i++) {
            		try {
            			if(sheet.getRow(i).getCell(j).getCellType() == CellType.STRING) {
            				//System.out.println("String Data. Row number "+sheet.getRow(i).getCell(j).getRowIndex());
            			if(sheet.getRow(i).getCell(j).getStringCellValue()!=null)
						data.add(sheet.getRow(i).getCell(j).getStringCellValue());
            			else break;
						} 
            			//Numeric
            			else if(sheet.getRow(i).getCell(j).getCellType() == CellType.NUMERIC || sheet.getRow(i).getCell(j).getCellType() == CellType.FORMULA) {
            				//System.out.println("Numeric Data. Row Number "+sheet.getRow(i).getCell(j).getRowIndex());
            				String.valueOf(data.add(sheet.getRow(i).getCell(j).getNumericCellValue()));
            			} 
            			else if(sheet.getRow(i).getCell(j).getCellType() == CellType.BLANK) {
            				//System.out.println("Numeric Data. Row Number "+sheet.getRow(i).getCell(j).getRowIndex());
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
	

	public void createFolderAtGivenLocation(String folderName)
	{
	
	final String srcFolder = folderName;
	File theDir = new File(srcFolder);
	
	//if the dorectory doesnt exists then create it
	if(!theDir.exists())
	
	//make directory
	theDir.mkdir();
	
	}
	
	public String generateUniqueName(){
			Date date = new Date();
			String fileName = dateFormat.format(date);
			return fileName;
		}
	
	public void createFolder(String folderName){
	
	final String srcFolder = folderName+generateUniqueName();
	File theDir = new File(srcFolder);
	
	//if th edirectory doesnt exists, create it
	if(!theDir.exists())
	
	//make dir
	theDir.mkdir();
	}
	
	public boolean checkFolderExistsAtGivenLocation(String location, String folderName){
	final String srcFolder = location+"\\"+folderName;
	
	File theDir= new File(srcFolder);
	
	//if directory doesnt exists create it
	return theDir.exists();
	
	}
	
	public void deleteFolderAtGivenLocation(String folderName) throws Exception{
	
	final String srcFolder= folderName;
	
	File directory = new File(srcFolder);
	
	//make sure directory exists
	if(directory.exists())
	deleteFile(directory);
	}
	
	public void deleteFile(File file) throws IOException{
	
	if(file.isDirectory()){
	
	//directory is empty then delete it
	
	if(file.list().length==0){
	file.delete();
	}
	else{
	
	//list all the directory content
	String files[]=file.list();
	
	for(String temp:files){
	//construct the file structure
	File fileDelete = new File(file,temp);
	
	//recursive delete
	deleteFile(fileDelete);
	}
	
	//check the directory path again, if empty then delete it
	if(file.list().length==0){
	file.delete();
	}
	}}
	else{
	//if file, then delete it
	file.delete();
	}
	}
	
	
	public void moveFileFromSrcToDest(String source, String destination) throws Exception{
	
	File src = new File(source);
	File dest = new File(destination);
	if(src.exists())
		FileUtils.copyDirectory(src,dest);
	
	}
	
	
	public void deleteExistingExecutionFolders() throws Exception{
		
	System.out.println("in before suite");
	moveFileFromSrcToDest("\\screenShots", "C:\\Users\\Navodita\\Downloads\\screenshots");
	//delete error screeshot folder
	deleteFolderAtGivenLocation("screenShots");
	createFolderAtGivenLocation(generateUniqueName());
	}
	
			
	public void archiveExecuttionFolder(){
	//create report and screenshots in a folder with todays date
		//foldername= currentfolder;
		//movefile
	
	String executionFolderName = generateUniqueName();
	createFolderAtGivenLocation(generateUniqueName());
	//moveFileFromSrourceToDest("E://test...screenshot", "E:\\" executionFolderName+"\\ScreenShot");
	}
	
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
