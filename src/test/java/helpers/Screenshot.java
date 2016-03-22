package helpers;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class Screenshot {


    private WebDriver driver;
    private  String pathToDir;
    private int ScreenshotNumber;

    public Screenshot(WebDriver driver, String pathToDir) {
        this.driver = driver;
        this.pathToDir = pathToDir;
    }

    public  void takeScreenshot() {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(this.pathToDir + "screenshot_" + ScreenshotNumber + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screenshot taken");
        ScreenshotNumber ++;


    }

}
