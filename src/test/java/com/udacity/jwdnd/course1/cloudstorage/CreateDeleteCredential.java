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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateDeleteCredential {
        @LocalServerPort
        private Integer port;

        private WebDriver driver;

        private Home home;

        @BeforeAll
        private static void beforeAll() {
            WebDriverManager.chromedriver().setup();
        }

        @BeforeEach
        public void beforeEach(){
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--remote-debugging-port=9225");
            driver = new ChromeDriver(options);

            driver.get("http://localhost:"+port+"/signup");
            Register register= new Register(driver);
            register.registerNewUser("Anupam", "Haldkar", "AH", "CITY");

            driver.get("http://localhost:"+port+"/login");
            Login loginPage = new Login(driver);
            loginPage.loginUser("AH", "CITY");
            home = new Home(driver);
        }
        @AfterEach
        public void afterEach() {
            if (this.driver != null) {
                driver.quit();
            }
        }



    @Test
    @Order(1)
    public void create(){
        credentialAdding("url","AH","PASS");
        assertEquals("url",home.getCredentialUrlText().getText());
        assertEquals("AH",home.getCredentialUsername().getText());
        assertFalse(home.getCredentialPassword().equals("PASS"));
    }

    @Test
    @Order(3)
    public void delete(){
        //WebDriverWait wait = new WebDriverWait(driver,);
        credentialAdding("ah.io","AH","PASS");
        home.getDeleteCredentialButton().click();
        Result result = new Result(driver);
        threadWait();
        assertEquals("Success",result.getStatus());
        result.getLink().click();
        threadWait();
        home.getCredentialTab().click();
        Assertions.assertThrows(Exception.class,()->home.getCredentialUrlText().click());

    }

    @Test
    @Order(2)
    public void edit(){
        WebDriverWait webDriverWait = new WebDriverWait(driver,25);
        credentialAdding("Initialurl","InitialAH","InitialPASS");
        home.getEditCredentialButton().click();
        webDriverWait.until(ExpectedConditions.visibilityOf(home.getCredentialSubmit()));
        home.editCredential("ChangeUrl","ChangeUser","ChangePass");
        Result result = new Result(driver);
        threadWait();
        assertEquals("Success",result.getStatus());
        result.getLink().click();
        threadWait();
        home.getCredentialTab().click();
        webDriverWait.until(ExpectedConditions.visibilityOf(home.getCredentialUrlText()));
        assertEquals("ChangeUrl", home.getCredentialUrlText().getText());
        assertEquals("ChangeUser", home.getCredentialUsername().getText());
        assertFalse(home.getCredentialPassword().equals("ChangePass"));
    }

    private void credentialAdding(String add,String name,String sword){
        WebDriverWait webDriverWait = new WebDriverWait(driver,30);
        threadWait();
        home.getCredentialTab().click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(home.getAddCredentialButton())).click();
        webDriverWait.until(ExpectedConditions.visibilityOf(home.getCredentialSubmit()));
        home.getCredentialUrl().sendKeys(add);
        home.getCredentialName().sendKeys(name);
        home.getCredentialPass().sendKeys(sword);
        home.getCredentialSubmit().click();
        Result result = new Result(driver);
        threadWait();
        result.getLink().click();
        threadWait();
        home.getCredentialTab().click();
        webDriverWait.until(ExpectedConditions.visibilityOf(home.getCredentialUrlText()));
    }

    public void threadWait(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
