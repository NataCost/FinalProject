package tests;

import helpers.Screenshot;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nata_Cost on 22.03.2016.
 */
public class TestRunner {
    Screenshot screnshot;
    WebDriver driver = null;
    java.sql.Connection con = null;
    java.sql.Statement stat = null;
    String TestUserName = "MyNewTestUser";
    String TestUserPassword= "12345";
    String TestUserEmail = "testuser@example.com";
    String DataBaseUrl="jdbc:mysql://http://192.168.0.183:3306/BucketList";
    String pathToScreenshots= "D:\\Java_QA\\QA_Auto\\QA_Auto";
    String TestApplURL = "http://192.168.0.183:5000/showSignUp/";

    @BeforeMethod
    public void setUp(){
        driver = new FirefoxDriver();
        screnshot = new Screenshot(driver, pathToScreenshots);
            }
    @AfterMethod
    public  void tearDown(ITestResult result){
        if(result.getStatus()== ITestResult.FAILURE){

            System.out.println("Test is down");
            screnshot.takeScreenshot();

        }
driver.close();
    }
    @Test
    public void insertTestData(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(TestApplURL);
        WebElement loginInput= driver.findElement(By.xpath("//*[@id=\"inputName\"]"));
        WebElement emailInput= driver.findElement(By.xpath("//*[@id=\"inputEmail\"]"));
        WebElement passwordInput= driver.findElement(By.xpath("//*[@id=\"inputPassword\"]"));
        WebElement submit= driver.findElement(By.xpath("//*[@id=\"btnSignUp\"]"));
        loginInput.sendKeys(TestUserName);
        emailInput.sendKeys(TestUserEmail);
        passwordInput.sendKeys(TestUserPassword);
        submit.click();
    }
    @Test
    public void DataBaseTest() throws Exception{

        Class.forName("com.mysqp.jdbs.Driver");
        con= DriverManager.getConnection(DataBaseUrl, "root", "12345");
        stat = con.createStatement();
        String query= "SELECT* FROM tab_USER";
        String clearTestData = "DELETE  FROM tab_USER  WHERE ID>=3";
        ResultSet result = stat.executeQuery(query);
        while (result.next()){
            int id = result.getInt("user_id");
            String username = result.getString("user_name");
            String useremail = result.getString("user_username");
            System.out.println("ID: " + id);
            System.out.println("Name: " + username);
            System.out.println("Email: " + useremail);
            if(id > 1){
                Assert.assertEquals(username, TestUserName);

            }

        }
        stat.executeUpdate(clearTestData);
        result.close();
    }



}
