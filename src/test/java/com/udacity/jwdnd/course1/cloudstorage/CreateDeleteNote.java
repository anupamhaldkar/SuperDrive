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

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class CreateDeleteNote {

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
            Register register = new Register(driver);
            register.registerNewUser("Anupam", "Haldkar", "AH", "CITY");
            driver.get("http://localhost:"+port+"/login");
            Login login = new Login(driver);
            login.loginUser("AH", "CITY");
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
    public void createNote(){
        noteAdding();
        assertEquals("Title-Drive",home.getNoteTitleText().getText());
        assertEquals("This is testing drive",home.getNoteTitleDescription().getText());
    }

    @Test
    @Order(3)
    public void deleteNote(){
        WebDriverWait wait = new WebDriverWait(driver,30);
        noteAdding();
        home.getDeleteNoteButton().click();
        Result result = new Result(driver);
        threadWait();
        assertEquals("Success",result.getStatus());
        result.getLink().click();
        threadWait();
        home.getNotePane();
        Assertions.assertThrows(Exception.class,()->home.getNoteTitleText().click());

    }

    @Test
    @Order(2)
    public void edit(){
            WebDriverWait webDriverWait = new WebDriverWait(driver,25);
            noteAdding();
            home.getEditNote().click();
            webDriverWait.until(ExpectedConditions.visibilityOf(home.getSubmit()));
            home.editNote("ChangeTitle","ChangeDesc");
            Result result = new Result(driver);
            threadWait();
            assertEquals("Success",result.getStatus());
            result.getLink().click();
            threadWait();
            home.getNotePane();
            webDriverWait.until(ExpectedConditions.visibilityOf(home.getNoteTitleText()));
            assertEquals("ChangeTitle", home.getNoteTitleText().getText());
        assertEquals("ChangeDesc", home.getNoteTitleDescription().getText());
    }

    private void noteAdding(){
        WebDriverWait webDriverWait = new WebDriverWait(driver,20);
        Home home=new Home(driver);
        threadWait();
        home.getNotePane();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(home.getAddNoteButton())).click();
        webDriverWait.until(ExpectedConditions.visibilityOf(home.getSubmit()));
        home.getNoteTitle().sendKeys("Title-Drive");
        home.getDescription().sendKeys("This is testing drive");
        home.getSubmit().click();
        Result result = new Result(driver);
        threadWait();
        result.getLink().click();
        threadWait();
        home.getNotePane();
        webDriverWait.until(ExpectedConditions.visibilityOf(home.getNoteTitleText()));
    }

    public void threadWait(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
