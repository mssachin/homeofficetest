package com.homeoffice.assessment.utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.Assert;


public class AssertionUtilities {


    public static void assertTrue(boolean condition,String passMessage, String failMessage,ExtentTest test){
        try {
            Assert.assertTrue(condition);
            test.log(Status.PASS, passMessage);

        } catch (AssertionError ae){
            ae.getMessage();
            test.log(Status.FAIL, failMessage);
        }
    }


}