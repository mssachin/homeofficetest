package com.homeoffice.assessment.pageobjects;

import com.homeoffice.assessment.utilities.WebUtilityFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VehicleEnquiryPage {

    private WebDriver driver;
    private static final By enterVehicleRegistrationNumberInputLocator = By.id("Vrm");
    private static final By continueButtonLocator = By.name("Continue");


    public VehicleEnquiryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterVehicleRegistrationNumber(String vehicleREgistrationNumber){
        WebUtilityFunctions.enterTextInTextBox(driver, enterVehicleRegistrationNumberInputLocator, vehicleREgistrationNumber);
    }

    public VehicleDetailsPage submitVehicleDetailsSearchRequest(){
        WebUtilityFunctions.linkOrButtonClick(driver, continueButtonLocator);
        return new VehicleDetailsPage(driver);
    }
}
