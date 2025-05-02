package utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility 
{
	public FileInputStream fis;
	public FileOutputStream fos;
	public String filePath;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	
	public ExcelUtility(String filePath) throws IOException 
	{
		 this.filePath = filePath;		 
	}
	
	public int getRowCount(String sheetName) throws IOException 
	{
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		int rowcount=sheet.getLastRowNum();
		workbook.close();
		fis.close();
		return rowcount;
	}
	
	public int getCellCount(String sheetName, int rownum) throws IOException
	{
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		int cellcount = row.getLastCellNum();
		workbook.close();
		fis.close();
		return cellcount;
	}
	
	public String getCellData(String sheetName, int rownum, int colnum) throws IOException 
	{
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell = row.getCell(colnum);
		DataFormatter formatter = new DataFormatter();
		String data;
		try
		{
			data = formatter.formatCellValue(cell); //returns the formatted value of a cell as a string regardless
		}
		catch(Exception e)
		{
			data="";
		}
		workbook.close();
		fis.close();
	 
		return data;
	}
	public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException
	{
		File xlfile=new File(filePath);
		if(!xlfile.exists()) //If file not exists then create new file
		{
			workbook=new XSSFWorkbook();
			fos=new FileOutputStream(filePath);
			workbook.write(fos);
		}
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		
		if(workbook.getSheetIndex(sheetName)==-1)// If sheet not exist then create new sheet
			workbook.createSheet(sheetName);
		sheet=workbook.getSheet(sheetName);
		
		if (sheet.getRow(rowNum) == null) //If row not exists create new row
		{
			sheet.createRow(rowNum);
			row = sheet.getRow(rowNum);
		}
		
		cell = row.createCell(colNum);
		cell.setCellValue(data);
		fos = new FileOutputStream(filePath);
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();
	}
	 

	/*public Object[][] getSheetDataForDataProvider() 
	{ 
		int rows = getRowCount(filePath);
		int cols = getCellCount(filePath, cols);
		Object[][] data = new Object[rows - 1][cols];
	
		for(int i = 1;i < rows;i++) 
		{ 
		// Start from 1 to skip header row      
			for (int j = 0; j < cols; j++) 
			{
				data[i - 1][j] = getCellData(i, j);
			}
		}
		return data;
	}

	public void closeWorkbook() throws IOException 
	{
		if (workbook != null) 
		{ 
			workbook.close();
		}
	}*/
}