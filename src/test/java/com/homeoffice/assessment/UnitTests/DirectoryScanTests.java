package com.homeoffice.assessment.UnitTests;

import com.homeoffice.assessment.fileapplication.DirectoryScan;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class DirectoryScanTests {

  private static DirectoryScan directoryScan;

  @BeforeClass
  public static void initializeApplication(){
      directoryScan = new DirectoryScan();
  }

  @Test
  public void scanANonExistingDirectory(){
      File[] allFiles = directoryScan.getAllFilesInADirectory("NonExistentDirector");
      Assert.assertTrue(allFiles == null);
  }

  @Test
  public void searchForUnsupportedMimeType(){
      List<File> filesOfUnsupportedMimeType=  directoryScan.getFilesBasedOnMimeType("Files", "UnsupportedMimeType");
      Assert.assertTrue(filesOfUnsupportedMimeType.size() == 0);

  }

  @Test
  public void scanAnExistingValidDirectoryAndValidateFilesReturned(){
      File[] allFiles = directoryScan.getAllFilesInADirectory("Files");
      Assert.assertTrue(allFiles.length == 12);
  }

  @Test
  public void searchForAValidMimeTypeAndValidateFileCount(){
      List<File> filesOfSupportedMimeType=  directoryScan.getFilesBasedOnMimeType("Files", "sheet");
      Assert.assertTrue(filesOfSupportedMimeType.size() == 3);
  }

  @Test
  public void checkNullHandling(){
      try {
          directoryScan.getMimeTypeOfAFile(null);
          directoryScan.getFileExtension(null);
          directoryScan.getFileName(null);
          directoryScan.getFileSize(null);
      }catch (IllegalArgumentException illegalArgException){
         Assert.assertEquals("File Cannot Be Null", illegalArgException.getMessage());
      }
  }

  @Test
  public void validateMimeTypeForAFile(){
      File file = new File(System.getProperty("user.dir")+"//pom.xml");
      String mimeType = directoryScan.getMimeTypeOfAFile(file);
      Assert.assertEquals("application/xml", mimeType);
  }

    @Test
    public void validateFileNameForAFile(){
        File file = new File(System.getProperty("user.dir")+"//chromedriver.exe");
        String fileName = directoryScan.getFileName(file);
        Assert.assertEquals("chromedriver.exe", fileName);
    }

    @Test
    public void validateFileExtensionForAFile(){
        File file = new File(System.getProperty("user.dir")+"//README.md");
        String fileExtension = directoryScan.getFileExtension(file);
        Assert.assertEquals("md", fileExtension);
    }


}
