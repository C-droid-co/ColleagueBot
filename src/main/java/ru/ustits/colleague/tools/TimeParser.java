package ru.ustits.colleague.tools;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * @author ustits
 */
public class TimeParser {

  private static final int HOURS = 1;
  private static final int MINUTES = 2;
  private static final int SECONDS = 3;

  private TimeParser() {
  }

  public static LocalTime parse(final String time) throws TimeParseException {
    if (time == null) {
      throw new TimeParseException("Time can't be null");
    }
    try {
      return LocalTime.parse(time);
    } catch (DateTimeParseException e) {
      throw new TimeParseException("Time must be of format hh:mm:ss", e);
    }
  }

  public static LocalTime parse(final String[] arguments) {
    final int hours = parseHours(arguments);
    final int minutes = parseMinutes(arguments);
    final int seconds = parseSeconds(arguments);
    return LocalTime.of(hours, minutes, seconds);
  }

  private static int parseHours(final String[] arguments) {
    return getTimeUnit(arguments, HOURS);
  }

  private static int parseMinutes(final String[] arguments) {
    return getTimeUnit(arguments, MINUTES);
  }

  private static int parseSeconds(final String[] arguments) {
    return getTimeUnit(arguments, SECONDS);
  }

  private static int getTimeUnit(final String[] arguments, final int unit) {
    if (arguments.length <= unit) {
      return 0;
    }
    return Integer.parseInt(arguments[unit]);
  }
}
