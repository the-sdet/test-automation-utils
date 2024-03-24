package io.github.the_sdet.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.the_sdet.logger.Log;
import io.github.the_sdet.web.SeleniumUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;

import static java.util.Collections.singletonList;

/**
 * Utility class for Appium Mobile automation tasks. This class provides various
 * methods to interact with mobile elements, take screenshots, handling scroll
 * and swipes, etc...
 *
 * @author Pabitra Swain (contact.the.sdet@gmail.com)
 */
@SuppressWarnings("unused")
public class AppiumUtils extends SeleniumUtils {
  AppiumDriver driver;

  /**
   * Constructor to initialize AppiumUtils.
   *
   * @param driver
   *            The Appium Driver (AndroidDriver/IOSDriver) instance to use.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public AppiumUtils(AppiumDriver driver) {
    super(driver);
    this.driver = driver;
  }

  /**
   * This method performs a click action on an element using gestures
   *
   * @param element
   *            element locator
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void advanceClick(By element) {
    waitForSeconds(1);
    WebElement webElement = driver.findElement(element);
    Point point = webElement.getLocation();
    Dimension size = webElement.getSize();
    int x = point.getX() + size.getWidth() / 2;
    int y = point.getY() + size.getHeight() / 2;

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence touch = new Sequence(finger, 0);
    touch.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
    driver.perform(singletonList(touch));
  }

  /**
   * Hides the on-screen keyboard if it is displayed.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void hideKeyboard() {
    if (driver instanceof AndroidDriver) {
      if (((AndroidDriver) driver).isKeyboardShown())
        ((AndroidDriver) driver).hideKeyboard();
    }
  }

  /**
   * Enumeration for scroll/swipe directions.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public enum DIRECTION {
    /** Represents the downward scroll/swipe direction. */
    DOWN,

    /** Represents the upward scroll/swipe direction. */
    UP,

    /** Represents the leftward scroll/swipe direction. */
    LEFT,

