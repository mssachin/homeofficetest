package com.homeoffice.assessment.pageobjects;

import com.homeoffice.assessment.utilities.WebUtilityFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VehicleInformationLandingPage {

    private WebDriver driver;
    private static final By startButtonLocator = By.cssSelector("a[class$='pub-c-button--start']");

    public VehicleInformationLandingPage(WebDriver driver) {
        this.driver = driver;
    }


    public VehicleEnquiryPage navigateToVehicleEnquiryPage(){
        WebUtilityFunctions.linkOrButtonClick(driver, startButtonLocator);
        return new VehicleEnquiryPage(driver);
    }

}
