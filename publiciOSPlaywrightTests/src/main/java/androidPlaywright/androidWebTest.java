// Created by Christopher Alton
// Version 1.0
// Updated 08-26-2025
package androidPlaywright;

//****** These are the JAVA dependencies required to run this test ******
//****** Without these, the test will be unable to run ******
//****** as these tell the test how to handle the assorted timeouts and ******
//****** the protocols to access the cloud for automation ******
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Playwright Dependencies to actually run Playwright Tests
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

// Google GSON Dependencies for Perfecto Reportium Support
import com.google.gson.JsonObject;
import com.google.gson.*;

// ****** These are my dependencies that handle my log ins, cloud names ******
// ****** as well as my reporting utilities ******
import myUtilities.logins;
import myUtilities.perfectoReportHelperV1;

public class androidWebTest {

//****** These are the strings that control the device details ******
//****** We need to set the host string to the cloud short name ******
//****** We need to set the myDUT to the device ID ******
	
	private static String host = "testing";
	private static String myDUT = "abcd1234";

	    public static void main(String[] args) throws MalformedURLException, IOException {
	    	
	    	String myWUT = "https://the-internet.herokuapp.com/login";
	    	String google = "https://www.google.com";
			String myToken = logins.getToken(host);	
			
			String userPath = "//*[@id=\"username\"]";
			String passPath = "//*[@id=\"password\"]";
			String loginButton = "//*[@class=\"radius\"]";
			String logoutButton = "//*[@class=\"button secondary radius\"]";
	    	
			String userName = "tomsmith";
			String passWord = "SuperSecretPassword!";
			
			String secureArea = "//*[text()=\" Secure Area\"]";

			String testName = "perfecto-Playwright-androidOS";
			String jobname = "perfecto-Playwright";
			String projectName = "perfecto-Playwright";
			String projectversion = "1.0";
			
			Playwright playwright = Playwright.create();
	            JsonObject capabilities = new JsonObject();
	            capabilities.addProperty("platformName", "Android");
	            capabilities.addProperty("deviceName", myDUT);
	            capabilities.addProperty("securityToken", myToken);

	            String caps = URLEncoder.encode(capabilities.toString(), "utf-8");
	            String hostUrl = "wss://" + host + ".perfectomobile.com/websocket?" + caps;
	            Browser browser = playwright.chromium().connect(hostUrl);

	            System.out.println("Starting Playwright Test");
	            

	            BrowserContext context = browser.newContext(
	            		new Browser.NewContextOptions()
	            		.setViewportSize(null)
	            		);      
	            
//	            Page page = browser.newPage();
	            Page page = context.newPage();	
	            
		        try { 
	          //test start
	            Map<String, Object> paramsTestStart = new HashMap<>();
	            paramsTestStart.put("name", testName);
	            //tags
	            paramsTestStart.put("tags", List.of("playwright", "support"));         
	            //job
	            paramsTestStart.put("jobName",jobname);
	            paramsTestStart.put("jobBranch", "perfecto-sampleCode");
	            paramsTestStart.put("jobNumber", 1);         
	            //project
	            paramsTestStart.put("projectName", projectName);
	            paramsTestStart.put("projectVersion", projectversion);
	            
	            page.evaluate("perfecto:report:testStart", new Gson().toJson(paramsTestStart));
	            
	            Map<String, Object> paramsStepStart = new HashMap<>();
	            
	            paramsStepStart.clear();
	            paramsStepStart.put("name", "Goto myWUT");
	            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
	            System.out.println("Goto myWUT");         
	            page.navigate(myWUT);
	                        
	            paramsStepStart.clear();
	            paramsStepStart.put("name", "Type in userName");
	            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
	            System.out.println("Type in userName");
				page.click(userPath);
				page.fill(userPath,userName);
				
	            paramsStepStart.clear();
	            paramsStepStart.put("name", "Type in passWord");
	            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
	            System.out.println("Type in passWord");
				page.click(passPath);
				page.fill(passPath,passWord);
				
	            paramsStepStart.clear();
	            paramsStepStart.put("name", "Click Log In");
	            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
				System.out.println("Click Log In");
				page.click(loginButton);
				
				try {
	            paramsStepStart.clear();
	            paramsStepStart.put("name", "Verify Login Page");
	            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
				System.out.println("Verify Login Page");
				Locator myElement = page.locator(secureArea);
				assertThat(myElement).isVisible();

				if (myElement.isVisible()) {
		            paramsStepStart.clear();
		            paramsStepStart.put("name", "Login Page is Visible");
		            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
					System.out.println("Login Page is Visible");
				} else {
					paramsStepStart.clear();
		            paramsStepStart.put("name", "Login Page is Not Visible");
		            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
					System.out.println("Login Page is Not Visible");
				}
			} catch (Exception e) {
				System.out.println("Check to see why the element was not found");
			}				

	            paramsStepStart.clear();
	            paramsStepStart.put("name", "Log Out of TestPage");
	            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
				System.out.println("Log Out of TestPage");
				page.click(logoutButton);
				
	            paramsStepStart.clear();
	            paramsStepStart.put("name", "Move Browser to a Clean Page");
	            page.evaluate("perfecto:report:stepStart", new Gson().toJson(paramsStepStart));
				System.out.println("Move Browser to a Clean Page");
				page.navigate(google);
				
				Thread.sleep(2000);
				
				//test stop
				Map<String, Object> paramsTestStop = new HashMap<>();
				paramsTestStop.put("success", true);			                    
				page.evaluate("perfecto:report:testEnd", new Gson().toJson(paramsTestStop));
				System.out.println(paramsTestStop);
				
	        } catch (Exception exception) {
	            exception.printStackTrace();
				System.err.println(exception.getMessage());
				
				Map<String, Object> paramsTestStop = new HashMap<>();
				paramsTestStop.put("success", false);
				paramsTestStop.put("failureDescription", "Review Test");			                    
				page.evaluate("perfecto:report:testEnd", new Gson().toJson(paramsTestStop));
				System.out.println(paramsTestStop);
	        	
	    } finally {
	    	browser.close();
    	
// ****** This code calls the executionId parameter ******
// ****** and will generate and print the execution report URL ******
	    
	    	browser.close();
	    	
	    	String reportUrl = perfectoReportHelperV1.getLatestReportUrl(host, jobname, myToken);
	    	System.out.println("Report Link\r\n" + reportUrl);
	    }
}}