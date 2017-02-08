package com.demostore.qa.TestNGlisteners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTest implements IRetryAnalyzer {

	int minretrycount = 0, maxretrycount = 2;
		
	public boolean retry(ITestResult result) {
	
		if(minretrycount <= maxretrycount)
		{
			System.out.println("The following test case is failing:"+result.getName());
			System.out.println("Retrying the failed test case. The retry count is:"+(minretrycount+1));
	        minretrycount++;
	        
	        return true;
		}
		return false;
	}

}