package com.demostoreqa.PageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPageObjects {
	WebDriver driver;

	public CheckoutPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	public CheckoutPageObjects productQuantityUpdate(String item, String quantity) throws InterruptedException {
		
		// To Locate table
		WebElement CheckoutTable = driver.findElement(By.xpath("//*[@id='checkout_page_container']/div[1]/table"));

		// Locate rows of table
		List<WebElement> rows = CheckoutTable.findElements(By.tagName("tr"));

		// No of rows in table
		int row_count = rows.size();
		System.out.println(row_count);

		// Locate columns of table
		// List<WebElement>
		// columns=CheckoutTable.findElements(By.tagName("td"));

		for (WebElement row : rows) {

			List<WebElement> col = row.findElements(By.tagName("td"));

			int col_count = col.size();

			if (col_count > 0 && (col.get(1).getText().equalsIgnoreCase(item))) {

				col.get(2).findElement(By.name("quantity")).clear();
				col.get(2).findElement(By.name("quantity")).sendKeys(quantity);
				Thread.sleep(500);
				col.get(2).findElement(By.name("submit")).click();

			}

		}

		return this;
	}

	public void removeProduct(String item) throws InterruptedException {
		WebElement CheckoutTable = driver.findElement(By.xpath("//*[@id='checkout_page_container']/div[1]/table"));
		List<WebElement> rows = CheckoutTable.findElements(By.tagName("tr"));
		// int row_count = rows.size();
		for (WebElement row : rows) {
			List<WebElement> column = row.findElements(By.tagName("td"));
			int col_count = column.size();

			if (col_count > 0 && column.get(1).getText().equalsIgnoreCase(item)) {
				column.get(5).findElement(By.name("submit")).click();
				Thread.sleep(500);
			}
		}
	}

	public boolean verifyTotalPriceOfProduct(String item) {
		int count = 0;
		WebElement CheckoutTable = driver.findElement(By.xpath("//*[@id='checkout_page_container']/div[1]/table"));
		List<WebElement> rows = CheckoutTable.findElements(By.tagName("tr"));
		// int row_count=rows.size();
		for (WebElement row : rows) {
			List<WebElement> column = row.findElements(By.tagName("td"));
			int col_count = column.size();

			if (col_count > 0 && column.get(1).getText().equalsIgnoreCase(item)) {

				String quantity = column.get(2).findElement(By.name("quantity")).getAttribute("value");
				// System.out.println("String S="+S);
				float product_quantity = Float.parseFloat(quantity);
				System.out.println("Quantity =" + product_quantity);

				String prod_price = column.get(3).findElement(By.className("pricedisplay")).getText();
				String new_prod_price = prod_price.replaceAll("[$]", "");
				System.out.println("new converted prod price=" + new_prod_price);

				double product_price = Double.parseDouble(new_prod_price);
				// System.out.println("Price = "+product_price);

				double total_product_price = (product_quantity * product_price);
				System.out.println("total calculated price=" + total_product_price);

				String tot_price = column.get(4).findElement(By.className("pricedisplay")).getText();
				String new_tot_price = tot_price.replaceAll("[$]", "");
				double total_price = Double.parseDouble(new_tot_price);

				if (total_product_price == total_price) {
					count++;
					// System.out.println("Test passed: Total Price of Product
					// verfied successfully");
				}
			}
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifySubTotalPrice() {
		double Tot_Product_Price = 0;
		WebElement CheckoutTable = driver.findElement(By.xpath("//*[@id='checkout_page_container']/div[1]/table"));
		List<WebElement> rows = CheckoutTable.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			List<WebElement> column = row.findElements(By.tagName("td"));
			int col_count = column.size();
			if (col_count > 0) {
				String tot_price = column.get(4).findElement(By.className("pricedisplay")).getText();
				String new_tot_price = tot_price.replaceAll("[$]", "");
				double total_price = Double.parseDouble(new_tot_price);

				Tot_Product_Price = Tot_Product_Price + total_price;
			}
		}
		WebElement sub_total_price = driver
				.findElement(By.xpath("//*[@id='checkout_page_container']/div[1]/span/span"));
		String sub_total = sub_total_price.getText();
		String total = sub_total.replaceAll("[$]", "");
		Double Sub_Total_Price = Double.parseDouble(total);
		if (Tot_Product_Price == Sub_Total_Price) {
			return true;
			// System.out.println("Subtotal price is equal to the sum of the
			// Prices of Products present in the cart");
		} else {
			return false;
		}

	}

	public void clickContinue() throws InterruptedException {
		WebElement Continue = driver.findElement(By.xpath("//*[@id='checkout_page_container']/div[1]/a/span"));
		Continue.click();
		Thread.sleep(2000);
	}

	public void entershippingDetails(String country, String state, String email, String FN, String LN, String Add,
			String city, String state2, String country_main, String PS, String phn) {
		Select Country = new Select(driver.findElement(By.id("current_country")));
		Country.selectByValue(country);

		// WebElement BillingAddress = (WebElement)
		// driver.findElement(By.className("wpsc_checkout_table table-1"));
		// List<WebElement> rows =
		// BillingAddress.findElements(By.tagName("tr"));
		// int row_count = rows.size();
		// for(WebElement row : rows)
		// {
		// List<WebElement> column = driver.findElements(By.tagName("td"));
		// column.get(1).findElement(By.wpsc_checkout_form_2)
		// }

		WebElement State = driver.findElement(By.xpath("//*[@id='change_country']/input[2]"));
		State.sendKeys(state);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement Email = driver.findElement(By.id("wpsc_checkout_form_9"));
		Email.sendKeys(email);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement First_Name = driver.findElement(By.id("wpsc_checkout_form_2"));
		First_Name.sendKeys(FN);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement Last_Name = driver.findElement(By.id("wpsc_checkout_form_3"));
		Last_Name.sendKeys(LN);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement Address = driver.findElement(By.id("wpsc_checkout_form_4"));
		Address.sendKeys(Add);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement City = driver.findElement(By.id("wpsc_checkout_form_5"));
		City.sendKeys(city);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement State2 = driver.findElement(By.id("wpsc_checkout_form_6"));
		State2.sendKeys(state2);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		Select CountryMain = new Select(driver.findElement(By.id("wpsc_checkout_form_7")));
		CountryMain.selectByValue(country_main);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement Postal_Code = driver.findElement(By.id("wpsc_checkout_form_8"));
		Postal_Code.sendKeys(PS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement Phone = driver.findElement(By.id("wpsc_checkout_form_18"));
		Phone.sendKeys(phn);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		WebElement RadioBtn = driver.findElement(By.id("shippingSameBilling"));
		RadioBtn.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	public boolean clickOnPurchase() {
		
		WebElement Purchase = driver.findElement(By.xpath("//*[@id='wpsc_shopping_cart_container']/form/div[4]/div/div/span/input"));
		Purchase.click();
		return true;
	}

	public boolean checkDuplicateItems(String Item) {
		int ProductCount = 0;
		WebElement CheckoutTable = driver.findElement(By.className("checkout_cart"));
		List<WebElement> rows = CheckoutTable.findElements(By.tagName("tr"));
		List<WebElement> columns = driver.findElements(By.tagName("td"));
		for (WebElement row : rows) {
			if (columns.get(0).getText().equalsIgnoreCase(Item))
			/* columns.get(1).getText().equalsIgnoreCase(Item) */
			{
				ProductCount++;
			}
		}

		System.out.println("ProductCount:" + ProductCount);

		if (ProductCount > 1) {
			return true;
		} else {
			return false;
		}
	}
}
