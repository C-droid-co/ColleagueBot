package ru.ustits.colleague.tools;

import static ru.ustits.colleague.tools.StringUtils.asString;

/**
 * @author ustits
 */

public final class CronBuilder {

  private static final String ALL_VALUES = "*";
  private static final String NO_SPECIFIC_VALUE = "?";

  private String seconds = ALL_VALUES;
  private String minutes = ALL_VALUES;
  private String hours = ALL_VALUES;
  private String dayOfMonth = ALL_VALUES;
  private String month = NO_SPECIFIC_VALUE;
  private String dayOfWeek = ALL_VALUES;

  private CronBuilder() {}

  public static CronBuilder builder() {
    return new CronBuilder();
  }

  public String build() {
    return asString(new String[]{
            seconds, minutes, hours,
            dayOfMonth, month, dayOfWeek});
  }

  public CronBuilder withSeconds(final String seconds) {
    this.seconds = seconds;
    return this;
  }

  public CronBuilder withMinutes(final String minutes) {
    this.minutes = minutes;
    return this;
  }

  public CronBuilder withHours(final String hours) {
    this.hours = hours;
    return this;
  }

  public CronBuilder withDayOfMonth(final String dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
    return this;
  }

  public CronBuilder withMonth(final String month) {
    this.month = month;
    return this;
  }

  public CronBuilder withDayOfWeek(final String dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
    return this;
  }

}
