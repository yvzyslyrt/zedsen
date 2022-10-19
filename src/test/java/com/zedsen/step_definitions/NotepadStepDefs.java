package com.zedsen.step_definitions;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.mk_latn.No;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class NotepadStepDefs {

    private static WindowsDriver NotepadSession = null;
    private static String NotepadAppId = "C:/Windows/System32/notepad.exe";


    public static WebElement WaitForElementToBeVisible(WindowsDriver<WindowsElement> NotepadSession, String locatorType,
                                                       String locatorValue) throws InterruptedException {

        WebElement element = null;

        for (int a = 0; a < 100; a++) {

            if (locatorType.contains("name")) {
                element = NotepadSession.findElementByName(locatorValue);
                element.isDisplayed();
                break;
            }
            if (locatorType.contains("id")) {
                element = NotepadSession.findElementByAccessibilityId(locatorValue);
                element.isDisplayed();
                break;
            }
            Thread.sleep(1000);
        }
        return element;
    }

    @Given("the user open notepad app")
    public void the_user_open_notepad_app() throws MalformedURLException {

        try {
            Desktop desktop=Desktop.getDesktop();
            desktop.open(new File("C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe"));

            Thread.sleep(2000);
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", NotepadAppId);
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            NotepadSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            NotepadSession.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
            Assert.assertNotNull(NotepadSession.getSessionId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    @When("the user write {string}")
    public void the_user_write(String string) throws InterruptedException {
        NotepadSession.findElement(By.name("Text editor")).sendKeys(string + Keys.ENTER);
        Thread.sleep(1000);
    }

    @Then("the user save file using current time")
    public void the_user_save_file_using_current_time() throws InterruptedException {
        NotepadSession.findElement(By.name("File")).click();
        Thread.sleep(1000);
        NotepadSession.findElement(By.name("Save")).click();
        Thread.sleep(1000);

        //set the name
        String time= String.valueOf(java.time.Clock.systemUTC().instant());

        System.out.println("time.substring(0,18) = " + time.substring(0, 19));

        String timeJustNumber=time.substring(0,19).replace("-","").
                replace(":","");


        NotepadSession.findElementByAccessibilityId("1001").
                sendKeys(timeJustNumber);
        //NotepadSession.findElement(By.name("File name:")).sendKeys("Zedsen.txt");
        Thread.sleep(1000);
        NotepadSession.findElement(By.name("Save")).click();
    }

    @Then("the user close app")
    public void the_user_close_app() throws InterruptedException {


        NotepadSession.findElement(By.name("File")).click();
        Thread.sleep(1000);

        WebElement exit = NotepadSession.findElement(By.name("Exit"));
        exit.click();

        NotepadSession = null;
        if (NotepadSession != null) {
            NotepadSession.quit();
        }
        NotepadSession = null;
    }

    @Then("the user close app without saving")
    public void the_user_close_app_without_saving() throws InterruptedException {
        NotepadSession.findElement(By.name("File")).click();
        Thread.sleep(1000);

        NotepadSession.findElement(By.name("Exit")).click();
        Thread.sleep(1000);

        WebElement dontSaveElement = NotepadSession.findElement(By.name("Don't save"));
        dontSaveElement.click();
        Thread.sleep(1000);

        NotepadSession = null;
        if (NotepadSession != null) {
            NotepadSession.quit();
        }
        NotepadSession = null;
    }

    @When("the user open textfile in resources and copy text")
    public void the_user_open_textfile_in_resources_and_copy_text() throws InterruptedException, IOException {


        NotepadSession.findElement(By.name("File")).click();
        Thread.sleep(1000);

        NotepadSession.findElement(By.name("Open")).click();
        Thread.sleep(1000);

        WebElement fileName = NotepadSession.findElement(By.name("File name:"));
        fileName.sendKeys("src/textfile.txt");
        Thread.sleep(1000);

        NotepadSession.findElement(By.name("Open")).click();
        Thread.sleep(1000);


    }

    @Then("the user copy text")
    public void the_user_copy_text() throws InterruptedException {
      NotepadSession.findElement(By.name("Edit")).click();

      NotepadSession.findElement(By.name("Select all")).click();

      NotepadSession.findElement(By.name("Edit")).click();

      NotepadSession.findElement(By.name("Copy")).click();

      Thread.sleep(2000);


    }

    @Then("the user minimise page")
    public void the_user_minimise_page() {
    //NotepadSession.manage().
    }

    @Then("the user open another page")
    public void the_user_open_another_page() throws InterruptedException {

        NotepadSession.findElement(By.name("File")).click();

        NotepadSession.findElement(By.name("New window")).click();



        Thread.sleep(2000);


    }
    @Then("the user paste to new page")
    public void the_user_paste_to_new_page() throws InterruptedException {
        NotepadSession.findElement(By.name("Edit")).click();

        NotepadSession.findElement(By.name("Paste")).click();
        Thread.sleep(2000);
    }
}

