package ru.ustits.colleague.tools;

import lombok.NonNull;

import static org.quartz.CronExpression.isValidExpression;
import static ru.ustits.colleague.tools.CronFields.*;
import static ru.ustits.colleague.tools.StringUtils.asString;
import static ru.ustits.colleague.tools.StringUtils.split;

/**
 * @author ustits
 */

public final class CronBuilder {

  private static final String ALL_VALUES = "*";
  private static final String NO_SPECIFIC_VALUE = "?";
  private static final int ARGS_COUNT = 6;

  private String seconds = ALL_VALUES;
  private String minutes = ALL_VALUES;
  private String hours = ALL_VALUES;
  private String dayOfMonth = NO_SPECIFIC_VALUE;
  private String month = ALL_VALUES;
  private String dayOfWeek = ALL_VALUES;

  private CronBuilder() {}

  private CronBuilder(final String[] args) {
    seconds = args[SECONDS.getFieldNumber()];
    minutes = args[MINUTES.getFieldNumber()];
    hours = args[HOURS.getFieldNumber()];
    dayOfMonth = args[DAY_OF_MONTH.getFieldNumber()];
    month = args[MONTH.getFieldNumber()];
    dayOfWeek = args[DAY_OF_WEEK.getFieldNumber()];
  }

  public static CronBuilder builder() {
    return new CronBuilder();
  }

  public static CronBuilder builder(@NonNull final String cron) {
    final String[] args = split(cron);
    if (args.length == ARGS_COUNT && isValidExpression(cron)) {
      return new CronBuilder(args);
    } else {
      throw new IllegalStateException("Wrong cron format: " + cron);
    }
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
