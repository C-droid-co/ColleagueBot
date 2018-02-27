package ru.ustits.colleague.repeats.tools;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ustits
 */
@RequiredArgsConstructor
public enum CronFields {

  SECONDS(0),
  MINUTES(1),
  HOURS(2),
  DAY_OF_MONTH(3),
  MONTH(4),
  DAY_OF_WEEK(5);

  @Getter
  private final int fieldNumber;

}
