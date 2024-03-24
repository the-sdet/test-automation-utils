
# Test Automation Toolkit
[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-sdet/test-automation-utils)](https://search.maven.org/artifact/io.github.the-sdet/test-automation-utils)
[![javadoc](https://javadoc.io/badge2/io.github.the-sdet/test-automation-utils/javadoc.svg)](https://javadoc.io/doc/io.github.the-sdet/test-automation-utils)
## Streamlining Test Automation for Web, API, Mobile and Database Testing

Welcome to the Automation Toolkit – your comprehensive solution for streamlining test automation across various domains. Whether you're testing web applications, APIs, or interacting with databases, our library provides a rich set of reusable utility methods tailored to your needs.

With support for popular automation frameworks including Selenium, Playwright, Appium, RestAssured, and JDBC, our toolkit empowers testers and developers to write efficient and maintainable automation scripts with ease. Say goodbye to repetitive tasks and boilerplate code – leverage our library to accelerate your testing workflows and ensure the quality and reliability of your software products.

From interacting with web and mobile elements to validating API responses and querying databases, the Automation Toolkit equips you with the tools you need to automate your tests effectively. Start automating smarter, not harder, with our comprehensive suite of utilities.

Join our community of testers and developers, and together, let's redefine test automation. Unlock the potential of automation with the Automation Toolkit today!


## Usage
Test Automation Toolkit requires Java 11 or newer.

To use Test Automation Toolkit simply add following dependency to your Maven project:

```xml
<dependency>
  <groupId>io.github.the-sdet</groupId>
  <artifactId>test-automation-utils</artifactId>
  <version>1.1.0</version>
</dependency>
```

## Example

This code snippet give you a glimpse of how your selenium and playwright code both can look very similar. You just need to extend SeleniumUtils.java or PlaywrightUtils.java as per your need.

Extend SeleniumUtils.java or PlaywrightUtils.java to your Page classes in Page object model and use the utility methods without working.

Depending on your usage, you can simply create an object of the SeleniumUtils.java or PlaywrightUtils.java classes and start using the utility methods.

```java
package io.github.the_sdet;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.appium.java_client.AppiumDriver;
import io.github.the_sdet.db.DatabaseUtils;
import io.github.the_sdet.mobile.AppiumUtils;
import io.github.the_sdet.web.PlaywrightUtils;
import io.github.the_sdet.web.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static io.github.the_sdet.excel.ExcelUtils.*;

public class TestingTestAutomationToolkit {
    public static void main(String[] args) throws IOException {
        String url = "https://www.selenium.dev/";
        String readModeAboutSeleniumWebDriver = "//a[contains(@class,'selenium-button selenium-webdriver')]";
        String getStartedButton = "//a[contains(text(),'Getting started')]";


        //Testing Selenium Utilities
        WebDriver driver = new ChromeDriver();
        SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
        seleniumUtils.setDefaultTimeOut(10);
        seleniumUtils.openPage(url);
        seleniumUtils.setScreenSize(1200, 700);
        seleniumUtils.maximizeScreen();
        seleniumUtils.javaScriptClick(readModeAboutSeleniumWebDriver);
        seleniumUtils.waitAndClick(getStartedButton, 15);
        seleniumUtils.waitForSeconds(1);
        seleniumUtils.getScreenshot();
        seleniumUtils.takeScreenshot("screenshot-by-selenium.png");
        seleniumUtils.takeFullPageScreenshot("full-screenshot-by-selenium.png");
        driver.quit();


        //Testing Playwright Utilities
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        Page page = browser.newPage();
        PlaywrightUtils playwrightUtils = new PlaywrightUtils(page);
        playwrightUtils.setDefaultTimeOut(10);
        playwrightUtils.openPage(url);
        playwrightUtils.setScreenSize(1200, 700);
        playwrightUtils.maximizeScreen();
        playwrightUtils.javaScriptClick(readModeAboutSeleniumWebDriver);
        playwrightUtils.waitAndClick(getStartedButton, 15);
        playwrightUtils.waitForSeconds(1);
        playwrightUtils.takeScreenshot("screenshot-by-playwright.png");
        playwrightUtils.takeFullPageScreenshot("full-screenshot-by-playwright.png");
        page.close();
        playwright.close();


        //Testing Appium Utilities
        AppiumDriver appiumDriver = new AppiumDriver(new MutableCapabilities());
        AppiumUtils appiumUtils = new AppiumUtils(appiumDriver);
        appiumUtils.click(By.xpath(getStartedButton));
        appiumUtils.swipeDownAndRefreshPage();
        appiumUtils.swipeLeft();
        appiumUtils.swipeRight();
        appiumUtils.scrollByPercent(50);


        //Testing Database Utilities
        Connection connection;
        try {
            connection = DriverManager.getConnection("db.url");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DatabaseUtils databaseUtils = new DatabaseUtils(connection);
        List<List<String>> employeesData = databaseUtils.readDataFromDb("Select * from Employees");
        List<String> employeeOneData = databaseUtils.readSingleRowFromDb("Select * from Employees where Emp_Id=001");
        String ageOfEmployeeOne = databaseUtils.readSingleDataFromDb("Select Emp_Name from Employees where Emp_Id=001");
        List<String> employees = databaseUtils.readSingleColumnFromDb("Select Emp_Name from Employees");
        System.out.println(employeesData);
        System.out.println(employeeOneData);
        System.out.println(ageOfEmployeeOne);
        System.out.println(employees);


        //Testing Excel Utilities
        System.out.println(readExcelSheet("src/test/resources/file.xlsx", "sheet1"));
        System.out.println(getHeaderList("src/test/resources/file.xlsx", "sheet1"));
        System.out.println(getValuesOfRow("src/test/resources/file.xlsx", "sheet1", "Row 4", true));
        System.out.println(getValuesOfColumn("src/test/resources/file.xlsx", "sheet1", "Header 4", true));
        System.out.println(readExcelSheet("src/test/resources/file.xlsx", "sheet1"));
        System.out.println(readExcelSheet("src/test/resources/file.xlsx", "sheet1", true));
    }
}
```
## Authors

- [@the-sdet](https://github.com/the-sdet)
- [@pabitra-qa](https://github.com/pabitra-qa)


## 🚀 About Me

I'm a dedicated and passionate Software Development Engineer in Test (SDET) trying to help the community in focusing in building great automation frameworks rather than writing the same utilities again and again and again...

## Feedback

If you have any feedback, please reach out to us at [contact.the.sdet@gmail.com](mailto:contact.the.sdet@gmail.com).