package automatinpracticewebsite.automatinpracticewebsite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest extends MySetUps {

	@BeforeTest
	public void mysetup() throws SQLException {

		setup();
	}

	@Test(priority = 1, enabled = false)
	public void SingUp() throws SQLException {
		driver.findElement(By.cssSelector("a[href='/login']")).click();

		String query = "SELECT * FROM customers WHERE customerNumber = 121;";
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			// Read Data from Data Base
			String firstName = rs.getString("contactFirstName").trim();
			String lastName = rs.getString("contactLastName").trim();
			String fullName = firstName + " " + lastName;
			email = firstName + lastName + "@mail.com";
			String fullDate = rs.getString("date_of_birth").trim();
			addressLine = rs.getString("addressLine1");
			countryFromDb = rs.getString("country");
			CompanyName = rs.getString("customerName");
			PostalCode = rs.getString("postalCode");
			City = rs.getString("city");
			State = rs.getString("state");
			PhoneNumber = rs.getString("phone");

			// insert Name and Email
			driver.findElement(By.cssSelector("input[data-qa='signup-name']")).sendKeys(fullName);
			driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(email);
			driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();

			int choice = Rand.nextInt(2);
			if (choice == 0) {
				driver.findElement(By.id("id_gender1")).click();
			} else {
				driver.findElement(By.id("id_gender2")).click();
			}

			driver.findElement(By.id("password")).sendKeys(password);

			// to split the date
			String[] parts = fullDate.split("-");
			String year = parts[0];
			String month = parts[1].startsWith("0") ? parts[1].substring(1) : parts[1];
			String day = parts[2].startsWith("0") ? parts[2].substring(1) : parts[2];

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			Select selectDay = new Select(
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[data-qa='days']"))));
			selectDay.selectByValue(day);

			Select selectMonth = new Select(
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[data-qa='months']"))));
			selectMonth.selectByValue(month);

			Select selectYear = new Select(
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[data-qa='years']"))));
			selectYear.selectByValue(year);

			WebElement newsletterCheckbox = driver.findElement(By.id("newsletter"));
			if (!newsletterCheckbox.isSelected()) {
				newsletterCheckbox.click();
			}

			driver.findElement(By.id("first_name")).sendKeys(firstName);

			driver.findElement(By.id("last_name")).sendKeys(lastName);

			driver.findElement(By.id("company")).sendKeys(CompanyName);

			driver.findElement(By.id("address1")).sendKeys(addressLine);

			String countryFromDb = rs.getString("country").trim();
			Select selectCountry = new Select(driver.findElement(By.id("country")));
			selectCountry.selectByVisibleText(countryFromDb);

			driver.findElement(By.id("state")).sendKeys(State);

			driver.findElement(By.id("city")).sendKeys(City);

			driver.findElement(By.id("zipcode")).sendKeys(PostalCode);

			driver.findElement(By.id("mobile_number")).sendKeys(PhoneNumber);

			driver.findElement(By.cssSelector(".btn.btn-default")).click();

			driver.findElement(By.cssSelector("a[data-qa='continue-button']")).click();

			Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Logout']")).isDisplayed(),
					"User not logged in after registration");

		}
	}

	@Test(priority = 2, enabled = true)
	public void Login() throws SQLException {
		driver.findElement(By.cssSelector("a[href='/login']")).click();

		String query = "SELECT * FROM customers WHERE customerNumber = 555;";
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);

		while (rs.next()) {

			String firstName = rs.getString("contactFirstName").trim();
			String lastName = rs.getString("contactLastName").trim();
			String fullName = firstName + " " + lastName;
			email = firstName + lastName + "@mail.com";

			driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(email);

			driver.findElement(By.cssSelector("input[data-qa='login-password']")).sendKeys(password);

			driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();

			WebElement logoutButton = driver.findElement(By.cssSelector("a[href='/logout']"));
			Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is not visible.");

		}
	}

	@Test(priority = 3, enabled = true)
	public void Searchproduct() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement productsLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/products']")));
		productsLink.click();

		List<String> brandOptions = new ArrayList<>(Arrays.asList("Polo", "H&M", "Madame", "Biba"));
		List<String> addedProducts = new ArrayList<>();
		for (int i = 0; i < 4 && !brandOptions.isEmpty(); i++) {
			int index = Rand.nextInt(brandOptions.size());
			String selectedBrand = brandOptions.remove(index);

			WebElement searchBox = driver.findElement(By.id("search_product"));
			searchBox.clear();
			searchBox.sendKeys(selectedBrand);
			driver.findElement(By.id("submit_search")).click();

			List<WebElement> productBlocks = driver.findElements(By.cssSelector(".productinfo.text-center"));

			if (!productBlocks.isEmpty()) {
				int randomIndex = Rand.nextInt(productBlocks.size());
				WebElement selectedBlock = productBlocks.get(randomIndex);

				String productName = selectedBlock.findElement(By.tagName("p")).getText().replaceAll("\\s+", " ")
						.trim();
				addedProducts.add(productName);

				WebElement addButton = selectedBlock.findElement(By.xpath(".//a[contains(text(),'Add to cart')]"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);

				WebElement continueBtn = wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Continue Shopping')]")));
				continueBtn.click();

			}
		}

		WebElement cartLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view_cart']")));
		cartLink.click();

		// ✅ Bring Name of Product from Cart
		List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_description h4 a"));
		List<String> cartNames = new ArrayList<>();
		for (WebElement item : cartItems) {
			cartNames.add(item.getText().replaceAll("\\s+", " ").trim());
		}

		// ✅ Check Products in Cart
		for (String addedProduct : addedProducts) {
			boolean foundInCart = cartNames.stream().anyMatch(cartName -> cartName.equalsIgnoreCase(addedProduct));
			Assert.assertTrue(foundInCart, addedProduct);
		}

	}

	@Test(priority = 4, enabled = true)
	public void Checkout() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement cartLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view_cart']")));
		cartLink.click();

		Thread.sleep(2000);

		List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_description h4 a"));
		List<String> cartNames = new ArrayList<>();
		for (WebElement item : cartItems) {
			cartNames.add(item.getText().replaceAll("\\s+", " ").trim());
		}

		// ✅ Delete one Produt From Cart
		List<WebElement> deleteButtons = driver.findElements(By.cssSelector(".cart_quantity_delete"));
		if (!deleteButtons.isEmpty()) {
			int randomIndex = Rand.nextInt(deleteButtons.size());
			String deletedItemName = cartNames.get(randomIndex);
			deleteButtons.get(randomIndex).click();
		} else {
			return;
		}

	}

	@Test(priority = 5, enabled = true)
	public void Contactus() throws SQLException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement cartLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/contact_us']")));
		cartLink.click();

		String query = "SELECT * FROM customers WHERE customerNumber = 555;";
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);

		while (rs.next()) {

			String firstName = rs.getString("contactFirstName").trim();
			String lastName = rs.getString("contactLastName").trim();
			String fullName = firstName + " " + lastName;
			email = firstName + lastName + "@mail.com";

			driver.findElement(By.cssSelector("input[data-qa='name']")).sendKeys(fullName);

			driver.findElement(By.cssSelector("input[data-qa='email']")).sendKeys(email);

			driver.findElement(By.id("message")).sendKeys("This is a test message from automation script.");

			WebElement uploadInput = driver.findElement(By.name("upload_file"));
			uploadInput.sendKeys("C:\\Users\\hp\\OneDrive\\Documents\\ShareX\\Screenshots\\2025-06");

			WebElement submit = driver.findElement(By.cssSelector(".btn.btn-primary.pull-left.submit_form"));
			submit.click();

			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.dismiss();

		}
	}

	@Test(priority = 6, enabled = true)
	public void Logout() {
		WebElement cartLink = driver.findElement(By.cssSelector("a[href='/logout']"));
		cartLink.click();
	}
}
