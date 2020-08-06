package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "loginButton")
    private WebElement login;

    @FindBy(id = "Logged-out")
    private WebElement loggedOutMessage;

    @FindBy(id = "invalid-credentials-message")
    private WebElement invalidMessage;

    public Login(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void loginUser(String username, String password){
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        login.click();
    }

    public boolean loggedOut(){
        return loggedOutMessage.isDisplayed();
    }

    public boolean invalidAuths(){
        return invalidMessage.isDisplayed();
    }

}