    /** Represents the rightward scroll/swipe direction. */
    RIGHT
  }

  /**
   * This method scrolls and swipes in both direction to check an element's
   * presence.
   *
   * @param direction
   *            scroll direction
   * @param element
   *            locator
   * @param duration
   *            scroll or swipe duration
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void scrollOrSwipe(By element, DIRECTION direction, long duration) {
    Dimension size = driver.manage().window().getSize();

    int startX, startY, endX, endY;

    switch (direction) {
      case RIGHT :
        startY = size.height / 2;
        startX = (int) (size.width * 0.90);
        endX = (int) (size.width * 0.05);

        for (int i = 0; i < 3; i++) {
          if (!isVisible(element)) {
            swipe(startX, startY, endX, Duration.ofMillis(duration));
          } else {
            break;
          }
        }
        break;

      case LEFT :
        startY = size.height / 2;
        startX = (int) (size.width * 0.05);
        endX = (int) (size.width * 0.90);

        for (int i = 0; i < 3; i++) {
          if (!isVisible(element)) {
            swipe(startX, startY, endX, Duration.ofMillis(duration));
          } else {
            break;
          }
        }
        break;

      case UP :
        endY = (int) (size.height * 0.70);
        startY = (int) (size.height * 0.30);
        startX = (size.width / 2);
        for (int i = 0; i <= 3; i++) {
          if (!isVisible(element)) {
            scroll(startX, startY, endY, Duration.ofMillis(duration));
          } else {
            break;
          }
        }
        break;

      case DOWN :
        startY = (int) (size.height * 0.70);
        endY = (int) (size.height * 0.30);
        startX = (size.width / 2);
        for (int i = 0; i <= 3; i++) {
          if (!isVisible(element)) {
            scroll(startX, startY, endY, Duration.ofMillis(duration));
          } else {
            break;
          }
        }
        break;
      default :
        Log.error("Invalid Direction...");
    }
  }

  /**
   * Handles swipe to Pay button
   *
   * @param container
   *            slider container
   * @param slider
   *            actual slider
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipeElementInsideAContainer(By container, By slider) {
    int containerWidth = driver.findElement(container).getSize().getWidth();
    WebElement swipeButton = driver.findElement(slider);
    Dimension size = swipeButton.getSize();
    int middleX = size.getWidth() / 2;
    int middleY = size.getHeight() / 2;
    Point source = swipeButton.getLocation();
    int startX = source.getX() + middleX;
    int startY = source.getY() + middleY;
    int endX = startX + containerWidth;
    swipe(startX, startY, endX, Duration.ofMillis(500));
  }

  /**
   * Swipes off a push notification appearing on top of the screen
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipePushNotification() {
    Dimension size = driver.manage().window().getSize();
    int startX = (int) (size.width * 0.1);
    int endX = (int) (size.height * 0.9);
    int startY = (int) (size.height * 0.1);
    swipe(startX, startY, endX, Duration.ofMillis(600));
  }

  /**
   * Swipes left in the middle of the screen.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipeLeft() {
    int height = driver.manage().window().getSize().getHeight();
    int width = driver.manage().window().getSize().getWidth();

    int startY = height / 2;
    int startX = (int) (width * 0.8); // Swipe starts from 80% of the width
    int endX = (int) (width * 0.2); // Swipe ends at 20% of the width
    swipe(startX, startY, endX, Duration.ofMillis(600));
  }

  /**
   * Swipes right in the middle of the screen.
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipeRight() {
    int height = driver.manage().window().getSize().getHeight();
    int width = driver.manage().window().getSize().getWidth();

    int startY = height / 2;
    int startX = (int) (width * 0.2); // Swipe starts from 20% of the width
    int endX = (int) (width * 0.8); // Swipe ends at 80% of the width
    swipe(startX, startY, endX, Duration.ofMillis(600));
  }

  /**
   * Handles scroll functionality
   *
   * @param startX
   *            Start X coordinate
   * @param startY
   *            Start Y coordinate
   * @param endY
   *            End Y coordinate
   * @param delay
   *            Scroll Timeout
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void scroll(int startX, int startY, int endY, Duration delay) {
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence swipe = new Sequence(finger, 0);
    swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        .addAction(finger.createPointerMove(delay, PointerInput.Origin.viewport(), startX, endY))
        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
    driver.perform(singletonList(swipe));
    waitForSeconds(1);
  }

  /**
   * Handles swipe functionality
   *
   * @param startX
   *            Start X coordinate
   * @param startY
   *            Start Y coordinate
   * @param endX
   *            End X coordinate
   * @param delay
   *            Swipe Timeout
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipe(int startX, int startY, int endX, Duration delay) {
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence swipe = new Sequence(finger, 0);
    swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        .addAction(finger.createPointerMove(delay, PointerInput.Origin.viewport(), endX, startY))
        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
    driver.perform(singletonList(swipe));
  }

  /**
   * Performs a swipe on the given element
   *
   * @param elementLocator
   *            The By locator of the element to swipe on.
   * @param startPercent
   *            The starting percentage of the element width for the swipe.
   * @param endPercent
   *            The ending percentage of the element width for the swipe.
   * @param durationMs
   *            The duration of the swipe in milliseconds.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipe(By elementLocator, double startPercent, double endPercent, int durationMs) {
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    WebElement element = driver.findElement(elementLocator);

    int startX = (int) (element.getLocation().getX() + element.getSize().getWidth() * startPercent);
    int endX = (int) (element.getLocation().getX() + element.getSize().getWidth() * endPercent);
    int centerY = element.getLocation().getY() + element.getSize().getHeight() / 2;

    swipe(startX, centerY, endX, Duration.ZERO);
  }

  /**
   * Scrolls the screen by X percentage
   *
   * @param scrollPercentage
   *            percentage of the screen to scroll
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void scrollByPercent(int scrollPercentage) {
    double percentage = (double) scrollPercentage / 100.0;
    Dimension size = driver.manage().window().getSize();
    int startX = size.width / 2;
    int startY = (int) (size.height * (1 - percentage));
    int endY = (int) (size.height * percentage);

    scroll(startX, startY, endY, Duration.ofMillis(500));
  }

  /**
   * Refreshes the screen by swiping down the screen
   *
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipeDownAndRefreshPage() {
    int deviceWidth = driver.manage().window().getSize().getWidth();
    int deviceHeight = driver.manage().window().getSize().getHeight();
    int midX = (deviceWidth / 2);
    int midY = (deviceHeight / 2);
    int bottomEdge = (int) (deviceHeight * 0.85f);

    scroll(midX, midY, bottomEdge, Duration.ofMillis(800));
  }

  /**
   * Tries to find an element by Scrolls the screen with a total scroll of 2
   * times. Returns true if it finds the element.
   *
   * @param locator
   *            element locator
   * @return true/false
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean checkIsElementPresentAllowScrolling(By locator) {
    for (int i = 0; i < 5; i++) {
      if (waitAndCheckIsVisible(locator, Duration.ofSeconds(2)))
        return true;
      else {
        scrollByPercent(10);
        if (waitAndCheckIsVisible(locator, Duration.ofSeconds(2)))
          return true;
      }
    }
    return false;
  }

  /**
   * Tries to find an element by Scrolls the screen with a total scroll of 2
   * times. Returns true if it finds the element.
   *
   * @param locator
   *            element locator
   * @param maxScrolls
   *            maximum number of scrolls
   * @return true/false
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean checkIsElementPresentAllowScrolling(By locator, int maxScrolls) {
    for (int i = 0; i < maxScrolls; i++) {
      if (waitAndCheckIsVisible(locator, Duration.ofSeconds(2)))
        return true;
      else {
        scrollByPercent(10);
        if (waitAndCheckIsVisible(locator, Duration.ofSeconds(2)))
          return true;
      }
    }
    return false;
  }

  /**
   * Tries to find an element by Scrolls the screen with a total scroll of 2
   * times. Returns true if it finds the element.
   *
   * @param locator
   *            element locator
   * @param scrollPercentAtATime
   *            Percentage of scroll
   * @param maxScrolls
   *            maximum number of scrolls
   * @return true/false
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean checkIsElementPresentAllowScrolling(By locator, int scrollPercentAtATime, int maxScrolls) {
    for (int i = 0; i < maxScrolls; i++) {
      if (waitAndCheckIsVisible(locator, Duration.ofSeconds(2)))
        return true;
      else {
        scrollByPercent(scrollPercentAtATime);
        if (waitAndCheckIsVisible(locator, Duration.ofSeconds(2)))
          return true;
      }
    }
    return false;
  }

  /**
   * Scrolls to the element and performs a click.
   *
   * @param element
   *            - By element
   * @return boolean - true if the scroll and click were successful, false
   *         otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean scrollAndClick(By element) {
    try {
      if (checkIsElementPresentAllowScrolling(element)) {
        click(element);
        return true;
      } else
        return false;
    } catch (Exception e) {
      Log.error("Exception during scroll and click...", e);
      return false;
    }
  }

  /**
   * Scrolls to the element and performs a click.
   *
   * @param element
   *            - By element
   * @param scrollPercentAtATime
   *            Percentage of scroll
   * @param maxScrolls
   *            maximum number of scrolls
   * @return boolean - true if the scroll and click were successful, false
   *         otherwise
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public boolean scrollAndClick(By element, int scrollPercentAtATime, int maxScrolls) {
    try {
      if (checkIsElementPresentAllowScrolling(element, scrollPercentAtATime, maxScrolls)) {
        click(element);
        return true;
      } else
        return false;
    } catch (Exception e) {
      Log.error("Exception during scroll and click...", e);
      return false;
    }
  }

  /**
   * Swipe right on the given element using the mobile:swipe command in Appium.
   *
   * @param element
   *            The By locator of the element to swipe on.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipeRight(By element) {
    swipe(element, 0.09, 0.01, 500);
  }

  /**
   * Swipe left on the given element using the mobile:swipe command in Appium.
   *
   * @param element
   *            The By locator of the element to swipe on.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void swipeLeft(By element) {
    swipe(element, 0.01, 0.09, 500);
  }

  /**
   * Clicks on the element identified by the given XPath using JavaScript.
   *
   * @param xpath
   *            The XPath of the element to click.
   * @author Pabitra Swain (contact.the.sdet@gmail.com)
   */
  public void javaScriptClick(String xpath) {
    advanceClick(By.xpath(xpath));
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
  void javaScriptFillText(String xpath, String value) {
    driver.findElement(By.xpath(xpath)).sendKeys(value);
  }
}
