package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseTestClass {
	
public static WebDriver driver;
public Logger logger;
public Properties p;
	
	@BeforeClass(groups={"sanity","regression","master","LoginDD"})
	@Parameters({"os","browser"})
	public void LaunchApp(String os,String br) throws IOException
	{
		//Loading config.properties file
		FileReader file= new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		logger = LogManager.getLogger(BaseTestClass.class);
		
		if(p.getProperty("execution_environment").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			// OS
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Plateform.WIN11)
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Plateform.MAC)
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Plateform.LINUX)
			}
			else
			{
				System.out.println("No Maching OS...");
				return;
			}
			
			//BROWSERS
			switch(br.toLowerCase())
			{
				case "chrome" : capabilities.setBrowserName("chrome");
				break;
				case "edge" : capabilities.setBrowserName("edge");
				break;
				default : System.out.println("No Browser name..");
				return;
			}
		}		
		
		if(p.getProperty("execution_environment").equalsIgnoreCase("remote"))
		{
			switch(br.toLowerCase())
			{
				case "chrome" : driver = new ChromeDriver();
				break;
				case "edge" : driver = new EdgeDriver();
				break;
				default : System.out.println("Invalid Browser name..");
				return;
			}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//driver.get(p.getProperty("agURL")); //reading url from config.properties file
		driver.get(p.getProperty("agLoginUrl")); // only for login url reading
		driver.manage().window().maximize();
	}

	@AfterClass(groups={"sanity","regression","master","LoginDD"})
	public void TearDown()
	{
		driver.quit();
	}
	
	public void generaterandomdata()
	{
		//RandomGenerator random_string =  RandomGenerator.of("ghs");
				//RandomStringUtils.randomAlphabetic(4);
	}
	
	public String CaptureScreen(String testname) throws IOException
	{
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		TakesScreenshot takescreenshot = (TakesScreenshot) driver;
		File sourcefile = takescreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetfilepath = System.getProperty("user.dir")+"\\screenshots\\" + testname + "_" + timestamp +".png";
		File targetfile = new File(targetfilepath);
		
		sourcefile.renameTo(targetfile);
		return targetfilepath;
	}

}
