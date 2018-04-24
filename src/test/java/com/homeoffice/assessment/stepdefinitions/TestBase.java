package com.homeoffice.assessment.stepdefinitions;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.gson.Gson;
import com.homeoffice.assessment.datadeserialization.VehicleInformation;
import com.homeoffice.assessment.fileapplication.ExcelToJSON;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;
    private boolean isInitialized;
    private FileInputStream fileInputStream;
    private Reader reader;
    public VehicleInformation[] allVehiclesInformation;
    private static ExtentHtmlReporter htmlReporter;
    private static ExtentReports extent;


    @Before
    public void  initializeEnvironment(){
        if(!isInitialized) {
            File file = new File(System.getProperty("user.dir")+"//report.html");
            file.delete();
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            driver = new ChromeDriver(chromeOptions);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            isInitialized = true;
            ExcelToJSON excelToJSON = new ExcelToJSON();
            excelToJSON.convertExcelToJsonTestData("Files", "sheet");
            try {
                fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\test\\testdata\\Vehicle Information.json"));
                reader = new InputStreamReader(fileInputStream, "UTF8");
                allVehiclesInformation = new Gson().fromJson(reader, VehicleInformation[].class);
                fileInputStream.close();
                reader.close();
            } catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public VehicleInformation[] getAllVehiclesInformation() {
        return allVehiclesInformation;
    }

    public static ExtentReports getExtentInstance(String filePath){
        if (extent != null)
            return extent; //avoid creating new instance of html file
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter(filePath));
        extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
        return extent;
    }

    private static ExtentHtmlReporter getHtmlReporter(String filePath) {

        htmlReporter = new ExtentHtmlReporter(filePath);
        htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
        htmlReporter.setAppendExisting(true);
        htmlReporter.config().setChartVisibilityOnOpen(true);


        return htmlReporter;
    }

    @After
    public void tearDownEnvironment(){
        driver.quit();
        extent.flush();
    }
}
