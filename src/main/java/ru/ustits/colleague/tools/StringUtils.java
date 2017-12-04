package ru.ustits.colleague.tools;

import static java.util.Arrays.copyOfRange;

/**
 * @author ustits
 */
public final class StringUtils {

  public static final String ZERO = "0";
  public static final String EMPTY = "";
  public static final String CR = "\r";
  public static final String LF = "\n";
  public static final String TAB = "\t";
  private static final String SPACE = " ";

  private StringUtils() {
  }

  public static String[] split(final String line) {
    if (line == null || line.isEmpty()) {
      return new String[]{};
    } else {
      return line.split(SPACE);
    }
  }

  public static String asString(final String[] array) {
    return asString(array, 0);
  }

  public static String asString(final String[] array, final int start) {
    if (array == null) {
      return "";
    }
    return asString(array, start, array.length);
  }

  public static String asString(final String[] array, final int start, final int end) {
    if (array == null || array.length == 0 || start >= end) {
      return "";
    }
    if (array.length < end) {
      return asString(array, start);
    }
    final String[] part = copyOfRange(array, start, end);
    return String.join(SPACE, part);
  }

}
