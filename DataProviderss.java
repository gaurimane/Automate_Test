package utility;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviderss {
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		String path=".\\testdata\\Agsearch_LoginData.xlsx"; //taking xml file from testData
		ExcelUtility xlutil = new ExcelUtility(path);
		
		int totalrows = xlutil.getRowCount("Sheet1"); //"Sheet1"
		int totalcols = xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols]; // created for two dimension array which can store data
		
		for(int i=1; i<=totalrows;i++) //1 //read the data from xml storing in two dimension array
		{
			for(int j=0;j<totalcols;j++) //0   i is rows j is cols
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i,j); //1,0 //Sheet1
			}
		}
		return logindata; //returning two dimension array
		
	}
}
