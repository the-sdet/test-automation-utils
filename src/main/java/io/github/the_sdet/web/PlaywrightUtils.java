package io.github.the_sdet.web;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.github.the_sdet.logger.Log;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;

import static io.github.the_sdet.files.FileUtils.byteArrayToFile;

/**
 * Utility class for Playwright-based web automation tasks. This class provides
 * various methods to interact with web elements, take screenshots, handle
 * timeouts, and perform other common actions using the Playwright library.
 *
 * @author Pabitra Swain (contact.the.sdet@gmail.com)
 */
@SuppressWarnings("unused")
public class PlaywrightUtils extends Utils {

  Page page;

  /**
   * Constructor for PlaywrightUtils class.
   *
   * @param page
   *            The Page instance associated with Playwright.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public PlaywrightUtils(Page page) {
    this.page = page;
  }

  /**
   * Retrieves the element by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element to retrieve.
   * @return The located element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  private Locator getElementByXpath(String xpath) {
    return page.locator(xpath);
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
    page.navigate(url);
    Log.info("Opened URL: " + url);
  }

  /**
   * Maximizes the browser window.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void maximizeScreen() {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    page.setViewportSize(dimension.width, dimension.height);
    Log.info("Maximized Screen...");
  }

  /**
   * Sets the screen size to the specified dimensions.
   *
   * @param width
   *            The width of the screen.
   * @param height
   *            The height of the screen.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void setScreenSize(int width, int height) {
    page.setViewportSize(width, height);
    Log.info("Screen Size set to: " + width + " x " + height);
  }

  /**
   * Clicks on the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void click(String xpath) {
    page.locator(xpath).click();
    Log.info("Clicked on Element with Xpath: " + xpath);
  }

  /**
   * Enters value on to element identified by the specified XPath
   *
   * @param xpath
   *            The XPath of the element
   * @param value
   *            Value to send
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  void fillText(String xpath, String value) {
    page.locator(xpath).fill(value);
  }

  /**
   * Clicks on the specified locator.
   *
   * @param element
   *            The locator to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void click(Locator element) {
    element.click();
    Log.info("Clicked on Element with Xpath: " + element);
  }

  /**
   * Executes a JavaScript click on the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void javaScriptClick(String xpath) {
    try {
      String jsCommand = "document.evaluate(\"$xpath\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();";
      page.evaluate(jsCommand.replace("$xpath", xpath));
      Log.info("Clicked on Element with Xpath: " + xpath);
    } catch (Exception e) {
      Log.error("Exception: " + e.getMessage(), e);
    }
  }

  /**
   * Enters value on to element identified by the specified XPath using
   * JavaScript.
   *
   * @param xpath
   *            The XPath of the element
   * @param value
   *            Value to send
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  void javaScriptFillText(String xpath, String value) {
    try {
      String jsCommand = "document.evaluate(\"$xpath\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.value="
          + value + ";";
      page.evaluate(jsCommand.replace("$xpath", xpath));
      Log.info("Entered Text" + value + " on Element with Xpath: " + xpath);
    } catch (Exception e) {
      Log.error("Exception: " + e.getMessage(), e);
    }
  }

  /**
   * Enters text into the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element to enter text into.
   * @param text
   *            The text to enter.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void enterText(String xpath, String text) {
    page.locator(xpath).fill(text);
    Log.info("Entered text: " + text + " into element with Xpath: " + xpath);
  }

  /**
   * Takes a screenshot of the page.
   *
   * @return The screenshot file.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public File getScreenshot() {
    return byteArrayToFile(getScreenshotAsByte(), "png");
  }

  /**
   * Takes a screenshot of the page and saves it to the specified file path.
   *
   * @param filepath
   *            The file path to save the screenshot to.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void takeScreenshot(String filepath) {
    page.screenshot(new Page.ScreenshotOptions().setFullPage(false).setPath(Paths.get(filepath)));
  }

  /**
   * Takes a screenshot of the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element to take a screenshot of.
   * @return The screenshot file.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public File getElementScreenshot(String xpath) {
    return byteArrayToFile(getElementScreenshotAsByte(xpath), "png");
  }

  /**
   * Takes a screenshot of the element identified by the specified XPath and saves
   * it to the specified file path.
   *
   * @param xpath
   *            The XPath of the element to take a screenshot of.
   * @param filepath
   *            The file path to save the screenshot to.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void takeElementScreenshot(String xpath, String filepath) {
    page.locator(xpath).screenshot(new Locator.ScreenshotOptions().setPath(Paths.get(filepath)));
  }

  /**
   * Takes a full-page screenshot.
   *
   * @return The screenshot file.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public File takeFullPageScreenshot() {
    return byteArrayToFile(getFullPageScreenshotAsByte(), "png");
  }

  /**
   * Takes a full-page screenshot and saves it to the specified file path.
   *
   * @param filepath
   *            The file path to save the screenshot to.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void takeFullPageScreenshot(String filepath) {
    page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get(filepath)));
  }

  /**
   * Retrieves the screenshot as a Base64 encoded string.
   *
   * @return The Base64 encoded screenshot string.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public String getScreenshotAsBase64() {
    return Base64.getEncoder().encodeToString(getScreenshotAsByte());
  }

  /**
   * Retrieves the screenshot of the element identified by the specified XPath as
   * a Base64 encoded string.
   *
   * @param xpath
   *            The XPath of the element to take a screenshot of.
   * @return The Base64 encoded screenshot string.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public String getElementScreenshotAsBase64(String xpath) {
    return Base64.getEncoder().encodeToString(getElementScreenshotAsByte(xpath));
  }

  /**
   * Retrieves the full-page screenshot as a Base64 encoded string.
   *
   * @return The Base64 encoded screenshot string.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public String getFullPageScreenshotAsBase64() {
    return Base64.getEncoder().encodeToString(getFullPageScreenshotAsByte());
  }

  /**
   * Retrieves the screenshot as a byte array.
   *
   * @return The screenshot byte array.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public byte[] getScreenshotAsByte() {
    return page.screenshot(new Page.ScreenshotOptions().setFullPage(false));
  }

  /**
   * Retrieves the screenshot of the element identified by the specified XPath as
   * a byte array.
   *
   * @param xpath
   *            The XPath of the element to take a screenshot of.
   * @return The screenshot byte array.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public byte[] getElementScreenshotAsByte(String xpath) {
    return page.locator(xpath).screenshot();
  }

  /**
   * Retrieves the full-page screenshot as a byte array.
   *
   * @return The screenshot byte array.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public byte[] getFullPageScreenshotAsByte() {
    return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
  }

  /**
   * Clears the element identified by the specified XPath and enters the given
   * text into it.
   *
   * @param xpath
   *            The XPath of the element.
   * @param text
   *            The text to enter.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void clearAndEnterText(String xpath, String text) {
    Locator locator = page.locator(xpath);
    locator.clear();
    locator.fill(text);
    Log.info("Entered text: " + text + " into element with Xpath: " + xpath);
  }

  /**
   * Waits for the element identified by the specified XPath to be clickable and
   * then clicks on it.
   *
   * @param xpath
   *            The XPath of the element to wait for and click.
   * @param seconds
   *            The maximum time to wait for the element to be clickable, in
   *            seconds.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void waitAndClick(String xpath, int seconds) {
    try {
      page.waitForSelector(xpath, new Page.WaitForSelectorOptions().setTimeout(seconds * 1000));
      click(xpath);
    } catch (TimeoutError e) {
      Log.error("Couldn't find element within specified time period. Xpath: " + xpath, e);
    }
  }

  /**
   * Waits for the specified number of seconds.
   *
   * @param seconds
   *            The number of seconds to wait.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void waitForSeconds(int seconds) {
    Log.info("Wait started for " + seconds + " seconds...");
    page.waitForTimeout(seconds * 1000);
    Log.info(seconds + " seconds of wait completed...");
  }

  /**
   * Sets the default timeout for waiting for elements to appear on the page.
   *
   * @param seconds
   *            The default timeout value, in seconds.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void setDefaultTimeOut(int seconds) {
    page.setDefaultTimeout(seconds * 1000);
    Log.info("Default timeout set to: " + seconds + " seconds...");
  }

  /**
   * Focuses on the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element to focus on.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void focusOnElement(String xpath) {
    page.locator(xpath).focus();
  }

  /**
   * Hovers over the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element to hover over.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void hoverOverElement(String xpath) {
    page.locator(xpath).hover();
  }

  /**
   * Simulates pressing the Tab key.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void pressTab() {
    page.keyboard().press("TAB");
  }

  /**
   * Simulates pressing the Enter key.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void pressEnter() {
    page.keyboard().press("ENTER");
  }

  /**
   * Simulates pressing the Tab key on the element identified by the specified
   * XPath.
   *
   * @param xpath
   *            The XPath of the element to press Tab on.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void pressTabOnElement(String xpath) {
    page.locator(xpath).press("TAB");
  }

  /**
   * Simulates pressing the Enter key on the element identified by the specified
   * XPath.
   *
   * @param xpath
   *            The XPath of the element to press Enter on.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void pressEnterOnElement(String xpath) {
    page.locator(xpath).press("ENTER");
  }

  /**
   * Scrolls the element identified by the specified XPath into view.
   *
   * @param xpath
   *            The XPath of the element to scroll into view.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public void scrollElementIntoToView(String xpath) {
    page.locator(xpath).scrollIntoViewIfNeeded();
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
    page.evaluate("window.scrollTo(0, document.documentElement.scrollHeight * (" + percentage + "/ 100.0));");
  }

  /**
   * Retrieves the value of the specified attribute of the element identified by
   * the specified XPath.
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
   * @return The located element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  Locator findElementByCustomizeXpath(String rawXpath, String value) {
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
   * @return The located element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  Locator findElementByCustomizeXpath(String rawXpath, String value1, String value2) {
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
   * @return The located element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  Locator findElementByCustomizeXpath(String rawXpath, String value1, String value2, String value3) {
    return getElementByXpath(customizeXpath(rawXpath, value1, value2, value3));
  }

  /**
   * Checks if the element identified by XPath is visible.
   *
   * @param xpath
   *            XPath identifying the element
   * @return true if the element is visible, false otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public boolean isVisible(String xpath) {
    return isVisible(page.locator(xpath));
  }

  /**
   * Checks if the element identified by the given locator is visible.
   *
   * @param locator
   *            locator for identifying the element
   * @return true if the element is visible, false otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean isVisible(Locator locator) {
    try {
      return locator.isVisible();
    } catch (NullPointerException e) {
      return false;
    }
  }

  /**
   * Waits for the element identified by XPath to be visible.
   *
   * @param xpath
   *            XPath identifying the element
   * @param duration
   *            maximum duration to wait
   * @return true if the element becomes visible within the duration, false
   *         otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public boolean waitAndCheckIsVisible(String xpath, Duration duration) {
    return waitAndCheckIsVisible(page.locator(xpath), duration);
  }

  /**
   * Waits for the element identified by XPath to be visible.
   *
   * @param element
   *            locator for identifying the element
   * @param duration
   *            maximum duration to wait
   * @return true if the element becomes visible within the duration, false
   *         otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean waitAndCheckIsVisible(Locator element, Duration duration) {
    element.waitFor(
        new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(duration.toMillis()));
    return element.isVisible();
  }

  /**
   * Waits for the element identified by XPath to be clickable.
   *
   * @param xpath
   *            XPath identifying the element
   * @param duration
   *            maximum duration to wait
   * @return true if the element becomes clickable within the duration, false
   *         otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public boolean waitAndCheckIsClickable(String xpath, Duration duration) {
    return waitAndCheckIsClickable(page.locator(xpath), duration);
  }

  /**
   * Waits for the element identified by XPath to be clickable.
   *
   * @param element
   *            locator for identifying the element
   * @param duration
   *            maximum duration to wait
   * @return true if the element becomes clickable within the duration, false
   *         otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean waitAndCheckIsClickable(Locator element, Duration duration) {
    element.waitFor(new Locator.WaitForOptions().setTimeout(duration.toMillis()));
    return element.isEnabled();
  }

  /**
   * Waits for the element identified by XPath to become invisible.
   *
   * @param xpath
   *            XPath identifying the element
   * @param duration
   *            maximum duration to wait
   * @return true if the element becomes invisible within the duration, false
   *         otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  @Override
  public boolean waitAndCheckIsInVisible(String xpath, Duration duration) {
    return waitAndCheckIsInVisible(page.locator(xpath), duration);
  }

  /**
   * Waits for the element identified by the given locator to become invisible.
   *
   * @param element
   *            locator for identifying the element
   * @param duration
   *            maximum duration to wait
   * @return true if the element becomes invisible within the duration, false
   *         otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean waitAndCheckIsInVisible(Locator element, Duration duration) {
    element.waitFor(
        new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(duration.toMillis()));
    return element.isHidden();
  }

  /**
   * Waits for the element identified by XPath to be present within a default
   * duration of 5 seconds.
   *
   * @param xpath
   *            XPath identifying the element
   * @param duration
   *            maximum wait time
   * @return WebElement representing the located element
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public Locator waitAndFindElement(String xpath, Duration duration) {
    page.waitForSelector(xpath, new Page.WaitForSelectorOptions().setTimeout(duration.toMillis()));
    return page.locator(xpath);
  }
}