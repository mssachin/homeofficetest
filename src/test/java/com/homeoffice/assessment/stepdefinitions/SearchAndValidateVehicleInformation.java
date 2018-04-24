package com.homeoffice.assessment.stepdefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.homeoffice.assessment.datadeserialization.VehicleInformation;
import com.homeoffice.assessment.pageobjects.VehicleDetailsPage;
import com.homeoffice.assessment.pageobjects.VehicleEnquiryPage;
import com.homeoffice.assessment.pageobjects.VehicleInformationLandingPage;
import com.homeoffice.assessment.utilities.AssertionUtilities;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;



public class SearchAndValidateVehicleInformation {

    private WebDriver driver;
    private TestBase testBase;
    private VehicleInformationLandingPage vehicleInformationLandingPage;
    private VehicleEnquiryPage vehicleEnquiryPage;
    private VehicleDetailsPage vehicleDetailsPage;
    private VehicleInformation[] allVehicles ;


    public SearchAndValidateVehicleInformation(TestBase testBase) {
        this.testBase = testBase;
        this.driver = testBase.getDriver();
        this.allVehicles = testBase.getAllVehiclesInformation();
    }

    @When("^I Start the process of searching for vehicle details$")
    public void i_Start_the_process_of_searching_for_vehicle_details() throws Throwable {
        vehicleInformationLandingPage = new VehicleInformationLandingPage(driver);
        vehicleEnquiryPage =vehicleInformationLandingPage.navigateToVehicleEnquiryPage();

    }

    @And("^I enter the vehicle registration number and submit to validate the details$")
    public void i_enter_the_vehicle_registration_number_and_submit() throws Throwable {
        ExtentReports report = TestBase.getExtentInstance(System.getProperty("user.dir")+"//report.html");
        for(VehicleInformation oneVehicleInformation: allVehicles) {
            VehicleInformation currentVehicleInformation = oneVehicleInformation;
            String vehicleRegistrationFromTestData = currentVehicleInformation.getVehicleRegistration();
            String vehicleMakeFromTestData = currentVehicleInformation.getMake();
            String vehicleColourFromTestData = currentVehicleInformation.getColour();
            ExtentTest testInstance = report.createTest("Vehicle Registration Number "+vehicleRegistrationFromTestData);
            vehicleEnquiryPage.enterVehicleRegistrationNumber(vehicleRegistrationFromTestData);
            vehicleDetailsPage = vehicleEnquiryPage.submitVehicleDetailsSearchRequest();
            String vehicleRegistrationFromApplication = vehicleDetailsPage.getVehicleRegistrationNumber();
            String vehicleMakeFromApplication = vehicleDetailsPage.getMake();
            String vehicleColourFromApplication = vehicleDetailsPage.getColour();
            AssertionUtilities.assertTrue(vehicleRegistrationFromTestData.equalsIgnoreCase(vehicleRegistrationFromApplication), "Vehicle Registration From test data "+vehicleRegistrationFromTestData+ " matches with that in the application "+vehicleRegistrationFromApplication,"Vehicle Registration From test data "+vehicleRegistrationFromTestData+ " does not match with that in the application "+vehicleRegistrationFromApplication,  testInstance);
            AssertionUtilities.assertTrue(vehicleMakeFromTestData.equalsIgnoreCase(vehicleMakeFromApplication), "Vehicle Make from test data "+vehicleMakeFromTestData+ " matches with that in the application "+vehicleMakeFromApplication,"Vehicle Make from test data "+vehicleMakeFromTestData+ " does not match with that in the application "+vehicleMakeFromApplication , testInstance);
            AssertionUtilities.assertTrue(vehicleColourFromTestData.equalsIgnoreCase(vehicleColourFromApplication), "Vehicle Color from test Data "+vehicleColourFromTestData+ " matches with that in the application "+vehicleColourFromApplication,"Vehicle Color from test Data "+vehicleColourFromTestData+ " does not match with that in the application "+vehicleColourFromApplication, testInstance);
            driver.navigate().to("https://vehicleenquiry.service.gov.uk/");
        }

    }


}
