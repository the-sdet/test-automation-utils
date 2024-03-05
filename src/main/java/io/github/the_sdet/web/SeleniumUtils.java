package io.github.the_sdet.web;

import io.github.the_sdet.logger.Log;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Base64;

/**
 * Utility class for Selenium-based web automation tasks. This class provides
 * various methods to interact with web elements, take screenshots, handle
 * timeouts, and perform other common actions using the Selenium WebDriver API.
 *
 * @author Pabitra Swain (contact.the.sdet@gmail.com)
 */
@SuppressWarnings("unused")
public class SeleniumUtils extends Utils {

  WebDriver driver;
  JavascriptExecutor javascriptExecutor;
  Actions actions;

  /**
   * Constructor to initialize SeleniumUtils.
   *
   * @param driver
   *            The WebDriver instance to use.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public SeleniumUtils(WebDriver driver) {
    this.driver = driver;
    javascriptExecutor = (JavascriptExecutor) driver;
    actions = new Actions(driver);
  }

  /**
   * Retrieves the WebElement by Xpath.
   *
   * @param xpath
   *            The XPath of the element to retrieve.
   * @return The WebElement found.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  private WebElement getElementByXpath(String xpath) {
    return driver.findElement(By.xpath(xpath));
  }

  /**
   * Retrieves the XPath of the specified WebElement.
   *
   * @param element
   *            The WebElement for which to retrieve the XPath.
   * @return The XPath of the WebElement.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  private String getXpathOfElement(WebElement element) {
    return element.toString().split("xpath: ")[1];
  }

  /**
   * Retrieves the XPath of the specified By selector.
   *
   * @param element
   *            The By selector for which to retrieve the XPath.
   * @return The XPath of the By selector.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  private String getXpathOfElement(By element) {
    return element.toString().split("xpath: ")[1];
  }

  /**
   * Opens the specified URL in the browser.
   *
   * @param url
   *            The URL to open.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void openPage(String url) {
    driver.get(url);
    Log.info("Opened URL: " + url);
  }

  /**
   * Maximizes the browser window.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void maximizeScreen() {
    driver.manage().window().maximize();
    Log.info("Maximized Screen...");
  }

  /**
   * Sets the screen size to the specified width and height.
   *
   * @param width
   *            The width of the screen.
   * @param height
   *            The height of the screen.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void setScreenSize(int width, int height) {
    driver.manage().window().setSize(new Dimension(width, height));
    Log.info("Screen Size set to: " + width + " x " + height);
  }

  /**
   * Clicks on the element identified by the given XPath.
   *
   * @param xpath
   *            The XPath of the element to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void click(String xpath) {
    getElementByXpath(xpath).click();
    Log.info("Clicked on Element with Xpath: " + xpath);
  }

  /**
   * Clicks on the specified WebElement.
   *
   * @param element
   *            The WebElement to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void click(WebElement element) {
    element.click();
    Log.info("Clicked on Element with Xpath: " + getXpathOfElement(element));
  }

  /**
   * Clicks on the element identified by the given By selector.
   *
   * @param element
   *            The By selector of the element to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void click(By element) {
    driver.findElement(element).click();
    Log.info("Clicked on Element with Xpath: " + getXpathOfElement(element));
  }

  /**
   * Clicks on the element identified by the given XPath using JavaScript.
   *
   * @param xpath
   *            The XPath of the element to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void jsClick(String xpath) {
    try {
      javascriptExecutor.executeScript("arguments[0].click();", getElementByXpath(xpath));
      Log.info("Clicked on Element with Xpath: " + xpath);
    } catch (Exception e) {
      Log.error("An error occurred: " + e.getMessage(), e);
    }
  }

  /**
   * Enters the specified text into the element identified by the given XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @param text
   *            The text to enter.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void enterText(String xpath, String text) {
    getElementByXpath(xpath).sendKeys(text);
    Log.info("Entered text: " + text + " into element with Xpath: " + xpath);
  }

  /**
   * Retrieves a screenshot of the current page.
   *
   * @return A File object representing the screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public File getScreenshot() {
    TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
    return screenshotDriver.getScreenshotAs(OutputType.FILE);
  }

  /**
   * Takes a screenshot of the current page and saves it to the specified
   * filepath.
   *
   * @param filepath
   *            The filepath where the screenshot should be saved.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void takeScreenshot(String filepath) {
    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    copyFile(screenshot, new File(filepath), "Screenshot");
  }

  /**
   * Retrieves a screenshot of the specified element.
   *
   * @param xpath
   *            The XPath of the element.
   * @return A File object representing the screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public File getElementScreenshot(String xpath) {
    return getElementByXpath(xpath).getScreenshotAs(OutputType.FILE);
  }

  /**
   * Takes a screenshot of the specified element and saves it to the specified
   * filepath.
   *
   * @param xpath
   *            The XPath of the element.
   * @param filepath
   *            The filepath where the screenshot should be saved.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void takeElementScreenshot(String xpath, String filepath) {
    File screenshot = getElementByXpath(xpath).getScreenshotAs(OutputType.FILE);
    copyFile(screenshot, new File(filepath), "Screenshot");
  }

  /**
   * Takes a full-page screenshot of the current page.
   *
   * @return A File object representing the full-page screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public File takeFullPageScreenshot() {
    Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
        .takeScreenshot(driver);

    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    try {
      ImageIO.write(screenshot.getImage(), "PNG", byteStream);
    } catch (IOException e) {
      Log.error("Could NOT save Screenshot...", e);
    }

    return new File("screenshot.png");
  }

  /**
   * Takes a full-page screenshot of the current page and saves it to the
   * specified filepath.
   *
   * @param filepath
   *            The filepath where the screenshot should be saved.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void takeFullPageScreenshot(String filepath) {
    Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
        .takeScreenshot(driver);

    File screenshotFile = new File(filepath);
    try {
      ImageIO.write(screenshot.getImage(), "PNG", screenshotFile);
    } catch (IOException e) {
      Log.error("Could NOT save Screenshot...", e);
    }
  }

  /**
   * Retrieves the screenshot of the current page as a Base64 encoded string.
   *
   * @return The Base64 encoded string representation of the screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public String getScreenshotAsBase64() {
    return Base64.getEncoder().encodeToString(getScreenshotAsByte());
  }

  /**
   * Retrieves the screenshot of the element identified by the given XPath as a
   * Base64 encoded string.
   *
   * @param xpath
   *            The XPath of the element.
   * @return The Base64 encoded string representation of the screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public String getElementScreenshotAsBase64(String xpath) {
    return Base64.getEncoder().encodeToString(getElementScreenshotAsByte(xpath));
  }

  /**
   * Retrieves the full-page screenshot as a Base64 encoded string.
   *
   * @return The Base64 encoded string representation of the full-page screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public String getFullPageScreenshotAsBase64() {
    return Base64.getEncoder().encodeToString(getFullPageScreenshotAsByte());
  }

  /**
   * Retrieves the screenshot of the current page as a byte array.
   *
   * @return The byte array representing the screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public byte[] getScreenshotAsByte() {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }

  /**
   * Retrieves the screenshot of the element identified by the given XPath as a
   * byte array.
   *
   * @param xpath
   *            The XPath of the element.
   * @return The byte array representing the screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public byte[] getElementScreenshotAsByte(String xpath) {
    return getElementByXpath(xpath).getScreenshotAs(OutputType.BYTES);
  }

  /**
   * Retrieves the full-page screenshot as a byte array.
   *
   * @return The byte array representing the full-page screenshot.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public byte[] getFullPageScreenshotAsByte() {
    Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
        .takeScreenshot(driver);
    byte[] screenshotBytes = null;
    try {
      BufferedImage bufferedImage = screenshot.getImage();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "png", outputStream);
      screenshotBytes = outputStream.toByteArray();
    } catch (IOException e) {
      Log.error("Could NOT save Screenshot...", e);
    }
    return screenshotBytes;
  }

  /**
   * Clears the existing text and enters the specified text into the element
   * identified by the given XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @param text
   *            The text to enter.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void clearAndEnterText(String xpath, String text) {
    WebElement element = getElementByXpath(xpath);
    element.clear();
    element.sendKeys(text);
    Log.info("Entered text: " + text + " into element with Xpath: " + xpath);
  }

  /**
   * Waits for the specified element to be clickable and then clicks on it.
   *
   * @param xpath
   *            The XPath of the element to click.
   * @param seconds
   *            The maximum time to wait for the element to be clickable, in
   *            seconds.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void waitAndClick(String xpath, int seconds) {
    try {
      click(new WebDriverWait(driver, Duration.ofSeconds(seconds))
          .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))));
    } catch (TimeoutException e) {
      Log.error("Couldn't find element within specified time period. Xpath: " + xpath);
    }
  }

  /**
   * Pauses the execution for the specified duration.
   *
   * @param seconds
   *            The duration to wait, in seconds.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void wait(int seconds) {
    Log.info("Wait started for " + seconds + " seconds...");
    try {
      Thread.sleep(seconds * 1000L);
    } catch (InterruptedException e) {
      Log.error("Error while applying wait...", e);
    }
    Log.info(seconds + " seconds of wait completed...");
  }

  /**
   * Sets the default timeout for implicit waits.
   *
   * @param seconds
   *            The default timeout value, in seconds.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void setDefaultTimeOut(int seconds) {
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    Log.info("Default timeout set to: " + seconds + " seconds...");
  }

  /**
   * Focuses on the element identified by the given XPath.
   *
   * @param xpath
   *            The XPath of the element to focus on.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void focusOnElement(String xpath) {
    javascriptExecutor.executeScript("arguments[0].focus();", getElementByXpath(xpath));
  }

  /**
   * Hovers over the element identified by the given XPath.
   *
   * @param xpath
   *            The XPath of the element to hover over.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void hoverOverElement(String xpath) {
    actions.moveToElement(getElementByXpath(xpath));
  }

  /**
   * Simulates pressing the Tab key.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void pressTab() {
    actions.sendKeys(Keys.TAB).perform();
  }

  /**
   * Simulates pressing the Enter key.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void pressEnter() {
    actions.sendKeys(Keys.ENTER).perform();
  }

  /**
   * Simulates pressing the Tab key on the element identified by the given XPath.
   *
   * @param xpath
   *            The XPath of the element to press Tab on.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void pressTabOnElement(String xpath) {
    actions.sendKeys(getElementByXpath(xpath), Keys.TAB).perform();
  }

  /**
   * Simulates pressing the Enter key on the element identified by the given
   * XPath.
   *
   * @param xpath
   *            The XPath of the element to press Enter on.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void pressEnterOnElement(String xpath) {
    actions.sendKeys(getElementByXpath(xpath), Keys.ENTER).perform();
  }

  /**
   * Scrolls the element identified by the given XPath into view.
   *
   * @param xpath
   *            The XPath of the element to scroll into view.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void scrollElementIntoToView(String xpath) {
    javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", getElementByXpath(xpath));
  }

  /**
   * Scrolls the page by the specified percentage.
   *
   * @param percentage
   *            The percentage by which to scroll the page.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void scrollByPercent(double percentage) {
    javascriptExecutor.executeScript(
        "window.scrollTo(0, document.documentElement.scrollHeight * (" + percentage + "/ 100.0));");
  }

  /**
   * Retrieves the value of the specified attribute of the element identified by
   * the given XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @param attributeName
   *            The name of the attribute.
   * @return The value of the attribute.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  String getAttributeValue(String xpath, String attributeName) {
    return getElementByXpath(xpath).getAttribute(attributeName);
  }

  /**
   * Finds the element using the customized XPath with one parameter.
   *
   * @param rawXpath
   *            The raw XPath pattern.
   * @param value
   *            The value to be replaced in the XPath.
   * @return The WebElement found.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  WebElement findElementByCustomizeXpath(String rawXpath, String value) {
    return getElementByXpath(customizeXpath(rawXpath, value));
  }

  /**
   * Finds the element using the customized XPath with two parameters.
   *
   * @param rawXpath
   *            The raw XPath pattern.
   * @param value1
   *            The first value to be replaced in the XPath.
   * @param value2
   *            The second value to be replaced in the XPath.
   * @return The WebElement found.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  WebElement findElementByCustomizeXpath(String rawXpath, String value1, String value2) {
    return getElementByXpath(customizeXpath(rawXpath, value1, value2));
  }

  /**
   * Finds the element using the customized XPath with three parameters.
   *
   * @param rawXpath
   *            The raw XPath pattern.
   * @param value1
   *            The first value to be replaced in the XPath.
   * @param value2
   *            The second value to be replaced in the XPath.
   * @param value3
   *            The third value to be replaced in the XPath.
   * @return The WebElement found.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  WebElement findElementByCustomizeXpath(String rawXpath, String value1, String value2, String value3) {
    return getElementByXpath(customizeXpath(rawXpath, value1, value2, value3));
  }

}
