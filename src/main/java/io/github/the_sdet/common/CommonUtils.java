package io.github.the_sdet.common;

/**
 * This class handles various common Utilities and Helper methods
 *
 * @author Pabitra Swain (contact.the.sdet@gmail.com)
 */
@SuppressWarnings("unused")
public class CommonUtils {
  /** Represents a String without content. */
  public static String EMPTY_STRING = "";

  /**
   * This method extracts numeric value from a string. E.g., -$300.00 will return
   * 300.00
   *
   * @param elementText
   *            String value from which number to be extracted.
   * @return number in double format
   * @author Pabitra Swain (pabitra.swain.work@gmail.com)
   */
  public static double getNumericValue(String elementText) {
    return Double.parseDouble(elementText.replaceAll("[^\\d.]", EMPTY_STRING));
  }

  /**
   * This method extracts numeric integer value from a string. E.g., -$300.00 will
   * return 300
   *
   * @param elementText
   *            String value from which number to be extracted.
   * @return number in integer format
   * @author Pabitra Swain (pabitra.swain.work@gmail.com)
   */
  public static int getIntegerValue(String elementText) {
    return (int) Double.parseDouble(elementText.replaceAll("[^\\d.]", EMPTY_STRING));
  }
}
