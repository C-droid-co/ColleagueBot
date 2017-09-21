package ru.ustits.colleague.tools;

/**
 * @author ustits
 */
public class TimeParseException extends Exception {

  public TimeParseException(final String message) {
    super(message);
  }

  public TimeParseException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
