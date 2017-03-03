package com.demostoreqa.FunctionalTests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.demostore.qa.TestNGlisteners.RetryTest;
import com.demostore.qa.dataprovider.DataProvider;
import com.demostoreqa.PageObjects.AccessoriesPageObject;
import com.demostoreqa.PageObjects.CheckoutPageObjects;
import com.demostoreqa.PageObjects.DemoStoreQADefaultPageObject;
import com.demostoreqa.PageObjects.HomePageObject;
import com.demostoreqa.PageObjects.MacBooksPageObject;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DemoStoreQAFunctionalTest {

	public WebDriver driver;
	public CheckoutPageObjects Checkobj;
	public HomePageObject Homeobj;

	public static final String USERNAME = "extentreports";
	public static final String ACCESS_KEY = "e4c75a95-5726-49da-ad68-1eeba7d8eeea";
	public static final String SURL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
	
	private static ExtentReports report = new ExtentReports("C:\\Selenium Demo Project\\DemoStoreQAReport\\DemoStoreReport.html", true);
	String browserName = null;
	Capabilities cap;

	//@Listeners(com.demostore.qa.TestNGlisteners.TestNGListener.class)
	
	@Parameters({"browser"})
	@BeforeMethod
	public void setUp(String browser) throws MalformedURLException {
		// Running on Local machine
		
	/*		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("http://store.demoqa.com/");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		    Homeobj = new HomePageObject(driver);
		    */
		
		DesiredCapabilities caps = new DesiredCapabilities();
		if(browser.equalsIgnoreCase("firefox"))
		{	
		 caps = DesiredCapabilities.firefox();
		 caps.setCapability("platform", "Windows 7");
	     caps.setCapability("version", "45");
		}
		
		else if(browser.equalsIgnoreCase("chrome"))
		{	
		 
		 caps = DesiredCapabilities.chrome();
	     caps.setCapability("platform", "Windows XP");
	     caps.setCapability("version", "46");
		}
		
		else if(browser.equalsIgnoreCase("InternetExplorer"))
		{	
		 caps = DesiredCapabilities.internetExplorer();
	     caps.setCapability("platform", "Windows 7");
	     caps.setCapability("version", "11");
		}
	    
	    driver = new RemoteWebDriver(new URL(SURL), caps);
		driver.get("http://store.demoqa.com/");
		driver.manage().window().maximize();
   		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

   		cap = ((RemoteWebDriver) driver).getCapabilities();
	    Homeobj = new HomePageObject(driver);
	}

	
	@Test(enabled = true, dataProvider = "UserAdress", dataProviderClass = DataProvider.class)
	public void verifyUserIsAbleToPurchaseSingleProduct(String country, String state, String email, String FN,
			String LN, String Add, String city, String state2, String country_main, String PS, String phn)
			throws InterruptedException {
		
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Product added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Checkobj.clickContinue();
		logger.log(LogStatus.INFO, "Clicked on the Continue button.");
		Checkobj.entershippingDetails(country, state, email, FN, LN, Add, city, state2, country_main, PS, phn);
		Checkobj.clickOnPurchase();
		logger.log(LogStatus.INFO, "Clicked on the Purchase button.");
		System.out.println("Purchase completed successfully");
		
		logger.log(LogStatus.PASS, "Product purchase completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = true, dataProvider = "UserAdress", dataProviderClass = DataProvider.class)
	public void verifyUserIsAbleToPurchaseMultipleProduct(String country, String state, String email, String FN,
			String LN, String Add, String city, String state2, String country_main, String PS, String phn)
			throws InterruptedException

	{
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Homeobj.continueShopping();
		logger.log(LogStatus.INFO, "Continue button clicked.");
		Homeobj.selectMenuItem("Apple TV");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Apple TV added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Checkobj.clickContinue();
		logger.log(LogStatus.INFO, "Clicked on Continue button.");
		Checkobj.entershippingDetails(country, state, email, FN, LN, Add, city, state2, country_main, PS, phn);
		Checkobj.clickOnPurchase();
		logger.log(LogStatus.INFO, "Clicked on Purchase button.");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Purchase completed successfully");
		logger.log(LogStatus.PASS, "Product purchase completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = false, dataProvider = "UserAdress", dataProviderClass = DataProvider.class)
	public void verifyUserCanRemoveProductFromCartAndPurchase(String country, String state, String email, String FN,
			String LN, String Add, String city, String state2, String country_main, String PS, String phn)
			throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Homeobj.continueShopping();
		Homeobj.selectMenuItem("Apple TV");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Apple TV added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Checkobj.removeProduct("Magic Mouse");
		System.out.println("Product Removed successfully");
		logger.log(LogStatus.INFO, "Magic Mouse removed from the cart.");
		Checkobj.clickContinue();
		Checkobj.entershippingDetails(country, state, email, FN, LN, Add, city, state2, country_main, PS, phn);
		Checkobj.clickOnPurchase();
		logger.log(LogStatus.INFO, "Clicked on Purchase button.");
		System.out.println("Purchase completed successfully");
		logger.log(LogStatus.PASS, "Product purchase completed successfully!");
		
	}

	@Test(enabled = true)
	public void verifySubTotalPriceCalcualtionAfterRemovingProductFromCart() throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Homeobj.continueShopping();
		Homeobj.selectMenuItem("Apple TV");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Apple TV added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Checkobj.removeProduct("Magic Mouse");
		System.out.println("Product Removed successfully");
		logger.log(LogStatus.INFO, "Magic Mouse removed from the cart.");
		Checkobj.verifySubTotalPrice();
		logger.log(LogStatus.INFO, "Sub total price is verified.");
		System.out.println("Test Passed successfully");
		logger.log(LogStatus.PASS, "Test case execution completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = false)
	public void verifySubTotalPriceCalcualtionForCartHavingMultipleProducts() throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Homeobj.continueShopping();
		Homeobj.selectMenuItem("Apple TV");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Apple TV added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Checkobj.verifySubTotalPrice();
		System.out.println("Test Passed successfully");
		logger.log(LogStatus.INFO, "Sub total price is verified.");
		logger.log(LogStatus.PASS, "Test case execution completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = false)
	public void verifyProductPriceAfterIncreaseInProductQuantity() throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Checkobj.productQuantityUpdate("Magic Mouse", "5");
		logger.log(LogStatus.INFO, "Magic Mouse quantity updated in the cart.");
		Checkobj.verifyTotalPriceOfProduct("Magic");
		logger.log(LogStatus.PASS, "Test case execution completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = false)
	public void verifyProductPriceAfterDecreaseInProductQuantity() throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Checkobj.productQuantityUpdate("Magic Mouse", "5");
		logger.log(LogStatus.INFO, "Magic Mouse quantity updated in the cart.");
		Checkobj.verifyTotalPriceOfProduct("Magic");
		Checkobj.productQuantityUpdate("Magic Mouse", "3");
		logger.log(LogStatus.INFO, "Magic Mouse quantity updated in the cart.");
		Checkobj.verifyTotalPriceOfProduct("Magic");
		logger.log(LogStatus.PASS, "Test case execution completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = false, retryAnalyzer=RetryTest.class)
	public void verifyDuplicateEntriesInCart() throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Homeobj.continueShopping();
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart for the second time.");
		Checkobj = Homeobj.gotoCheckout();
		Assert.assertEquals(Checkobj.checkDuplicateItems("Magic Mouse"), false);
		logger.log(LogStatus.PASS, "Test case execution completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = false, dataProvider = "UserAdress", dataProviderClass = DataProvider.class)
	public void verifyPurchasingItemsFromDifferentCategories(String country, String state, String email, String FN,
			String LN, String Add, String city, String state2, String country_main, String PS, String phn)
			throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Homeobj.continueShopping();
		Homeobj.hoverMenuAndClick("iPads");
		Homeobj.selectMenuItem("Apple iPad 2 16GB, Wi-Fi, 9.7in – Black");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Apple iPad 2 16GB added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Checkobj.clickContinue();
		Checkobj.entershippingDetails(country, state, email, FN, LN, Add, city, state2, country_main, PS, phn);
		Assert.assertEquals(Checkobj.clickOnPurchase(), true);
		logger.log(LogStatus.PASS, "Test case execution completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = false)
	public void verifySubtotalForItemsFromDiffCategories() throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Homeobj.continueShopping();
		Homeobj.hoverMenuAndClick("iPads");
		Homeobj.selectMenuItem("Apple iPad 2 16GB, Wi-Fi, 9.7in – Black");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Apple iPad 2 16GB added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Assert.assertEquals(Checkobj.verifySubTotalPrice(), true);
		logger.log(LogStatus.PASS, "Test case execution completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@Test(enabled = false)
	public void verifyEachProductPriceAddedFromDiffCategories() throws InterruptedException {
		browserName = cap.getBrowserName();
		ExtentTest logger = report.startTest(Thread.currentThread().getStackTrace()[1].getMethodName()+ " : "+browserName);
		Homeobj.hoverMenuAndClick("Accessories");
		Homeobj.selectMenuItem("Magic Mouse");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Magic Mouse added to the cart.");
		Homeobj.continueShopping();
		Homeobj.hoverMenuAndClick("iPods");
		Homeobj.selectMenuItem("Apple iPod touch Large");
		Homeobj.addToCart();
		logger.log(LogStatus.INFO, "Apple iPod touch Large added to the cart.");
		Checkobj = Homeobj.gotoCheckout();
		Assert.assertEquals(Checkobj.verifyTotalPriceOfProduct("Magic Mouse"), true);
		Assert.assertEquals(Checkobj.verifyTotalPriceOfProduct("Apple iPod touch Large"), true);
		logger.log(LogStatus.PASS, "Test case completed successfully!");
		report.endTest(logger);
		report.flush();
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		ExtentTest logger=null;
		if(result.getStatus()==ITestResult.FAILURE)
		{
			String screenshot_path=Homeobj.captureScreenshot(driver, "Error");
			logger.log(LogStatus.FAIL, "Test case failed!");
			
		}
		report.endTest(logger);
		report.flush();
		driver.quit();
	}

}