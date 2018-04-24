package com.homeoffice.assessment.fileapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.homeoffice.assessment.serializeexcel.VehicleData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelToJSON {

    private DirectoryScan directoryScan;
    private File vehicleInformationFile;



    public ExcelToJSON() {
        directoryScan = new DirectoryScan();
    }

    public void convertExcelToJsonTestData(String directoryName, String mimeType){
      List<File> allExcelFilesInDirectory = directoryScan.getFilesBasedOnMimeType(directoryName, mimeType);
      for(File singleExcelFile: allExcelFilesInDirectory){
          if(singleExcelFile.getName().contains("Vehicle Information")) {
              vehicleInformationFile = singleExcelFile;
          }
      }
      List<VehicleData> listOfAllVehiclesInformation = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(vehicleInformationFile);
            XSSFSheet sheet = workbook.getSheet("Vehicle Info");
            for(int i= sheet.getFirstRowNum()+1; i<=sheet.getLastRowNum();i++){
                VehicleData vehicleData = new VehicleData();
                Row rowObject = sheet.getRow(i);
                for(int j= rowObject.getFirstCellNum(); j<rowObject.getLastCellNum(); j++) {
                Cell cellObject = rowObject.getCell(j);
                if(j==0){
                    vehicleData.setVehicleRegistration(cellObject.getStringCellValue());
                }else if(j==1){
                    vehicleData.setMake(cellObject.getStringCellValue());
                }else if(j==2){
                    vehicleData.setColour(cellObject.getStringCellValue());
                }

                }
                listOfAllVehiclesInformation.add(vehicleData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter(System.getProperty("user.dir")+"\\src\\test\\testdata\\Vehicle Information.json");
            gson.toJson(listOfAllVehiclesInformation, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public static void main (String[] args){
        ExcelToJSON excelToJSON = new ExcelToJSON();
        excelToJSON.convertExcelToJsonTestData("Files", "sheet");
    }




}
