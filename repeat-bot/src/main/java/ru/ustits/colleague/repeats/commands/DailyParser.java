package ru.ustits.colleague.repeats.commands;

import ru.ustits.colleague.repeats.tools.CronBuilder;
import ru.ustits.colleague.tools.StringUtils;

import static ru.ustits.colleague.tools.StringUtils.split;

/**
 * @author ustits
 */
public final class DailyParser extends RepeatParser {

  private static final Integer PARAMETERS_COUNT = 3;

  public DailyParser() {
    super(PARAMETERS_COUNT);
  }

  public DailyParser(final int parametersCount, final int start) {
    super(parametersCount, start);
  }

  @Override
  public String transformCron(final String cron) {
    final String[] args = split(cron);
    final String hours = args[0];
    final String minutes = args[1];
    return CronBuilder.builder()
            .withSeconds(StringUtils.ZERO)
            .withHours(hours)
            .withMinutes(minutes)
            .build();
  }

}
