package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Result {

    public Result(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(tagName = "a")
    private WebElement link;

    @FindBy(tagName = "h1")
    private WebElement hw;

    public String getStatus(){
        return hw.getText();
    }

    public WebElement getLink(){
        return link;
    }
}
