package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.Home;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.Login;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.Register;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.Result;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();

	}

	@BeforeEach
	public void beforeEach() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("useAutomationExtension",false);
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments("--remote-debugging-port=9225");

		this.driver = new ChromeDriver(chromeOptions);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test()
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test()
	public void WithoutLoginHome(){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		driver.get("http://localhost:"+port+"/home");
		assertEquals("Login",driver.getTitle());

	}
	@Test()
	public void getSignUp(){
		driver.get("http://localhost:"+port+"/signup");
		assertEquals("Sign Up",driver.getTitle());
	}

	@Test()
	public void CheckSignup(){
		driver.get("http://localhost:"+port+"/signup");
		Register register = new Register(driver);
		register.registerNewUser("Anupam","Haldkar","AH","CITY");
		Assertions.assertTrue(register.isSuccessful());
		driver.get("http://localhost:"+port+"/login");
		Login login = new Login(driver);
		login.loginUser("AH","CITY");
		assertEquals("Home",driver.getTitle());
		Home home = new Home(driver);
		try{
			Thread.sleep(3000);
		}catch (Exception e){
			e.printStackTrace();
		}
		home.logOut();
		login = new Login(driver);
		assertTrue(
				login.loggedOut()
		);
	}

}
