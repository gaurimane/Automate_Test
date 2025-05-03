package utility;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseTestClass;

public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkreporter; //UI of the report
	public ExtentReports extent; //populate common info on the report
	public ExtentTest test; //create test case entries in the report and update status of the test methods
	String reportname;
	
	public void onTestStart(ITestResult result) {
	    // not implemented
	  }
	 public void onStart(ITestContext context) {
	  	 
		 String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		 reportname = "Test-Report-" + timestamp + ".html";
		 sparkreporter = new ExtentSparkReporter(".\\reports\\" + reportname);
		 
		 sparkreporter.config().setDocumentTitle("Agsearch Automation Report");
		 sparkreporter.config().setReportName("Agsearch Functional Testing");
		 sparkreporter.config().setTheme(Theme.STANDARD);
		 
		 extent = new ExtentReports();
		 extent.attachReporter(sparkreporter); //attach the file UI with common info
			 
		 extent.setSystemInfo("Application", "Agsearch");
		 extent.setSystemInfo("Environment", "QA");
		 //extent.setSystemInfo("Tester Name", "Gauri Mane");
		 extent.setSystemInfo("Module", "Front-End");
		 extent.setSystemInfo("Sub-Module", "Search Engine");
		 extent.setSystemInfo("Module", System.getProperty("user.name"));
		 
		 String os = context.getCurrentXmlTest().getParameter("os");
		 extent.setSystemInfo("Operating System", os);
		 
		 String browsern = context.getCurrentXmlTest().getParameter("browser");
		 extent.setSystemInfo("Browser Name", browsern);
		 
		List<String> includegroups = context.getCurrentXmlTest().getIncludedGroups();
		 if(includegroups.isEmpty())
		 {
			 extent.setSystemInfo("Groups", includegroups.toString());
		 }

		  }
	 public void onTestSuccess(ITestResult result) {
		 // Log test success to ExtentReports
	        test = extent.createTest(result.getTestClass().getName());
	        test.assignCategory(result.getMethod().getGroups()); // to display groups in report
	        test.log(Status.PASS, "Test passed: " + result.getName());
	  }

	  public void onTestFailure(ITestResult result) {
		  // Log test failure to ExtentReports
		  	test = extent.createTest(result.getTestClass().getName());
	        test.assignCategory(result.getMethod().getGroups()); 
	        test.log(Status.FAIL, "Test failed: " + result.getName());
	        test.log(Status.INFO, result.getThrowable().getMessage());
	        
	        try
	        {
	        	String imgpath = new BaseTestClass().CaptureScreen(result.getName());
	        	test.addScreenCaptureFromPath(imgpath);
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	  }
	
	  public void onTestSkipped(ITestResult result) {
		  // Log test skipped to ExtentReports
		  test = extent.createTest(result.getTestClass().getName());
	        test.assignCategory(result.getMethod().getGroups()); 
	        test.log(Status.SKIP, "Test skipped: " + result.getName());
	        test.log(Status.INFO, result.getThrowable().getMessage());
	  }
		  
	  public void onFinish(ITestContext context) {
		// Finalize the report after all tests have finished
	        extent.flush();
	        String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+reportname;
	        File extreport = new File(pathOfExtentReport);
	        try
	        {
	        	Desktop.getDesktop().browse(extreport.toURI());
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
		  }

}
