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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class MySetUps {

	WebDriver driver = new ChromeDriver();
	Random Rand = new Random();

	String TheWebSiteUrl = "https://www.automationexercise.com/";

	Connection con;
	Statement stmt;
	ResultSet rs;

	int customerNumber;
	String customerName;
	String contactFirstName;
	String email;
	String password = "@12345HelmyJ@mous";
	String addressLine;
	String countryFromDb;
	String CompanyName;
	String PostalCode;
	String City;
	String State;
	String PhoneNumber;

	public void setup() throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "246846");
		driver.get(TheWebSiteUrl);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.manage().window().maximize();

	}

}
