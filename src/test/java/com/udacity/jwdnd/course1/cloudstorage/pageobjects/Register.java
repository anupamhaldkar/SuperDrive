package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Register {
    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signUpButton")
    private WebElement submit;

    @FindBy(id = "signup-success")
    private WebElement signupSuccess;

    @FindBy(id = "signup-error")
    private WebElement signupFail;

    public Register(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void registerNewUser(String firstName, String lastName, String username, String password){
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        submit.click();
    }

    public boolean isSuccessful(){
        return signupSuccess.isDisplayed();
    }

    public boolean isFail(){
        return signupFail.isDisplayed();
    }
}