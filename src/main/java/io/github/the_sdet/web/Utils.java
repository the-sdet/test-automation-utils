package io.github.the_sdet.web;

import io.github.the_sdet.logger.Log;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * Abstract class containing utility methods for web automation.
 *
 * @author Pabitra Swain (contact.the.sdet@gmail.com)
 */
@SuppressWarnings({"unused", "UnusedReturnValue", "SameParameterValue"})
public abstract class Utils {

  /**
   * Opens the specified URL in the browser.
   *
   * @param url
   *            The URL to open.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void openPage(String url);

  /**
   * Maximizes the screen.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void maximizeScreen();

  /**
   * Sets the screen size to the specified width and height.
   *
   * @param width
   *            The width of the screen.
   * @param height
   *            The height of the screen.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void setScreenSize(int width, int height);

  /**
   * Sets the default timeout for waiting for elements to appear on the page.
   *
   * @param seconds
   *            The default timeout value, in seconds.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void setDefaultTimeOut(int seconds);

  /**
   * Clicks on the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void click(String xpath);

  /**
   * Clicks on the element identified by the specified XPath using JavaScript.
   *
   * @param xpath
   *            The XPath of the element to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void jsClick(String xpath);

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
  abstract void waitAndClick(String xpath, int seconds);

  /**
   * Waits for the specified number of seconds.
   *
   * @param seconds
   *            The number of seconds to wait.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void wait(int seconds);

  /**
   * Enters the specified text into the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @param text
   *            The text to enter.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void enterText(String xpath, String text);

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
  abstract void clearAndEnterText(String xpath, String text);

  /**
   * Retrieves a screenshot of the current page.
   *
   * @return The screenshot file.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract File getScreenshot();

  /**
   * Takes a screenshot of the current page and saves it to the specified file
   * path.
   *
   * @param filepath
   *            The file path where the screenshot should be saved.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void takeScreenshot(String filepath);

  /**
   * Retrieves a screenshot of the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @return The screenshot file.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract File getElementScreenshot(String xpath);

  /**
   * Takes a screenshot of the element identified by the specified XPath and saves
   * it to the specified file path.
   *
   * @param xpath
   *            The XPath of the element.
   * @param filepath
   *            The file path where the screenshot should be saved.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void takeElementScreenshot(String xpath, String filepath);

  /**
   * Takes a full-page screenshot.
   *
   * @return The screenshot file.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract File takeFullPageScreenshot();

  /**
   * Takes a full-page screenshot and saves it to the specified file path.
   *
   * @param filepath
   *            The file path where the screenshot should be saved.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void takeFullPageScreenshot(String filepath);

  /**
   * Retrieves the screenshot of the current page as a Base64 encoded string.
   *
   * @return The Base64 encoded screenshot string.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract String getScreenshotAsBase64();

  /**
   * Retrieves the screenshot of the element identified by the specified XPath as
   * a Base64 encoded string.
   *
   * @param xpath
   *            The XPath of the element.
   * @return The Base64 encoded screenshot string.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract String getElementScreenshotAsBase64(String xpath);

  /**
   * Retrieves the full-page screenshot as a Base64 encoded string.
   *
   * @return The Base64 encoded screenshot string.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract String getFullPageScreenshotAsBase64();

  /**
   * Retrieves the screenshot of the current page as a byte array.
   *
   * @return The screenshot byte array.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract byte[] getScreenshotAsByte();

  /**
   * Retrieves the screenshot of the element identified by the specified XPath as
   * a byte array.
   *
   * @param xpath
   *            The XPath of the element.
   * @return The screenshot byte array.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract byte[] getElementScreenshotAsByte(String xpath);

  /**
   * Retrieves the full-page screenshot as a byte array.
   *
   * @return The screenshot byte array.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract byte[] getFullPageScreenshotAsByte();

  /**
   * Focuses on the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void focusOnElement(String xpath);

  /**
   * Hovers over the element identified by the specified XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void hoverOverElement(String xpath);

  /**
   * Simulates pressing the Tab key.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void pressTab();

  /**
   * Simulates pressing the Enter key.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void pressEnter();

  /**
   * Simulates pressing the Tab key on the element identified by the specified
   * XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void pressTabOnElement(String xpath);

  /**
   * Simulates pressing the Enter key on the element identified by the specified
   * XPath.
   *
   * @param xpath
   *            The XPath of the element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void pressEnterOnElement(String xpath);

  /**
   * Scrolls the element identified by the specified XPath into view.
   *
   * @param xpath
   *            The XPath of the element.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void scrollElementIntoToView(String xpath);

  /**
   * Scrolls the page by the specified percentage.
   *
   * @param percentage
   *            The percentage of the page height to scroll by.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  abstract void scrollByPercent(double percentage);

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
  abstract String getAttributeValue(String xpath, String attributeName);

  /**
   * Customizes the XPath pattern by replacing placeholder values with the
   * provided values.
   *
   * @param rawXpath
   *            The raw XPath pattern.
   * @param value
   *            The value to replace the placeholder.
   * @return The customized XPath.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public String customizeXpath(String rawXpath, String value) {
    return rawXpath.replace("v1", value);
  }

  /**
   * Customizes the XPath pattern by replacing placeholder values with the
   * provided values.
   *
   * @param rawXpath
   *            The raw XPath pattern.
   * @param value1
   *            The first value to replace the first placeholder.
   * @param value2
   *            The second value to replace the second placeholder.
   * @return The customized XPath.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public String customizeXpath(String rawXpath, String value1, String value2) {
    return rawXpath.replace("v1", value1).replace("v2", value2);
  }

  /**
   * Customizes the XPath pattern by replacing placeholder values with the
   * provided values.
   *
   * @param rawXpath
   *            The raw XPath pattern.
   * @param value1
   *            The first value to replace the first placeholder.
   * @param value2
   *            The second value to replace the second placeholder.
   * @param value3
   *            The third value to replace the third placeholder.
   * @return The customized XPath.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public String customizeXpath(String rawXpath, String value1, String value2, String value3) {
    return rawXpath.replace("v1", value1).replace("v2", value2).replace("v3", value2);
  }

  /**
   * Copies a file from the source to the destination.
   *
   * @param source
   *            The source file.
   * @param destination
   *            The destination file.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void copyFile(File source, File destination) {
    try {
      FileUtils.copyFile(source, destination);
      Log.info("File saved to: " + destination);
    } catch (IOException e) {
      Log.error("Could NOT save screenshot...", e);
    }
  }

  /**
   * Copies a file from the source to the destination with metadata.
   *
   * @param source
   *            The source file.
   * @param destination
   *            The destination file.
   * @param metadata
   *            The metadata information.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void copyFile(File source, File destination, String metadata) {
    try {
      FileUtils.copyFile(source, destination);
      Log.info(metadata + " saved to: " + destination);
    } catch (IOException e) {
      Log.error("Could NOT save " + metadata + "...", e);
    }
  }

  /**
   * Converts a byte array to a file.
   *
   * @param data
   *            The byte array data.
   * @return The file created from the byte array.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public File byteArrayToFile(byte[] data) {
    File screenshotFile = null;
    try {
      // Wrap byte array into ByteArrayInputStream
      InputStream inputStream = new ByteArrayInputStream(data);

      // Create temporary file
      screenshotFile = File.createTempFile("screenshot", ".png");

      // Create FileOutputStream without specifying a file path
      OutputStream outputStream = new FileOutputStream(screenshotFile);

      // Read from input stream and write to output stream
      byte[] buffer = new byte[1024];
      int length;
      while ((length = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, length);
      }

      // Close streams
      inputStream.close();
      outputStream.close();
    } catch (IOException e) {
      Log.error("Couldn't save screenshot to a file...", e);
    }
    return screenshotFile;
  }
}
