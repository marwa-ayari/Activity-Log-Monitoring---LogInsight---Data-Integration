package routines;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.lang.Thread;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;



public class HTML_Scraping_Tools {

   
	public static ArrayList<HashMap<String,String>> listOfReviewsCapterra() throws InterruptedException {
		
	    // Set the path to the Microsoft Edge webdriver executable
	    System.setProperty("webdriver.edge.driver", "C:/Users/msi/Downloads/edgedriver_win64/msedgedriver.exe");
	    System.setProperty("webdriver.edge.driver.capabilities.useChromium", "true");
	    
	    // Create a new instance of the EdgeDriver
	    WebDriver driver = new EdgeDriver();

	    // Navigate to the website
	    driver.get("https://www.capterra.com/p/246747/Talend-Open-Studio/reviews/");
	    //Wait for loading
	    Thread.sleep(1000);
	    
		// Click on Accept cookies button if it exists
	    try {
	    		WebElement acceptCookiesButton = driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']"));
	    		acceptCookiesButton.click();
	    }catch (org.openqa.selenium.NoSuchElementException e) { }

	    // Find the element containing the reviews and iterate through each review
	    WebElement reviewContainer = driver.findElement(By.xpath("//div[@class='gtm-review-section']"));
 
	    List<WebElement> reviews = reviewContainer.findElements(By.xpath(".//div[@class='nb-m-auto nb-block md:nb-flex']"));

	    ArrayList<HashMap<String,String>> returnReviews = new ArrayList<HashMap<String,String>>();

	    for (WebElement review : reviews) {
	    	
	    	//User Job
	    	WebElement job = review.findElement(By.xpath(".//div[@class='nb-text-sm nb-ml-xs sm:nb-ml-xl']//div[2]"));
	        String userJob = job.getText();
	        
	        //Industry
	        WebElement companyInfos = review.findElement(By.xpath(".//div[@class='nb-text-sm nb-ml-xs sm:nb-ml-xl']//div[3]"));
	        String companyInfosText = companyInfos.getText();
	        String Industry = companyInfosText.substring(0, companyInfosText.indexOf(",")-1);
	        
	        //Number Of Employees
	        String nbrEmployees = companyInfosText.substring(companyInfosText.indexOf(",")+2);
	        
	        //User Experience
	        WebElement experience = review.findElement(By.xpath(".//div[@class='nb-text-sm nb-ml-xs sm:nb-ml-xl']//div[4]"));
	        String userExperience = experience.getText().replace("Used the software for: ", "");
	        
	        //Review Date
	        List<WebElement> date = review.findElements(By.xpath(".//div[@class='nb-text-sm nb-text-gray-300 nb-w-full nb-pt-xs']//div[@class='nb-flex nb-items-center nb-my-3xs']"));
	        String reviewDate = date.get(1).getText();
	            
	        //Review Title
	        WebElement titleElement = review.findElement(By.xpath(".//div[@class='nb-type-lg-bold nb-mt-2xl md:nb-mt-0']"));
	        String title = titleElement.getText();
	        title = title.substring(1, title.length()-1);
	        
	        //Review Text
	        List<WebElement> textElement = review.findElements(By.xpath(".//div[@class='nb-w-full nb-h-auto']//div[@class='nb-text-md nb-my-xl']"));
	        String text = "";
	        for (WebElement e : textElement)
	        	text = text + e.getText() + " ";	        
	        
	        
	        
	        // Store the extracted information in a data structure, such as a list of reviews
	        HashMap<String,String> map = new HashMap<String,String>();
	        map.put("Title", title);
	        map.put("User_Job", userJob);
	        map.put("Industry", Industry);
	        map.put("Nbr_Employees", nbrEmployees);
	        map.put("User_Experience", userExperience);
	        map.put("Text", text);
	        map.put("Date", reviewDate);
	        
	        returnReviews.add(map);
	    }
	    
	    driver.quit();
	    return returnReviews;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static ArrayList<HashMap<String,String>> listOfReviewsGartner() throws InterruptedException {
	    // Set the path to the Microsoft Edge webdriver executable
	    System.setProperty("webdriver.edge.driver", "C:/Users/msi/Downloads/edgedriver_win64/msedgedriver.exe");
	    System.setProperty("webdriver.edge.driver.capabilities.useChromium", "true");
	    
	    // Create a new instance of the EdgeDriver
	    WebDriver driver = new EdgeDriver();

	    // Navigate to the website
	    driver.get("https://www.gartner.com/reviews/market/data-integration-tools/vendor/talend/reviews");

	    Thread.sleep(5000);
	    
		// Accepter les cookies en cliquant sur le bouton "Accepter tout"
	    try {
    			WebElement acceptCookiesButton = driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']"));
    			acceptCookiesButton.click();
	    }catch (org.openqa.selenium.NoSuchElementException e) {
	    	
	    }catch(org.openqa.selenium.ElementClickInterceptedException ex ) {
			new Actions(driver).moveByOffset(5, 5).click().perform();
	    	WebElement acceptCookiesButton = driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']"));
			acceptCookiesButton.click();	    	
	    }

	    ArrayList<HashMap<String,String>> returnReviews = new ArrayList<HashMap<String,String>>();
	    
	    //12 Pages of reviews
	    boolean i=true;
	    while( i ) {
	    	
	    	
	    	WebElement reviewContainer = driver.findElement(By.xpath("//ul[@class='all-reviews']"));
	    	 
		    List<WebElement> reviews = reviewContainer.findElements(By.xpath(".//div[@class='review']"));

		    for (WebElement review : reviews) {
		    	
		    	//Rating
		    	WebElement  rate = review.findElement(By.xpath(".//div[@class='ratingNumber']"));
		        String Rating = rate.getText();
		        
		        //Date
		    	WebElement  date = review.findElement(By.xpath(".//div[@class='ratingLabel']"));
		        String reviewDate = date.getText();
		        
		        //Product Name
		    	WebElement  product = review.findElement(By.xpath(".//div[@class='product-name-link']"));
		        String productName = product.getText().replace("Product: ", "");
		        
		        //Title
		    	WebElement  title = review.findElement(By.xpath(".//h3[@class='review-headline']"));
		        String Title = title.getText();
		        
		        //Reviewer Function
		    	WebElement  function = review.findElement(By.xpath(".//ul[@class='review-details']//li[1]"));
		        String reviewerFunction = function.getText().replace("Reviewer Function: ", "");	
		        
		        //Company Size
		    	WebElement  company = review.findElement(By.xpath(".//ul[@class='review-details']//li[2]"));
		        String companySize = company.getText().replace("Company Size: ", "");	        
		        
		        //Industry
		    	WebElement  industry = review.findElement(By.xpath(".//ul[@class='review-details']//li[3]"));
		        String Industry = industry.getText().replace("Industry: ", "");
		        
		        //Review Text
		    	WebElement  text = review.findElement(By.xpath(".//div[@class='uxd-truncate-text']"));
		        String reviewerText = text.getText();
		        
		        
		        
		        
		        // Store the extracted information in a data structure, such as a list of reviews
		        HashMap<String,String> map = new HashMap<String,String>();
		        map.put("Rate", Rating);
		        map.put("Date", reviewDate);
		        map.put("Product_Name", productName);
		        map.put("User_Function", reviewerFunction);
		        map.put("Company_Size", companySize);
		        map.put("Industry", Industry);
		        map.put("Title", Title);
		        map.put("Text", reviewerText);
		        
		        returnReviews.add(map);
		    }
	    	
	    	
		    
		    
		    
		    
		    
	    	
	    	//Go to Next Page if it exists and set i=false if it doesn't
		    try {
		    		
		    		WebElement nextPageButton = driver.findElement(By.xpath("//a[@data-pagination-type='Next page']"));
		    		//nextPageButton.click();
		    		Actions actions = new Actions(driver);
		    		actions.moveToElement(nextPageButton).click().perform();
		    	
		    } catch(org.openqa.selenium.ElementClickInterceptedException ex ) {
		    	new Actions(driver).moveByOffset(5, 5).click().perform();
	    		WebElement nextPageButton = driver.findElement(By.xpath("//a[@data-pagination-type='Next page']"));
	    		//nextPageButton.click();
	    		Actions actions = new Actions(driver);
	    		actions.moveToElement(nextPageButton).click().perform();
		    	
		    	
		    }catch (org.openqa.selenium.NoSuchElementException e) {
		        i=false;
		    }
		    
		    
		    
		    
		    	    	
	    }

	    
	    driver.quit();
	    return returnReviews;
	}
	
}